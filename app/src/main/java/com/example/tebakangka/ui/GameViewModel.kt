
package com.example.tebakangka.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private var secretNumber: Int = generateSecretNumber()

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun generateSecretNumber(): Int {
        return Random.nextInt(0, 101)
    }

    fun updateUserGuess(guess: String) {
        _uiState.update { state ->
            state.copy(userGuess = guess)
        }
    }

    fun checkGuess() {
        val state = _uiState.value

        if (state.isGameOver) return

        val guess = state.userGuess.toIntOrNull()

        if (guess == null || guess !in 0..100) {
            _uiState.update {
                it.copy(
                    message = "Masukkan angka antara 0 dan 100!"
                )
            }
            return
        }

        // Mencegah tebakan di luar rentang petunjuk saat ini.
        if (guess <= state.lowerBound || guess >= state.upperBound) {
            _uiState.update {
                it.copy(
                    message = "Pilih angka di dalam rentang yang tersedia!"
                )
            }
            return
        }

        val newAttemptsLeft = state.attemptsLeft - 1
        val newGuessesMade = state.guessesMade + 1

        if (guess == secretNumber) {
            _uiState.update {
                it.copy(
                    attemptsLeft = newAttemptsLeft,
                    guessesMade = newGuessesMade,
                    message = "Selamat! Tebakan kamu benar!",
                    isGameOver = true,
                    isWon = true,
                    secretNumber = secretNumber
                )
            }
            return
        }

        val newScore = (state.score - 10).coerceAtLeast(0)
        val newLowerBound =
            if (guess < secretNumber) guess else state.lowerBound
        val newUpperBound =
            if (guess > secretNumber) guess else state.upperBound

        val gameOver = newAttemptsLeft == 0

        _uiState.update {
            it.copy(
                score = newScore,
                attemptsLeft = newAttemptsLeft,
                guessesMade = newGuessesMade,
                userGuess = "",
                lowerBound = newLowerBound,
                upperBound = newUpperBound,
                message = if (gameOver) {
                    "Kesempatan habis!"
                } else {
                    "Coba tebak lagi!"
                },
                isGameOver = gameOver,
                isWon = false,
                secretNumber = if (gameOver) secretNumber else null
            )
        }
    }

    fun surrenderGame() {
        val state = _uiState.value
        if (state.isGameOver) return

        _uiState.update {
            it.copy(
                isGameOver = true,
                isWon = false,
                isSurrendered = true,
                secretNumber = secretNumber,
                message = "Kamu menyerah!"
            )
        }
    }

    fun resetGame() {
        secretNumber = generateSecretNumber()
        _uiState.value = GameUiState()
    }
}
