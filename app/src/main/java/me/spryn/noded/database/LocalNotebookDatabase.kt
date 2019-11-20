package me.spryn.noded.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.spryn.noded.models.NotebookModel

@Database(entities = arrayOf(NotebookModel::class), version = 1)
abstract class LocalNotebookDatabase : RoomDatabase() {

    abstract val localNotebookDao: LocalNotebookDao

    companion object {
        @Volatile
        private var INSTANCE: LocalNotebookDatabase? = null
        fun getInstance(context: Context): LocalNotebookDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalNotebookDatabase::class.java,
                        "notebooks_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}