
package com.example.tebakangka.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TEBAK ANGKA",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Tebak angka rahasia dari 0 sampai 100!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Skor: ${gameUiState.score}",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Sisa kesempatan: ${gameUiState.attemptsLeft}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = gameUiState.userGuess,
                    onValueChange = {
                        gameViewModel.updateUserGuess(it)
                    },
                    label = {
                        Text("Masukkan angka")
                    },
                    placeholder = {
                        Text("0–100")
                    },
                    singleLine = true,
                    enabled = !gameUiState.isGameOver,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        gameViewModel.checkGuess()
                    },
                    enabled = !gameUiState.isGameOver,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("TEBAK!")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = gameUiState.message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                if (gameUiState.isGameOver) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = if (gameUiState.isWon) {
                            "Kamu menang!"
                        } else {
                            "Coba lagi di permainan berikutnya!"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            gameViewModel.resetGame()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("MAIN LAGI")
                    }
                }
            }
        }
    }
}
