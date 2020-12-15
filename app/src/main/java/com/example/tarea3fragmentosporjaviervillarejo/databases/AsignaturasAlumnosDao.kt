package com.example.tarea3fragmentosporjaviervillarejo.databases

import androidx.room.*

@Dao
interface AsignaturasAlumnosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: AsignaturasAlumnosCrossRef)

    @Transaction
    @Query("SELECT * FROM asignaturas WHERE asignaturasId = :asignaturasId")
    fun getAlumnos(asignaturasId: Int): Array<AsignaturasAlumnos>
}