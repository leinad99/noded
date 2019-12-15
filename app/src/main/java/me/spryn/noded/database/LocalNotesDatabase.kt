package me.spryn.noded.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

@Database(entities = arrayOf(NoteModel::class), version = 3)
abstract class LocalNotesDatabase: RoomDatabase() {

    abstract val localNotesDao: LocalNotesDao

    companion object {
        @Volatile
        private var INSTANCE: LocalNotesDatabase? = null
        fun getInstance(context: Context): LocalNotesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        LocalNotesDatabase::class.java,
                        "notebooks_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}