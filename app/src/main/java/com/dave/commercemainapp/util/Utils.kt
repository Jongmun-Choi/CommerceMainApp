package com.dave.commercemainapp.util

object Utils {

    fun Long.toPrice(): String {
        return String.format("%,d원", this)
    }

    enum class SectionType(typeString: String) {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical"),
        GRID("grid")
    }
}