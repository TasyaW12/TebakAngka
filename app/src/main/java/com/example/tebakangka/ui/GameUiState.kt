package com.example.tebakangka.ui

data class GameUiState(
    val score: Int = 100,
    val attemptsLeft: Int = 10,
    val userGuess: String = "",
    val message: String = "Tebak angka dari 0 sampai 100!",
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
)