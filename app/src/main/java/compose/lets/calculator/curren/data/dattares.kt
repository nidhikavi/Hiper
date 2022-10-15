package compose.lets.calculator.curren.data

data class dattares(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)