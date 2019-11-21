package me.spryn.noded.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table", primaryKeys = ["title", "notebookTitle"])
data class NoteModel(
    val title: String,
    var text: String = "",
    var lastModified: Long = 0,
    var notebookTitle: String
    )