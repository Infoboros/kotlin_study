import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10

fun main(args: Array<String>) {

    placeOrder("shandy,Dragon's Breath,5.2")
    placeOrder("shandy,Dragon's Breath,5.2")
}

private fun performPurchase(price: Double): Boolean{
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

private fun displayBalance(){
    println("Player's purse balance: Gold: $playerGold, Silver: $playerSilver")
}

private fun placeOrder(menuData: String){
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("Madrigal speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')

    if (performPurchase(price.toDouble())) {
        val message = "Madrigal buys a $name ($type) for $price."
        println(message)
        val phrase = if (name == "Dragon's Breath") {
            "Madrigal exclaims: ${toDragonSpeak("AH, DELICIOUS $name!")}"
        }else{
            "Madrigal says: Thanks for the $name!."
        }
        println(phrase)
    }else{
        val message = "Madrigal can't buys a $name ($type) for $price."
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