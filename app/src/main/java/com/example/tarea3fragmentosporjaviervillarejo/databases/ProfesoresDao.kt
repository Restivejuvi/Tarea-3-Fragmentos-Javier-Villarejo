package com.example.tarea3fragmentosporjaviervillarejo.databases

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ProfesoresDao {

    @Insert
    fun insertAll(vararg profesores: Profesores)

    @Insert
    fun insertAll(profesores: List<Profesores>)
}