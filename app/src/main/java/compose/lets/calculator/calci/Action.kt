package compose.lets.calculator.calci

sealed class Action{
    data class Number(val number: Int): Action()
    object Clear: Action()
    object Decimal: Action()
    object AC: Action()
    object Calculate: Action()
    data class Operation(val operation: compose.lets.calculator.calci.Operation): Action()
}
