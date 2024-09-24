package com.smart.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.smart.calculadora.models.Resultados
import com.smart.calculadora.models.room.ResultadosDatabaseDao
import com.smart.calculadora.room.ResultadosDatabase
import com.smart.calculadora.ui.theme.CalculadoraTheme
import com.smart.calculadora.viewmodels.ResultadosViewModel
import com.smart.calculadora.views.body.TicTacToeBody
import com.smart.calculadora.views.header.TicTacToeHeader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            // Aplicación del tema de la calculadora
            CalculadoraTheme(darkTheme = isDarkTheme) {
                // Inicialización de la base de datos
                val database = Room.databaseBuilder(
                    this,
                    ResultadosDatabase::class.java,
                    "db_resultados"
                ).build()

                val dao = database.resultadosDao()
                val viewModel = ResultadosViewModel(dao)

                // Mostrar contenido principal
                MainScreen(viewModel = viewModel, onThemeChange = {
                    isDarkTheme = !isDarkTheme
                })
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultadosViewModel,
    onThemeChange: () -> Unit
) {
    // Estado de desplazamiento para habilitar el scroll vertical
    val scrollState = rememberScrollState()

    // Superficie principal que cubre toda la pantalla
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()  // Ocupa todo el ancho disponible
                .verticalScroll(scrollState)  // Habilita el desplazamiento vertical
                .padding(8.dp),  // Margen de 8dp en los bordes
            verticalArrangement = Arrangement.spacedBy(8.dp)  // Espaciado entre elementos
        ) {
            // Cabecera del juego
            TicTacToeHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                viewModel = viewModel,
                onThemeChange = onThemeChange
            )

            // Cuerpo principal del juego
            TicTacToeBody(
                modifier = Modifier
                    .fillMaxWidth()  // Asegura que ocupe todo el ancho
                    .wrapContentHeight(),  // Se ajusta según el contenido
                viewModel = viewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Falso DAO para el preview
    val fakeDao = object : ResultadosDatabaseDao {
        override fun obtenerResultados(): Flow<List<Resultados>> = flowOf(emptyList())
        override fun obtenerResultado(id: Int): Flow<Resultados> {
            TODO("Not yet implemented")
        }
        override suspend fun insertarPartida(resultados: Resultados) {}
        override suspend fun actualizarResultado(resultado: Resultados) {}
        override suspend fun borrarResultado(resultado: Resultados) {}
        override suspend fun iniciarPartida(id: Int) {}
        override suspend fun anularPartida(id: Int) {}
        override suspend fun finalizarPartida(id: Int, ganador: String, punto: Int) {}
        override fun obtenerEstadoPartida(partidaId: Int): Flow<String> = flowOf("J")
    }

    val fakeViewModel = ResultadosViewModel(fakeDao)

    // Preview del contenido principal
    CalculadoraTheme {
        MainScreen(viewModel = fakeViewModel, onThemeChange = {})
    }
}
