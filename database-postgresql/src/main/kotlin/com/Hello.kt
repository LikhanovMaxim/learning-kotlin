package com

import javax.persistence.*

fun main() {
    println("hello")

//    doInHib
}

@Entity
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String
)
