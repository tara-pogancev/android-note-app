package com.tarapogancev.notesbycats.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.tarapogancev.notesbycats.model.Note

@Dao
interface MainDAO {

    @Insert(onConflict = REPLACE)
    fun insert(note: Note): Void

    @Query("SELECT * FROM notes ORDER BY pinned DESC, id DESC")
    fun getAll(): List<Note>

    @Query("UPDATE notes SET title= :title, text= :text WHERE id = :id")
    fun update(id: Int, title: String, text: String): Void

    @Delete
    fun delete(note: Note): Void

    @Query("UPDATE notes SET pinned= :pinned WHERE id = :id")
    fun updatePinned(id: Int, pinned: Boolean): Void

}