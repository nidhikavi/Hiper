package compose.lets.calculator.database

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DatabaseRepo(private val historyDao: historyDao) {
    val searchResults = MutableLiveData<List<LiveHistory>>()
    val completeHistory: Flow<List<LiveHistory>> = historyDao.getHistory()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun insertHistory(history: LiveHistory) {
        coroutineScope.launch {
            historyDao.insertHistory(history)
        }
    }
    fun updateHistory(history: LiveHistory) {
        coroutineScope.launch {
            historyDao.updateHistory(history)
        }
    }
    fun deleteHistory() {
        coroutineScope.launch {
            historyDao.deleteTable()
        }
    }
}
