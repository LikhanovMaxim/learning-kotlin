package com


import kotlinx.serialization.*

@InternalSerializationApi
@ExperimentalSerializationApi
inline fun <reified T> BinaryFormat.dump(
    value: T
): ByteArray = encodeToByteArray(serializer(), value)

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> BinaryFormat.dump(
    serializer: SerializationStrategy<T>,
    value: T
): ByteArray = encodeToByteArray(serializer, value)

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> BinaryFormat.dumps(
    serializer: SerializationStrategy<T>,
    value: T
): String = encodeToHexString(serializer, value)

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> BinaryFormat.load(
    deserializer: DeserializationStrategy<T>,
    bytes: ByteArray
): T = decodeFromByteArray(deserializer, bytes)

@InternalSerializationApi
@ExperimentalSerializationApi
fun <T> BinaryFormat.loads(
    deserializer: DeserializationStrategy<T>,
    string: String
): T = decodeFromHexString(deserializer, string)
