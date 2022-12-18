package compose.lets.calculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LiveHistory::class], version = 1)
abstract class database : RoomDatabase() {

    abstract fun hisDao(): historyDao

    companion object {
        @Volatile
        private var Instance: database ? = null

        fun getInstance(context: Context): database {
            synchronized(this) {
                if (Instance == null) {
                    Instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            database::class.java,
                            "historyDatabase"
                        )
                            .build()
                }
            }
            return Instance!!
        }
    }
}
