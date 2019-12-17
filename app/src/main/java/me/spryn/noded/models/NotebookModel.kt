package me.spryn.noded.models

// @Entity(tableName = "notebooks_table")
data class NotebookModel(
    val ID: String,
    val title: String,
    val color: String,
    var lastModified: Long
)
