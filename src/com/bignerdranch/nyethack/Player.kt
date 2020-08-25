package com.bignerdranch.nyethack

import java.io.File
import com.bignerdranch.nyethack.Coordinate as Coordinate

class Player(_name: String = "madrigal",
             override var healthPoints: Int = 100,
             val isBlessed: Boolean = true,
             private val isImmortal: Boolean = false) : Fightable {
    var name = _name
        get() = "${field.capitalize()} of $hometown"
        private set(value) {
            field = value.trim()
        }

    val hometown by lazy { selectHometown() }
    var currentPosition = Coordinate(0, 0)

    override val diceCount = 3

    override val diceSides = 6

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) damageRoll * 2 else damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }

    init {
        require(healthPoints > 0, { "healthPoints must be greater than zero." })
        require(name.isNotBlank(), { "Player must have a name." })
    }

    constructor(name: String) : this(
            name,
            if (name.toLowerCase() == "kar") 40 else 100,
            true,
            false
    )

    fun selectHometown() = File("data/towns.txt")
            .readText()
            .split('\n')
            .shuffled()
            .first()

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