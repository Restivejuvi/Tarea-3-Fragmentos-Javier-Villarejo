package com.example.tarea3fragmentosporjaviervillarejo.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alumnos (
    @PrimaryKey val alumnosId: Int,
    val nombre: String?,
    val apellido: String?
)