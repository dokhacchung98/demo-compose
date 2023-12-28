package com.alphavn.todoapp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alphavn.todoapp.model.NoteModel
import com.alphavn.todoapp.model.NoteStatus

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteModel)

    @Update
    suspend fun update(note: NoteModel)

    @Query("SELECT * FROM notes where status = :status")
    suspend fun getAllNotes(status: Int = NoteStatus.IN_PROGRESS.value): List<NoteModel>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: String): NoteModel?
}