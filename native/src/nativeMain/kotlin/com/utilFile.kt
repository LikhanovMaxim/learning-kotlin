package com

import kotlinx.cinterop.*
import platform.posix.*


//todo add dependencies ktor util
// import io.ktor.utils.io.core.*
//import io.ktor.utils.io.streams.*
//fun readFileKtor(filePath: String): String {
//    val fileDescriptor = open(filePath, EROFS)
//    println("fileDescriptor = $fileDescriptor")
//    val input = Input(fileDescriptor)
//    val read2 = input.readBytes()
//    println(read2.toString())
//    println(read2.decodeToString())
////    input.close()
//    return read2.decodeToString()
//}

//todo refactor fgetc ?
fun readFile(filePath: String): String {
    val resultBuffer = StringBuilder()
    val file = fopen(filePath, "r") ?: throw IllegalArgumentException("Cannot open input file $filePath")

    try {
        memScoped {
            val readBufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line = fgets(buffer, readBufferLength, file)?.toKString()
            while (line != null) {
//                println("line = $line")
                resultBuffer.append(line)
                line = fgets(buffer, readBufferLength, file)?.toKString()
            }
        }
    } finally {
        fclose(file)
    }

    return resultBuffer.toString()
}


//todo read file by open()
fun readFile(fileDescriptor: Int?, fileName: String) {
//    fileDescriptor?.let { Output(it).close() }
    //todo rename
    val descriptor = open(
        fileName,
        O_RDONLY
//            ,
//            S_IRUSR or S_IWUSR or S_IRGRP or S_IROTH
    )
    memScoped {
        val readBufferLength = 64 * 1024
        val buffer = allocArray<ByteVar>(readBufferLength)
//        var line = gets(buffer, readBufferLength, descriptor)?.toKString()
//        while (line != null) {
//            println("line = $line")
//            returnBuffer.append(line)
//            line = fgets(buffer, readBufferLength, descriptor)?.toKString()
//        }
    }
    val buf = ByteArray(2048)
//    buf.of
    val cValuesRef: CValuesRef<*>? = null//todo
    buf.usePinned {
        platform.posix.read(descriptor, cValuesRef, 256)
    }
    val version = buf.toKString()
    println("Our Linux version is ${version}")
    platform.posix.close(descriptor)

}

