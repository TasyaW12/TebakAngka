
package com.example.tebakangka.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color


@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val state by gameViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F2))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        if (!state.isGameOver) {
            Text(
                text = "TEBAK ANGKA",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF353631)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Temukan angka rahasia dari 0 sampai 100!",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF73776C)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Skor: ${state.score}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF353631)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sisa kesempatan: ${state.attemptsLeft}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF73776C)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "RENTANG ANGKA",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF55594F),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${state.lowerBound} — ${state.upperBound}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(20.dp))


                    OutlinedTextField(
                        value = state.userGuess,
                        onValueChange = { gameViewModel.updateUserGuess(it) },
                        label = { Text("Masukkan angka") },
                        placeholder = { Text("0–100") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF353631),
                            unfocusedTextColor = Color(0xFF353631),
                            cursorColor = Color(0xFF353631),
                            focusedBorderColor = Color(0xFF55594F),
                            unfocusedBorderColor = Color(0xFFD6D6D0),
                            focusedLabelColor = Color(0xFF55594F),
                            unfocusedLabelColor = Color(0xFF73776C),
                            focusedPlaceholderColor = Color(0xFF73776C),
                            unfocusedPlaceholderColor = Color(0xFF73776C)
                        )
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { gameViewModel.checkGuess() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF353631),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("TEBAK ANGKA")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { gameViewModel.surrenderGame() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF353631)
                        )
                    ) {
                        Text(
                            text = "MENYERAH",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = state.message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF73776C)
                    )
                }
            }
        } else {
            ResultScreen(
                score = state.score,
                guessesMade = state.guessesMade,
                secretNumber = state.secretNumber,
                isWon = state.isWon,
                isSurrendered = state.isSurrendered,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}



@Composable
private fun ResultScreen(
    score: Int,
    guessesMade: Int,
    secretNumber: Int?,
    isWon: Boolean,
    isSurrendered: Boolean,
    onPlayAgain: () -> Unit
) {
    val darkText = Color(0xFF353631)
    val secondaryText = Color(0xFF73776C)
    val lightCard = Color(0xFFE8E8E1)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isWon) "🏆" else "🎯",
                fontSize = 56.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when {
                    isWon -> "SELAMAT, KAMU MENANG!"
                    isSurrendered -> "KAMU MENYERAH"
                    else -> "PERMAINAN BERAKHIR"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = darkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = when {
                    isWon ->
                        "Kamu berhasil menemukan angka rahasia!"
                    isSurrendered ->
                        "Tidak apa-apa, ayo coba lagi!"
                    else ->
                        "Kesempatan habis. Coba lagi, ya!"
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = secondaryText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ANGKA RAHASIA",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = secondaryText
            )

            Text(
                text = secretNumber?.toString() ?: "-",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = lightCard
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "SKOR AKHIR",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = secondaryText
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$score / 100",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = darkText
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = lightCard
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "JUMLAH TEBAKAN",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = secondaryText
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "$guessesMade kali",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = darkText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = darkText,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "MAIN LAGI",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

