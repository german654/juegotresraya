package com.smart.calculadora.views.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smart.calculadora.R
import com.smart.calculadora.viewmodels.ResultadosViewModel
import com.smart.calculadora.views.body.inter

@Composable
fun TicTacToeHeader(
    modifier: Modifier = Modifier,
    viewModel: ResultadosViewModel,
    onThemeChange: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0061A5)) // Color de fondo azul
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderTitle(title = "Material 2")
            HeaderIcons(
                screenNumber = 1,
                onThemeChange = onThemeChange
            ) // Indica el número de la pantalla
        }
    }
}

@Composable
fun HeaderTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontFamily = inter,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun HeaderIcons(
    screenNumber: Int,
    onThemeChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderIcon(painterResourceId = R.drawable.header_vector, onClick = onThemeChange)
        HeaderIcon(
            painterResourceId = R.drawable.header_vector2,
            screenNumber = screenNumber
        ) // Ícono para el nuemro de pantalla
        HeaderIcon(painterResourceId = R.drawable.header_vector3) // Ícono tes puntos
    }
}

@Composable
fun HeaderIcon(
    painterResourceId: Int,
    screenNumber: Int? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp) // Tamaño del ícono
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)

    ) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.fillMaxSize()
        )
        // Solo mostrar el número si `screenNumber` no es nulo
        if (screenNumber != null) {
            Text(
                text = screenNumber.toString(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center), // Alinea el texto al centro del ícono
                textAlign = TextAlign.Center
            )
        }
    }
}


