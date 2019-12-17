package me.spryn.noded.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity(tableName = "notes_table")
data class NoteModel(
    val ID: String,
    var title: String = "untitled",
    var text: String = "",
    var lastModified: Long = 0,
    var notebookID: String
    )