package com.tarapogancev.notesbycats.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "notes")
class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String = "",
    var text: String = "",
    var timestamp: String = "",
    var pinned: Boolean = false,
    ) : Serializable {
}