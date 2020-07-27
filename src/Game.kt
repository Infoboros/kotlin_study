fun main(args: Array<String>) {
    val name = "Madrigal"
    val race = "gnome"
    val faction = when(race){
        "dwarf" -> "Копатель из рудников"
        "gnome" -> "Копатель из рудников"
        "orc"   -> "Свободный человек с холмов"
        "human" -> "Свободный человек с холмов"
        else    -> "Ты кто нахуй такой"
    }
    var healthPoints = 89
    val isBlessed = true
    val isImmortal = false

    val auraColor = auraColor(isBlessed, healthPoints, isImmortal)

    val healthStatus = formatHealthStatus(healthPoints, isBlessed)

    printPlayerStatus(auraColor, isBlessed, name, healthStatus)

    val drunkStatus = castFireball(66)
    printDrunkStatus(name, drunkStatus)
}

private fun printDrunkStatus(name: String, drunkStatus: Int) =
    println("$name ${formatDrunkStatus(drunkStatus)}")


private fun printPlayerStatus(auraColor: String,
                              isBlessed: Boolean,
                              name: String,
                              healthStatus: String) {
    println("(Aura: $auraColor) (Blessed: ${if (isBlessed) "YES" else "NO"})")
    println("$name $healthStatus")
}

private fun auraColor(isBlessed: Boolean,
                      healthPoints: Int,
                      isImmortal: Boolean) =
    if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"

private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean)=
        when (healthPoints) {
            100 -> "полностью здоров!"
            in 90..99 -> "поцарапался."
            in 75..89 -> if (isBlessed)
                "слегка ранен, но раны быстро заживут."
            else
                "слегка ранен."
            in 15..74 -> "выглядит тяжело раненным."
            else -> "в ужасном состоянии!"
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