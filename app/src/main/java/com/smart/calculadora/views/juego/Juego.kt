package com.smart.calculadora.views.juego

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.relay.compose.BoxScopeInstanceImpl.align
import com.smart.calculadora.models.Resultados
import com.smart.calculadora.models.room.ResultadosDatabaseDao
import com.smart.calculadora.viewmodels.ResultadosViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun TicTacToeGameBoard(
    modifier: Modifier = Modifier,
    viewModel: ResultadosViewModel,
    partidaId: Int
) {
    var board by remember { mutableStateOf(List(3) { MutableList(3) { "" } }) }
    var gameStarted by remember { mutableStateOf(false) }
    var currentPlayer by remember { mutableStateOf("X") }

    // Recuperar el estado de la partida
    val estadoPartida by viewModel.obtenerEstadoPartida(partidaId).collectAsState(initial = "F")

    // Actualizar el estado de inicio del juego según el estado de la partida
    gameStarted = estadoPartida == "J"

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // Reduce el padding para minimizar la separación entre el tablero y los bordes
        contentAlignment = Alignment.Center // Centra el tablero dentro del Box
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Filas del tablero
            for (row in 0..2) {
                TicTacToeRow {
                    for (col in 0..2) {
                        TicTacToeCell(
                            symbol = board[row][col],
                            enabled = gameStarted && board[row][col].isEmpty(),
                            onClick = {
                                if (board[row][col].isEmpty()) {
                                    board[row][col] = currentPlayer
                                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TicTacToeRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        content = content
    )
}

@Composable
fun TicTacToeCell(
    symbol: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (enabled) Color(0xFF0460A3) else Color.Gray)
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Text(
            text = symbol,
            fontSize = 64.sp,
            color = when (symbol) {
                "X" -> Color(0xFFBB535B)
                "O" -> Color.White
                else -> Color.Transparent
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview(widthDp = 430, heightDp = 1500)
@Composable
fun TicTacToeGameBoardPreview() {
    // Creación de un ViewModel ficticio para la previsualización
    val fakeDao = object : ResultadosDatabaseDao {
        override fun obtenerResultados(): Flow<List<Resultados>> {
            TODO("Not yet implemented")
        }

        override fun obtenerResultado(id: Int): Flow<Resultados> {
            TODO("Not yet implemented")
        }

        override suspend fun insertarPartida(resultados: Resultados) {
            TODO("Not yet implemented")
        }

        override suspend fun actualizarResultado(resultado: Resultados) {
            TODO("Not yet implemented")
        }

        override suspend fun borrarResultado(resultado: Resultados) {
            TODO("Not yet implemented")
        }

        override suspend fun iniciarPartida(id: Int) {}
        override suspend fun anularPartida(id: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun finalizarPartida(id: Int, ganador: String, punto: Int) {
            TODO("Not yet implemented")
        }

        override fun obtenerEstadoPartida(partidaId: Int): Flow<String> {
            return flowOf("J") // Estado ficticio
        }
    }

    val fakeViewModel = ResultadosViewModel(fakeDao)

    MaterialTheme {
        TicTacToeGameBoard(
            viewModel = fakeViewModel, // Se pasa el ViewModel ficticio
            partidaId = 1 // Se pasa un ID de partida ficticio
        )
    }
}