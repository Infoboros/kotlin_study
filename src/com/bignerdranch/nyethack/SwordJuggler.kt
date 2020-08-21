package com.bignerdranch.nyethack

import java.lang.Exception
import java.lang.IllegalStateException

fun main(args: Array<String>) {
    var swordJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient){
        swordJuggling = 2
    }
    try {
        proficiencyCheck(swordJuggling)
        swordJuggling = swordJuggling!!.plus(1)
    }catch (e : Exception){
        println(e)
    }


    println("You juggle $swordJuggling swords!")
}

private fun proficiencyCheck(swordJuggling: Int?) {
    checkNotNull(swordJuggling, {"com.bignerdranch.nyethack.Player cannot juggle swords"})

}

class UnskilledSwordJugglerException():
        IllegalStateException("com.bignerdranch.nyethack.Player cannot juggle swords")