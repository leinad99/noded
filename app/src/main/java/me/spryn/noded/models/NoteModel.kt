package me.spryn.noded.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteModel(
    @PrimaryKey val title: String,
    var text: String,
    var lastModified: Long,
    @PrimaryKey var notebookTitle: String
    )