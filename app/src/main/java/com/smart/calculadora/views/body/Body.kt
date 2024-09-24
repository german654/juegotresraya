package com.smart.calculadora.views.body

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.smart.calculadora.R
import com.smart.calculadora.models.Resultados
import com.smart.calculadora.models.room.ResultadosDatabaseDao
import com.smart.calculadora.viewmodels.ResultadosViewModel
import com.smart.calculadora.views.juego.TicTacToeGameBoard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import androidx.compose.material3.*


@Composable
fun TicTacToeBody(
    modifier: Modifier = Modifier,
    viewModel: ResultadosViewModel
) {
    // Variables de estado locales para los nombres de los jugadores
    var player1Name by remember { mutableStateOf("") }
    var player2Name by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(0.dp)
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
    ) {
        // Sección de Jugadores
        PlayersSection {
            // Título del juego
            GameTitleContainer {
                TicTacToeTitle()
            }

            // Sección para el Jugador 1
            PlayerSection(
                playerLabel = "Jugador 1",
                playerName = player1Name,
                onNameChange = { player1Name = it }
            )

            // Sección para el Jugador 2
            PlayerSection(
                playerLabel = "Jugador 2",
                playerName = player2Name,
                onNameChange = { player2Name = it }
            )

            // Botones de Iniciar y Anular con acciones
            ControlButtons {
                ActionButton(
                    text = "Iniciar Partida",
                    viewModel = viewModel,
                    nombreJugador1 = "Jugador 1",
                    nombreJugador2 = "Jugador 2",
                    partidaId = 1,
                    esIniciarPartida = true
                )
                Spacer(modifier = Modifier.width(20.dp)) // Espacio entre los botones
                ActionButton(
                    text = "Anular Partida",
                    viewModel = viewModel,
                    nombreJugador1 = "Jugador 1",
                    nombreJugador2 = "Jugador 2",
                    partidaId = 1,
                    esIniciarPartida = false
                )
            }
        }

        // Tablero del juego
        GameBoard(viewModel = viewModel, partidaId = 1)

        // Sección de Puntajes
        ScoreSection {
            // Estados del Juego
            Scoreboard {
                // Turno del Jugador
                TurnBox("J1: Juan (x)")
                Spacer(modifier = Modifier.width(5.dp))
                // Ganador del Juego: J1 o J2 o empate
                WinnerBox("Empate")
            }
            // Tabla de puntajes
            // Titulo de la tabla de puntajes
            ScoreTable { ScoreTableTitle() }

            // Tabla de puntajes del partido
            // En qui deben de estar los resultados del partido
            MatchScoreContainer {
                MatchInfo(
                    matchTitle = "Partido 1",
                    matchResult = "Ganador: Empate",
                    labelScore = "P:",
                    labelStatus = "E:",
                    score = 0,
                    status = "T"
                )
            }
        }
    }
}


@Preview(widthDp = 430, heightDp = 1500)
@Composable
fun TicTacToeBodyPreview() {
    // Crear un dao ficticio que no hace nada
    val mockDao = object : ResultadosDatabaseDao {
        override fun obtenerResultados() = flowOf(emptyList<Resultados>())

        // Implementación ficticia del método obtenerResultado
        override fun obtenerResultado(id: Int) =
            flowOf(Resultados(id, "Partida $id", "Jugador1", "Jugador2", "Empate", 0, "Jugando"))

        override suspend fun actualizarResultado(resultado: Resultados) {}
        override suspend fun borrarResultado(resultado: Resultados) {}
        override suspend fun iniciarPartida(id: Int) {
            // Implementación ficticia vacía
        }

        override suspend fun insertarPartida(resultados: Resultados) {}
        override suspend fun finalizarPartida(id: Int, ganador: String, punto: Int) {}

        // Implementación correcta de obtenerEstadoPartida
        override fun obtenerEstadoPartida(partidaId: Int): Flow<String> {
            // Retornamos un Flow con el estado "Jugando"
            return flowOf("J")
        }

        override suspend fun anularPartida(id: Int) {}
    }

    // Crear el viewModel con el dao ficticio
    val mockViewModel = ResultadosViewModel(dao = mockDao)

    MaterialTheme {
        // Usamos el viewModel ficticio en la vista previa
        TicTacToeBody(viewModel = mockViewModel)
    }
}


// Sección de jugadores
@Composable
fun PlayersSection(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        content()
    }
}

