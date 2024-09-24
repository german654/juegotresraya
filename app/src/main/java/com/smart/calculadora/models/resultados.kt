package com.smart.calculadora.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultados")
data class Resultados(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_resultado")
    val idResultado: Int = 1,

    @ColumnInfo(name = "nombre_partida")
    val nombrePartida: String = "Partida",

    @ColumnInfo(name = "nombre_jugador1")
    val nombreJugador1: String,

    @ColumnInfo(name = "nombre_jugador2")
    val nombreJugador2: String,

    @ColumnInfo(name = "ganador")
    val ganador: String = "Empate",

    @ColumnInfo(name = "punto")
    val punto: Int = 0,

    @ColumnInfo(name = "estado")
    val estado: String = "F"
)