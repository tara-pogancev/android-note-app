package com.tarapogancev.notesbycats.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "notes")
class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val timestamp: String = "",
    val pinned: Boolean = false,
    ) : Serializable {
}