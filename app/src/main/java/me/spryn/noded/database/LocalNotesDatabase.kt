package me.spryn.noded.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.spryn.noded.models.NoteModel
import me.spryn.noded.models.NotebookModel

@Database(entities = arrayOf(NoteModel::class), version = 1)
abstract class LocalNotesDatabase: RoomDatabase() {
    abstract fun localNotesDao(): LocalNotesDao
}