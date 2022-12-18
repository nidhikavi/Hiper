package compose.lets.calculator.curren.livenetwork

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
