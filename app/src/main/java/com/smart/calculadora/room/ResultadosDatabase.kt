package com.smart.calculadora.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smart.calculadora.models.Resultados
import com.smart.calculadora.models.room.ResultadosDatabaseDao

@Database(
    entities = [Resultados::class],
    version = 1,
    exportSchema = false
)
abstract class ResultadosDatabase : RoomDatabase() {
    abstract fun resultadosDao(): ResultadosDatabaseDao
}
