package com.example.tarea3fragmentosporjaviervillarejo.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profesores(
    @PrimaryKey val profesoresId: Int,
    val nombre: String?,
    val apellido: String?
)