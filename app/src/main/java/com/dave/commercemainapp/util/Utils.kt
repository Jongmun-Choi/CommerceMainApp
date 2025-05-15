package com.dave.commercemainapp.util

object Utils {

    fun Long.toPrice(): String {
        return String.format("%,d원", this)
    }

    inline fun <reified T : Enum<T>> safeValueOf(type: String, default: T): T {
        return try {
            java.lang.Enum.valueOf(T::class.java, type)
        } catch (e: IllegalArgumentException) {
            default
        }
    }

    enum class SectionType(typeString: String) {
        HORIZONTAL("horizontal"),
        VERTICAL("vertical"),
        GRID("grid")
    }
}