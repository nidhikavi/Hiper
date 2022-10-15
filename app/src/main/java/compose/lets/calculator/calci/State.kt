package compose.lets.calculator

import compose.lets.calculator.calci.Operation

data class State(
    val number1: String = "0",
    val number2: String = "",
    val operation: Operation?= null
)

data class LiveState(
    val answer: String = "0"
)

data class Item(
   val count : Int = 0
)

data class History(
    val history: String = "0"
)

open class StoreArray{
    var hisArray: List<String> = mutableListOf()
}

data class HisArray(
    var hisArray: MutableList<String> = mutableListOf()
)
