package com.shoshin.restaurant.local_db.mappers

abstract class Mapper<E, T> {
    abstract fun mapTo(from: E): T
    abstract fun mapFrom(from: T): E

    fun mapTo(fromList: List<E>): List<T> {
        return fromList.map { mapTo(it) }
    }

    fun mapFrom(fromList: List<T>): List<E> {
        return fromList.map { mapFrom(it) }
    }
}