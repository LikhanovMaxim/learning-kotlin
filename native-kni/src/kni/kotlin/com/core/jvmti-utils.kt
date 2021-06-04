@file:Suppress(
    "unused",
    "UNUSED_PARAMETER",
    "RemoveRedundantQualifierName"
)

package com.core

import com.epam.drill.jvmapi.JNIEnvPointer
import com.epam.drill.jvmapi.gen.*
import kotlinx.cinterop.*
import kotlin.native.concurrent.freeze

//@CName("Agent_OnLoad")
//@CName("JVM_OnLoad")
//public fun agentOnLoad(
//    vmPointer: CPointer<JavaVMVar>,
//    options: String,
//    reservedPtr: Long
//): Int = memScoped {
//    println("JVM_OnLoad  agentOnLoad...")
//    com.epam.drill.jvmapi.vmGlobal.value = vmPointer.freeze()
//    val vm = vmPointer.pointed
//    val jvmtiEnvPtr = alloc<CPointerVar<jvmtiEnvVar>>()
//    vm.value!!.pointed.GetEnv!!(
//        vm.ptr, jvmtiEnvPtr.ptr.reinterpret(),
//        com.epam.drill.jvmapi.gen.JVMTI_VERSION.convert()
//    )
//    com.epam.drill.jvmapi.jvmti.value = jvmtiEnvPtr.value
//    jvmtiEnvPtr.value.freeze()
//    JNI_OK
//}

@CName("JNI_OnLoad")
public fun jniOnLoad(
    vmPointer: CPointer<JavaVMVar>,
    reservedPtr: Long
): Int = memScoped {
    println("JNI_OnLoad...")
    com.epam.drill.jvmapi.vmGlobal.value = vmPointer.freeze()
    val vm = vmPointer.pointed
    val jvmtiEnvPtr = alloc<CPointerVar<jvmtiEnvVar>>()
    vm.value!!.pointed.GetEnv!!(
        vm.ptr, jvmtiEnvPtr.ptr.reinterpret(),
        com.epam.drill.jvmapi.gen.JVMTI_VERSION.convert()
    )
    com.epam.drill.jvmapi.jvmti.value = jvmtiEnvPtr.value
    jvmtiEnvPtr.value.freeze()
    JNI_VERSION_1_6
}

//@CName("Agent_OnUnload")
//public fun agentOnUnload(vmPointer: CPointer<JavaVMVar>): Unit {
////    com.epam.drill.core.Agent.agentOnUnload()
//}

@CName("currentEnvs")
public fun currentEnvs(): JNIEnvPointer {
    return com.epam.drill.jvmapi.currentEnvs()
}

@CName("jvmtii")
public fun jvmtii(): CPointer<jvmtiEnvVar>? = com.epam.drill.jvmapi.jvmtii()

@CName("getJvm")
public fun getJvm(): CPointer<JavaVMVar>? = com.epam.drill.jvmapi.vmGlobal.value

@CName("checkEx")
public fun checkEx(errCode: jvmtiError, funName: String): jvmtiError =
    com.epam.drill.jvmapi.checkEx(errCode, funName)

@CName("JNI_OnUnload")
public fun JNI_OnUnload(): Unit {
    // stub
}

@CName("JNI_GetCreatedJavaVMs")
public fun JNI_GetCreatedJavaVMs(): Unit {
    // stub
}

@CName("JNI_CreateJavaVM")
public fun JNI_CreateJavaVM(): Unit {
    // stub
}

@CName("JNI_GetDefaultJavaVMInitArgs")
public fun JNI_GetDefaultJavaVMInitArgs(): Unit {
    // stub
}
