package com.smart.calculadora.states

import com.smart.calculadora.models.Resultados

data class ResultadosState(
    val listaResultados: List<Resultados> = emptyList()
)
