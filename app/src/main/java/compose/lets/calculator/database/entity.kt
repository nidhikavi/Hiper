package compose.lets.calculator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class LiveHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "operation")
    val operation: String,
    @ColumnInfo(name = "result")
    val result: String

)
