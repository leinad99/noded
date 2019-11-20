package me.spryn.noded.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notebook_table")
data class NotebookModel(
    @PrimaryKey val title: String,
    val color: String,
    var lastModified: Long
)
