package com.bignerdranch.nyethack

import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()

val patronGold = mutableMapOf<String, Double>()

val helloString = "*** Welcome to Taernyl's Folly ***"
val menuList = File("data/tavern-menu-data.txt")
        .readText()
        .split('\n')
        .sorted()

private fun addDot(first: String, last: String, size: Int): String {
    var dotString: String = first
    (0..size - first.length - last.length).forEach { dotString += '.' }
    return dotString + last
}

fun main(args: Array<String>) {
    if ("Eli" in patronList)
        println("The tavern master says: Eli's in the back playing cards. ")
    else
        println("The tavern master says: Eli isn't here.")

    if (patronList.containsAll(listOf("Sophie", "Mordoc")))
        println("The tavern master says: Yea, they're seated by the stew kettle.")
    else
        println("The tavern master says: Nay, they departed hours ago.")


    printMenu()

    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniquePatrons += name
    }

    uniquePatrons.forEach {
        patronGold[it] = 6.0
    }

    var orderCount = 0
    while (orderCount <= 9) {
        placeOrder(uniquePatrons.shuffled().first(),
                menuList.shuffled().first())
        println()
        orderCount++
    }
    bugai()
    displayPatronBalances()

}

private fun bugai() {
    val listPure = mutableListOf<String>()
    patronGold.forEach { patron, balance ->
        if (balance == 0.0)
            listPure += patron
    }
    listPure.forEach {
        patronGold.remove(it)
    }
}

private fun displayPatronBalances() {
    println("Patrons Balance:\n")
    patronGold.forEach { patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}

private fun printMenu() {
    println(helloString)
    var oldType = ""
    menuList.forEach {
        val (type, name, price) = it.split(',')
        if (type != oldType) {
            oldType = type
            println("           ~[$type]~")
        }
        println(addDot(name, price, helloString.length))
    }
    println()
}

fun performPurchase(price: Double, patronName: String): Boolean {
    val totalPurse = patronGold.getValue(patronName)
    if (totalPurse >= price) {
        patronGold[patronName] = totalPurse - price
        return true
    }
    return false
}

private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')

    if (performPurchase(price.toDouble(), patronName)) {
        val message = "$patronName buys a $name ($type) for $price."
        println(message)
        val phrase = if (name == "Dragon's Breath") {
            "$patronName exclaims: ${toDragonSpeak("AH, DELICIOUS $name!")}"
        } else {
            "$patronName says: Thanks for the $name!."
        }
        println(phrase)
    } else {
        val message = "$patronName can't buys a $name ($type) for $price."
        println(message)
    }
}

private fun toDragonSpeak(phrase: String) =
        phrase.replace(Regex("(?i)[aeiou]")) {
            when (it.value.toLowerCase()) {
                "a" -> "4"
                "e" -> "3"
                "i" -> "1"
                "o" -> "0"
                "u" -> "|_|"
                else -> it.value
            }
        }