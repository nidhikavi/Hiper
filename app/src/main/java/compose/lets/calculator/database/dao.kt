package compose.lets.calculator.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface historyDao {

    @Insert
    suspend fun insertHistory(history: LiveHistory)

    @Update
    suspend fun updateHistory(history: LiveHistory)

    @Query("Delete from History")
    suspend fun deleteTable()

    @Query("select * from History")
    fun getHistory(): Flow<List<LiveHistory>>
}