// Sección de Jugadores
@Composable
fun PlayerSection(playerLabel: String, playerName: String, onNameChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(0.dp), // Agregar padding
        horizontalAlignment = Alignment.CenterHorizontally // Centrar contenido horizontalmente
    ) {
        // Label del jugador
        PlayerLabel(playerLabel)

        // Campo de texto para el nombre del jugador
        PlayerInfo(name = playerName, onNameChange = onNameChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInfo(name: String, onNameChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        placeholder = { Text(text = "Ingresa el nombre del jugador") },
        modifier = Modifier
            .fillMaxWidth(0.6f) // Ajustar al 90% del ancho para no ocupar toda la pantalla
            .padding(vertical = 8.dp) // Espacio vertical entre el label y el campo
            .height(56.dp), // Altura del TextField estándar
        singleLine = true,
        shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    )
}


@Composable
fun PlayerLabel(label: String) {
    Text(
        text = label,
        fontSize = 14.sp, // Tamaño de texto ajustado
        fontWeight = FontWeight.Bold, // Más peso para el texto
        modifier = Modifier.padding(vertical = 4.dp), // Espaciado ajustado
        textAlign = TextAlign.Center // Centrar el texto
    )
}

// Título del juego
@Composable
fun TicTacToeTitle(modifier: Modifier = Modifier) {
    Text(
        text = "3 en Raya",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold, // Negrita
        fontFamily = inter,
        textAlign = TextAlign.Center, // Centramos el texto
        modifier = modifier.fillMaxWidth()
    )
}

// Contenedor del título
@Composable
fun GameTitleContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Ajustamos padding para más flexibilidad
            .height(40.dp), // Un poco más alto para mejor visualización
        contentAlignment = Alignment.Center // Centramos el contenido dentro del contenedor
    ) {
        content()
    }
}

// Botones de acción
@Composable
fun ControlButtons(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun ActionButton(
    text: String,
    viewModel: ResultadosViewModel,
    nombreJugador1: String,
    nombreJugador2: String,
    partidaId: Int,
    esIniciarPartida: Boolean // Indica si es el botón de "Iniciar" o "Anular"
) {
    val nombrePartida = "Partida $partidaId" // Generar nombre de la partida según el partido

    Box(
        modifier = Modifier
            .background(Color(0xFF005CC9), shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 5.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                if (esIniciarPartida) {
                    viewModel.iniciarPartida(
                        nombreJugador1,
                        nombreJugador2,
                        nombrePartida,
                        partidaId
                    )
                } else {
                    viewModel.anularPartida(partidaId)
                }
            }
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = inter,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun GameBoard(modifier: Modifier = Modifier, viewModel: ResultadosViewModel, partidaId: Int) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp),
        contentAlignment = Alignment.Center // Centra todo el contenido dentro del Box
    ) {
        TicTacToeGameBoard(
            modifier = Modifier.fillMaxSize(), // Asegura que el tablero use todo el espacio disponible
            viewModel = viewModel,
            partidaId = partidaId
        )
    }
}

// Sección de puntajes
@Composable
fun ScoreSection(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 5.dp)
    ) {
        content()
    }
}

// Estados del Juego
@Composable
fun Scoreboard(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 30.dp), // Reducido el padding horizontal
        horizontalArrangement = Arrangement.Center, // Centramos el contenido
        verticalAlignment = Alignment.CenterVertically // Alineamos verticalmente
    ) {
        content()
    }
}


// Modificar el componente TurnBox para el turno del jugador
@Composable
fun TurnBox(playerName: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0xFFE0E0E0))
                .padding(3.dp)
        ) {
            CircleLabel("T:")
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = playerName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = inter,
                color = Color.Black
            )
        }
    }
}

// Modificar el componente WinnerBox para el ganador del juego
@Composable
fun WinnerBox(winner: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0xFFE0E0E0))
                .padding(3.dp)
        ) {
            CircleLabel("G:")
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = winner,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = inter,
                color = Color.Black
            )
        }
    }
}

// Componente para la letra dentro del círculo (T, G, P, E)
@Composable
fun CircleLabel(letter: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Título de la tabla de puntajes centrado
@Composable
fun ScoreTableTitle() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center // Centrar el texto
    ) {
        Text(
            text = "TABLA DE PUNTAJES",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

// Contenedor de la tabla de puntajes
@Composable
fun ScoreTable(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        content()
    }
}

// Contenedor de resultados de los partidos
@Composable
fun MatchScoreContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        content()
    }
}

// Componente que muestra el número del partido y el ganador en la izquierda
@Composable
fun MatchLeftInfo(matchTitle: String, matchResult: String) {
    Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = matchTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = inter
        )
        Text(
            text = matchResult,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF041BA3),
            fontFamily = inter
        )
    }
}

// Componente que muestra el puntaje y el estado del juego en la derecha
@Composable
fun MatchRightInfo(labelScore: String, labelStatus: String, score: Int, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center // Ajustamos el espacio entre los elementos
    ) {
        // Caja para el puntaje
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFE0E0E0))
                    .padding(3.dp)
            ) {
                CircleLabel(labelScore)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = score.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inter
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp)) // Reducimos el espacio entre las cajas

        // Caja para el estado del juego
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0xFFE0E0E0))
                    .padding(3.dp) // Espaciado interno ajustado
            ) {
                CircleLabel(labelStatus)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = status,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inter
                )
            }
        }
    }
}


// Contenedor de MatchInfo que combina ambos componentes
@Composable
fun MatchInfo(
    matchTitle: String,
    matchResult: String,
    labelScore: String,
    labelStatus: String,
    score: Int,
    status: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MatchLeftInfo(matchTitle, matchResult) // Información del partido y ganador
        Spacer(modifier = Modifier.width(20.dp))
        MatchRightInfo(labelScore, labelStatus, score, status)  // Puntaje y estado del juego
    }
}

