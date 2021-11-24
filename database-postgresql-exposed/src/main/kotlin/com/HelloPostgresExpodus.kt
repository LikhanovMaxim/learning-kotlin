package com

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
    println("hello postgresql")

    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver",
        user = "postgres", password = "admin123"
    )

    transaction {
        Person.selectAll().forEach {
            println("it $it")
        }
    }
}

object Person : Table() {
    val id = integer("id")
    val name = varchar("name", length = 256)

}

