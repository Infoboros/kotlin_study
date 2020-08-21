package com.bignerdranch.nyethack

class Player {
    var name = "madrigal"
        get() = field.capitalize()
        private set(value) {
            field = value.trim()
        }

    var healthPoints = 89
    val isBlessed = true
    private val isImmortal = false

    fun castFireball(numFireballs: Int = 2) {
        println("A glass of Fireball springs into existence. (x$numFireballs)")
    }

    val auraColor
        get() = if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"

    val healthStatus
        get() = when (healthPoints) {
            100 -> "полностью здоров!"
            in 90..99 -> "поцарапался."
            in 75..89 -> if (isBlessed)
                "слегка ранен, но раны быстро заживут."
            else
                "слегка ранен."
            in 15..74 -> "выглядит тяжело раненным."
            else -> "в ужасном состоянии!"
        }

}