package com

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.modules.*

private typealias AgentParameters = Map<String, String>

//fun String?.asAgentParams(
//    lineDelimiter: String = ",",
//    removeStartWith: String = "",
//    mapDelimiter: String = "="
//): AgentParameters {
//    if (this.isNullOrEmpty()) return mutableMapOf()
//    val split = this.split(lineDelimiter)
//    println("split=$split")
//    val mapNotNull:List<String> = split
//        .filterNot {
//            it.isEmpty() || (removeStartWith.isNotBlank() && it.startsWith(removeStartWith))
//        }
//    println("mapNotNull=$mapNotNull")
//    val associate: Map<String, String> = mapNotNull.associate {
//        val (key, value) = it.split(mapDelimiter)
//        val pair = key to value
//        println("pair $it =>$pair")
//        pair
//    }
//    println("associate=$associate")
//    return associate
//}

fun String?.asAgentParams(
    lineDelimiter: String = ",",
    filterPrefix: String = "",
    mapDelimiter: String = "="
): AgentParameters {
    if (this.isNullOrEmpty()) return mutableMapOf()
    return try {
        this.split(lineDelimiter)
            .filter { it.isNotEmpty() && (filterPrefix.isEmpty() || !it.startsWith(filterPrefix)) }
            .associate {
                val (key, value) = it.split(mapDelimiter)
                val pair = key to value
                pair
            }
    } catch (parseException: Exception) {
        throw IllegalArgumentException("wrong agent parameters: $this")
    }
}

fun String?.asAgentParamsProperties(): AgentParameters {
    if (this.isNullOrEmpty()) return mutableMapOf()
    return try {
        this.split("\n").mapNotNull {
            if (it.startsWith("#")) {
                null
            } else it
        }.associate {
            val (key, value) = it.split("=")
            val pair = key to value
            pair
        }
    } catch (parseException: Exception) {
        throw IllegalArgumentException("wrong agent parameters: $this")
    }
}

//fun String?.asAgentParams(): AgentParameters {
//    if (this.isNullOrEmpty()) return mutableMapOf()
//    return try {
//        this.split(",")
//            .associate {
//                val (key, value) = it.split("=")
//                key to value
//            }
//    } catch (parseException: Exception) {
//        throw IllegalArgumentException("wrong agent parameters: $this")
//    }
//}

@InternalSerializationApi
inline fun <reified T : Any> Map<String, String>.parseAs(): T = T::class.serializer().deserialize(
    SimpleMapDecoder(this)
)

@ExperimentalSerializationApi
class SimpleMapDecoder(
    map: Map<String, String>,
    override val serializersModule: SerializersModule = EmptySerializersModule
) : AbstractDecoder() {

    private val iterator = map.iterator()

    private var current: Pair<SerialDescriptor, String>? = null


    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        val next = iterator.takeIf { it.hasNext() }?.next()
        return next?.key?.let(descriptor::getElementIndex)?.let { index ->
            when (index) {
                in 0 until descriptor.elementsCount -> {
                    current = descriptor.getElementDescriptor(index) to next.value
                    index
                }
                //ignore unknown properties
                else -> decodeElementIndex(descriptor)
            }

//        } ?: CompositeDecoder.READ_DONE
        } ?: CompositeDecoder.DECODE_DONE
    }

    override fun decodeValue(): Any = current?.let { (desc, value) ->
        when (desc.kind) {
            PrimitiveKind.STRING -> value
            PrimitiveKind.BOOLEAN -> value.toBoolean()
            PrimitiveKind.BYTE -> value.toByte()
            PrimitiveKind.SHORT -> value.toShort()
            PrimitiveKind.INT -> value.toInt()
            PrimitiveKind.LONG -> value.toLong()
            PrimitiveKind.FLOAT -> value.toFloat()
            PrimitiveKind.DOUBLE -> value.toDouble()
            else -> super.decodeValue()
        }
    } ?: super.decodeValue()
}


