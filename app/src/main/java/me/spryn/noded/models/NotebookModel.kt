package me.spryn.noded.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity(tableName = "notebooks_table")
data class NotebookModel(
    val ID: String,
    val title: String,
    val color: String,
    var lastModified: Long
)
