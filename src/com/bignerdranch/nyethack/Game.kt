package com.bignerdranch.nyethack

fun main(args: Array<String>) {
    val race = "gnome"
    val fraction = when(race){
        "dwarf" -> "Копатель из рудников"
        "gnome" -> "Копатель из рудников"
        "orc"   -> "Свободный человек с холмов"
        "human" -> "Свободный человек с холмов"
        else    -> "Ты кто нахуй такой"
    }


    val player = Player("Madrigal")
    player.castFireball()

    var currentRoom : Room = TownSquare()
    println(currentRoom.description)
    println(currentRoom.load())

    printPlayerStatus(player)
}

private fun printDrunkStatus(name: String, drunkStatus: Int) =
    println("$name ${formatDrunkStatus(drunkStatus)}")


private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor}) (Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name} ${player.healthStatus}")
}

private fun formatDrunkStatus(drunkStatus: Int)=
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