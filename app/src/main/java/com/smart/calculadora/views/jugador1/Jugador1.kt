package com.smart.calculadora.jugador1

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText

/**
 * This composable was generated from the UI Package 'jugador1'.
 * Generated code; do not edit directly
 */
@Composable
fun Jugador1(
    modifier: Modifier = Modifier,
    jugador1Children: @Composable RelayContainerScope.() -> Unit = {}
) {
    TopLevel(modifier = modifier) {
        jugador1Children()
    }
}

@Preview(widthDp = 430, heightDp = 70)
@Composable
private fun Jugador1Preview() {
    MaterialTheme {
        RelayContainer {
            Jugador1(
                jugador1Children = {
                    RelayContainer(
                        mainAxisAlignment = MainAxisAlignment.Start,
                        arrangement = RelayContainerArrangement.Row,
                        padding = PaddingValues(
                            start = 16.0.dp,
                            top = 12.0.dp,
                            end = 16.0.dp,
                            bottom = 12.0.dp
                        ),
                        itemSpacing = 10.0,
                        strokeWidth = 1.0,
                        strokeColor = Color(
                            alpha = 255,
                            red = 0,
                            green = 0,
                            blue = 0
                        ),
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.Center,
                            offset = DpOffset(
                                x = 0.5.dp,
                                y = -0.5.dp
                            )
                        ).height(IntrinsicSize.Min).requiredWidth(267.0.dp)
                    ) {
                        RelayText(
                            content = "Juan",
                            fontSize = 16.0.sp,
                            fontFamily = inter,
                            height = 1.2102272510528564.em,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight(500.0.toInt())
                        )
                    }
                    RelayContainer(
                        backgroundColor = Color(
                            alpha = 255,
                            red = 255,
                            green = 255,
                            blue = 255
                        ),
                        arrangement = RelayContainerArrangement.Row,
                        padding = PaddingValues(
                            start = 2.0.dp,
                            top = 0.0.dp,
                            end = 2.0.dp,
                            bottom = 0.0.dp
                        ),
                        itemSpacing = 10.0,
                        modifier = Modifier.boxAlign(
                            alignment = Alignment.TopStart,
                            offset = DpOffset(
                                x = 96.0.dp,
                                y = 4.0.dp
                            )
                        ).height(IntrinsicSize.Min)
                    ) {
                        RelayText(
                            content = "Nombre Jugador 1:",
                            fontSize = 11.0.sp,
                            fontFamily = laila,
                            height = 1.549999930641868.em,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight(300.0.toInt())
                        )
                    }
                },
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun TopLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
