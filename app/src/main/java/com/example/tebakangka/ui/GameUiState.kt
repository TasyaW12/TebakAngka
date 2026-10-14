
package com.example.tebakangka.ui

data class GameUiState(
    val score: Int = 100,
    val attemptsLeft: Int = 10,
    val guessesMade: Int = 0,
    val userGuess: String = "",
    val message: String = "Tebak angka rahasia!",
    val lowerBound: Int = 0,
    val upperBound: Int = 100,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
    val isSurrendered: Boolean = false,
    val secretNumber: Int? = null
)
