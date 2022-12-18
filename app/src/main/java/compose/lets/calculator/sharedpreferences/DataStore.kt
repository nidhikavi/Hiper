package compose.lets.calculator.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("pref")

class Board(private val context: Context) {
    companion object {
        val Board = stringPreferencesKey("board")
    }
    val getBoard: Flow<String> = context.datastore.data.map {
        it[Board] ?: ""
    }
    suspend fun saveBoard(board: String) {
        context.datastore.edit {
            it[Board] = board
        }
    }
}
