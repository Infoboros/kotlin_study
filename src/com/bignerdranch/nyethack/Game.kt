package com.bignerdranch.nyethack

import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    Game.play()
}

object Game {

    private val player = Player()
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
            listOf(currentRoom, Room("Tavern"), Room("Back Room")),
            listOf(Room("Long Corridor"), Room("Generic Room"))
    )

    init {
        println("Welcome, adventurer.")
        player.castFireball()
    }

    fun play() {
        while (true) {
            println(currentRoom.description)
            println(currentRoom.load())

            printPlayerStatus()

            print("> Enter your command: ")
            println(GameInput(readLine()).processCommand())
            println()
        }
    }

    private fun printPlayerStatus() {
        println("(Aura: ${player.auraColor}) (Blessed: ${if (player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.healthStatus}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when (command.toLowerCase()) {
            "fight" -> fight()
            "quit" -> exitProcess(0)
            "map" -> getMap()
            "move" -> move(argument)
            "ring" -> ring()
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }

    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0){
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete."
    }?: "There's nothing here to fight."

    private fun slay(monster: Monster){
        println("${monster.name} did ${monster.attack(player)} damage!")
        println("${player.name} did ${player.attack(monster)} damage!")

        if (player.healthPoints <= 0){
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if (monster.healthPoints <= 0){
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }
    }

    private fun ring() = when(currentRoom){
        is TownSquare -> (currentRoom as TownSquare).ringBell()
        else -> "You don't ring because You not on Town Square."
    }

    private fun getMap (): String {
        var map = ""
        worldMap.forEach {
            it.forEach {
                map += when(currentRoom){
                    it -> "X "
                    else -> when(it.monster){
                        is Goblin -> "G "
                        else -> "O "
                    }
                }
            }
            map += '\n'
        }
        return map
    }

    private fun move(directionInput: String) =
            try {
                val direction = Direction.valueOf(directionInput.toUpperCase())
                val newPosition = direction.updateCoordinate(player.currentPosition)

                if (!newPosition.isInBounds)
                    throw IllegalStateException("$direction is out of bounds.")

                val newRoom = worldMap[newPosition.y][newPosition.x]
                player.currentPosition = newPosition
                currentRoom = newRoom
                "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
            } catch (e: Exception) {
                "Invalid direction: $directionInput."
            }

}

private fun printDrunkStatus(name: String, drunkStatus: Int) =
        println("$name ${formatDrunkStatus(drunkStatus)}")


private fun formatDrunkStatus(drunkStatus: Int) =
        when (drunkStatus) {
            in 41..50 -> "в стельку!"
            in 31..40 -> "сильно пьяный."
            in 21..30 -> "пьяный."
            in 11..20 -> "выпивший."
            else -> "навеселе."
        }

private fun castFireball(numFireballs: Int = 2): Int {
    println("Порождается бокал дурманящего напитка. (x$numFireballs)")
    return if (numFireballs > 50) 50 else numFireballs
}