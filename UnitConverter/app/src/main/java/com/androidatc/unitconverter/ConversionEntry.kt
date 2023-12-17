package com.androidatc.unitconverter

data class ConversionEntry (
    val fromUnit: String,
    val toUnit: String,
    val result: Double,
    var timestamp: Long
)
