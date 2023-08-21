package ru.resodostudios.flick.core.model.data

data class Person(
    val birthday: String,
    val country: Country,
    val deathday: String,
    val gender: String,
    val id: Int,
    val image: Image,
    val name: String
)