
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
        _uiState.update { currentState ->
            currentState.copy(userGuess = guess)
        }
    }

    fun checkGuess() {
        val currentState = _uiState.value

        if (currentState.isGameOver) return

        val guess = currentState.userGuess.toIntOrNull()

        if (guess == null || guess !in 0..100) {
            _uiState.update {
                it.copy(
                    message = "Masukkan angka antara 0 dan 100!"
                )
            }
            return
        }

        val newAttemptsLeft = currentState.attemptsLeft - 1

        if (guess == secretNumber) {
            _uiState.update {
                it.copy(
                    attemptsLeft = newAttemptsLeft,
                    message = "Selamat! Tebakan kamu benar!",
                    isGameOver = true,
                    isWon = true
                )
            }
        } else {
            val newScore = (currentState.score - 10).coerceAtLeast(0)

            val hint = if (guess < secretNumber) {
                "Terlalu kecil!"
            } else {
                "Terlalu besar!"
            }

            val gameOver = newAttemptsLeft == 0 || newScore == 0

            _uiState.update {
                it.copy(
                    score = newScore,
                    attemptsLeft = newAttemptsLeft,
                    userGuess = "",
                    message = if (gameOver) {
                        "Permainan berakhir! Angka rahasianya $secretNumber."
                    } else {
                        hint
                    },
                    isGameOver = gameOver,
                    isWon = false
                )
            }
        }
    }

    fun resetGame() {
        secretNumber = generateSecretNumber()

        _uiState.value = GameUiState()
    }
}
