package com

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.lang.StringBuilder
import java.util.*

import javax.lang.model.element.Modifier;

class JavaGen {

    fun getJavaFile(): JavaFile {
        return JavaFile
            .builder("com.generated", getGeneratedTest())
            .indent("    ")
            .build()
    }

    fun getGeneratedTest(
        clazzName: String = "BigClazz",
    ): TypeSpec? {
        return TypeSpec.classBuilder(clazzName)
            .addModifiers(Modifier.PUBLIC)
//            .addMethod(
//                MethodSpec
//                    .methodBuilder("some")
//                    .addModifiers(Modifier.PUBLIC)
//                    .build()
//            )
            .addMethod(methodWithCycles())
//            .addMethod(methodWithCycles("methodForinFor1", countCycles = 100))
//            .addMethod(methodWithCycles("methodForinFor2", countCycles = 200))
//            .addMethod(methodWithCycles("methodForinFor3", countCycles = 300))
            .addMethod(methodWithCondition())
            .addMethod(methodWithCondition("methodIf1", 50))
//            .addMethod(methodWithCondition("methodIf2", 200))
//            .addMethod(methodWithCondition("methodIf3", 300))
//            .addMethod(methodWithCondition("methodIf4", 400))
//            .addMethod(methodWithCondition("methodIf5", 500))
            .build()
    }

    var countIf = 0

    private fun methodWithCondition(
        methodName: String = "methodWithCondition",
        countIfElse: Int = 10
    ): MethodSpec? {
        countIf = countIfElse
        val ifExample = ifExample()
        return MethodSpec.methodBuilder(methodName)
            .returns(Int::class.java)
            .addStatement("int sum = 0")
            .addStatement("Random random = new \$T()", Random::class.java)
            .addStatement(ifExample)
            .addStatement("return sum")
            .build()
    }

    private fun ifExample(): String {
        if (countIf == 0) {
            return ""
        }
        val iterator = "1$countIf"
        countIf--
        return """
        if ((int) (Math.random() * 10000) % 2 == 0 || ${condition(countIf+10)}) {
            sum += $iterator;
            ${ifExample()}
        } else {
            sum -= $iterator;
            ${ifExample()}
        }
        """.trimIndent()
    }

    private fun condition(count: Int = 20): String {
        val a = StringBuilder()
        repeat(count) {
            a.append("random.nextInt(100) + ${it + 1} > random.nextInt(200) || ")
        }
        a.append("random.nextInt(100) >= random.nextInt(200)")
        return a.toString()
    }


    var countFor = 3
    private fun methodWithCycles(
        methodName: String = "manyForInFor",
        from: Int = 0,
        to: Int = 100,
        countCycles: Int = 10,
    ): MethodSpec? {
        countFor = countCycles
        val forExample = forExample(from, to)
        return MethodSpec.methodBuilder(methodName)
            .returns(Long::class.java)
            .addStatement("long sum = 0")
            .addStatement("Random random = new \$T()", Random::class.java)
            .addStatement(forExample)
//            .beginControlFlow("for (int i = \$L; i <= \$L; i++)", from, to)
//            .addStatement("sum = sum + i")
//            .endControlFlow()
            .addStatement("return sum")
            .build()
    }

    private fun forExample(from: Int, to: Int): String {
        if (countFor == 0) {
            return ""
        }
        val iterator = "i$countFor"
        countFor--
        return """
            for (int $iterator = random.nextInt(5);  $iterator <= random.nextInt(15) + 5;  $iterator++){ 
                sum += $iterator; 
                ${forExample(from, to)}
            }
       """.trimIndent()
    }

//    fun generateClass(clazzName: String, id: Int): TypeSpec {
//
//        return TypeSpec.classBuilder(clazzName)
//            .addFunction(someFunction(id))
//            .build()
//    }
//
//    fun someFunction(id: Int): FunSpec {
//        return FunSpec.builder("someFun$id")
//            .addStatement("println(\"hi\")")
//            .build()
//    }
}
