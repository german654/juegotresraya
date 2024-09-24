package com.smart.calculadora.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.calculadora.models.Resultados
import com.smart.calculadora.models.room.ResultadosDatabaseDao
import com.smart.calculadora.states.ResultadosState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ResultadosViewModel(
    private val dao: ResultadosDatabaseDao
) : ViewModel() {

    // Mantener el estado mutable de la vista
    private var state by mutableStateOf(ResultadosState())

    init {
        // Iniciar la recopilación de resultados en el estado
        viewModelScope.launch {
            dao.obtenerResultados().collectLatest { listaResultados ->
                state = state.copy(
                    listaResultados = listaResultados
                )
            }
        }
    }

    // Método para agregar un nuevo resultado (nueva partida)
    fun agregarResultado(resultado: Resultados) = viewModelScope.launch {
    }

    // Método para actualizar un resultado (cuando termina una partida)
    fun actualizarResultado(resultado: Resultados) = viewModelScope.launch {
        dao.actualizarResultado(resultado = resultado)
    }

    // Método para borrar un resultado (eliminar una partida)
    fun borrarResultado(resultado: Resultados) = viewModelScope.launch {
        dao.borrarResultado(resultado = resultado)
    }

    // Método para iniciar una partida (activar los botones del juego y guardar la partida)
    fun iniciarPartida(
        nombreJugador1: String,
        nombreJugador2: String,
        nombrePartida: String,
        partidaId: Int
    ) = viewModelScope.launch {
        // Crear un nuevo registro en la base de datos
        val nuevaPartida = Resultados(
            nombrePartida = nombrePartida,
            nombreJugador1 = nombreJugador1,
            nombreJugador2 = nombreJugador2,
            ganador = "Empate",
            punto = 0,
            estado = "J"
        )
        dao.insertarPartida(nuevaPartida)
    }

    // Método para finalizar una partida (actualizar el ganador y el puntaje)
    fun finalizarPartida(id: Int, ganador: String, punto: Int) = viewModelScope.launch {
        dao.finalizarPartida(id, ganador, punto)
    }

    // Método para anular una partida (volver al estado inicial)
    fun anularPartida(id: Int) = viewModelScope.launch {
        dao.anularPartida(id)
    }

    // Método para obtener el estado de una partida por su ID
    fun obtenerEstadoPartida(partidaId: Int) = dao.obtenerEstadoPartida(partidaId)
}
