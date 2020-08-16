import java.io.File
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()

val helloString = "*** Welcome to Taernyl's Folly ***"
val menuList = File("data/tavern-menu-data.txt")
        .readText()
        .split('\n')
        .sorted()

private fun addDot(first: String, last: String, size: Int): String{
    var dotString : String = first
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

    println(uniquePatrons)

    var orderCount = 0
    while (orderCount <= 9) {
        placeOrder(uniquePatrons.shuffled().first(),
                menuList.shuffled().first())
        println()
        orderCount++
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

private fun performPurchase(price: Double): Boolean {
    displayBalance()
    val totalPurse = playerGold + (playerSilver / 100.0)
    println("Total purse: $totalPurse")
    println("Purchasing item for $price")

    val remaningBalance = totalPurse - price
    println("Remaining balance: ${"%.2f".format(remaningBalance)}")

    if (remaningBalance <= 0)
        return false

    val remainingGold = remaningBalance.toInt()
    val remainingSilver = (remaningBalance % 1 * 100).roundToInt()
    playerGold = remainingGold
    playerSilver = remainingSilver
    displayBalance()

    return true
}

private fun displayBalance() {
    println("Player's purse balance: Gold: $playerGold, Silver: $playerSilver")
}

private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')

//    if (performPurchase(price.toDouble())) {
    val message = "$patronName buys a $name ($type) for $price."
    println(message)
    val phrase = if (name == "Dragon's Breath") {
        "$patronName exclaims: ${toDragonSpeak("AH, DELICIOUS $name!")}"
    } else {
        "$patronName says: Thanks for the $name!."
    }
    println(phrase)
//    } else {
//        val message = "Madrigal can't buys a $name ($type) for $price."
//        println(message)
//    }
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