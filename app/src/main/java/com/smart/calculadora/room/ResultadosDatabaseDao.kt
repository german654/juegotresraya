package com.smart.calculadora.models.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.smart.calculadora.models.Resultados
import kotlinx.coroutines.flow.Flow


@Dao
interface ResultadosDatabaseDao {

    // Obtener todos los resultados de las partidas
    @Query("SELECT * FROM resultados")
    fun obtenerResultados(): Flow<List<Resultados>>

    // Obtener un resultado específico por ID
    @Query("SELECT * FROM resultados WHERE id_resultado = :id")
    fun obtenerResultado(id: Int): Flow<Resultados>

    // Insertar una nueva partida
    @Insert
    suspend fun insertarPartida(resultados: Resultados)

    // Actualizar los detalles de una partida (puntaje, ganador, estado)
    @Update
    suspend fun actualizarResultado(resultado: Resultados)

    // Borrar una partida
    @Delete
    suspend fun borrarResultado(resultado: Resultados)

    // Actualizar el estado de la partida a "Jugando" al iniciar
    @Query("UPDATE resultados SET estado = 'Jugando' WHERE id_resultado = :id")
    suspend fun iniciarPartida(id: Int)

    // Actualizar el estado de la partida a "Anulado" si se cancela
    @Query("UPDATE resultados SET estado = 'Anulado', punto = 0 WHERE id_resultado = :id")
    suspend fun anularPartida(id: Int)

    // Actualizar el ganador y el puntaje una vez que uno de los jugadores gana
    @Query("UPDATE resultados SET ganador = :ganador, punto = :punto, estado = 'Finalizado' WHERE id_resultado = :id")
    suspend fun finalizarPartida(id: Int, ganador: String, punto: Int)

    // Obtener el estado de una partida específica
    @Query("SELECT estado FROM resultados WHERE id_resultado = :partidaId")
    fun obtenerEstadoPartida(partidaId: Int): Flow<String>
}
