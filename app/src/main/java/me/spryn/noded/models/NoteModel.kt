package me.spryn.noded.models

// @Entity(tableName = "notes_table")
data class NoteModel(
    val ID: String,
    var title: String = "",
    var text: String = "",
    var lastModified: Long = 0,
    var notebookID: String
)