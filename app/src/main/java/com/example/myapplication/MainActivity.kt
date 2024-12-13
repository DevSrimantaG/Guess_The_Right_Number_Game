package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppUI()
                    }
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AppUI() {
    val hint = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val rand = remember { mutableStateOf(randNum()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Title Section
        Text(
            fontFamily = FontFamily.Cursive,
            fontSize = 32.sp,
            color = Color(0xFF4CAF50),
            text = "Guess The Right Number -",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Hint Text
        Text(
            text = hint.value,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display the guessed number in a card
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(80.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),



            ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    fontSize = 40.sp,
                    text = number.value,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Numeric Buttons in a Grid Layout
        Column(modifier = Modifier.fillMaxWidth()) {
            for (i in 1..3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (j in 1..3) {
                        val num = (i - 1) * 3 + j
                        if (num <= 9) {
                            Button(
                                onClick = { number.value += num.toString() },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(100.dp),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4CAF50)
                                )
                            ) {
                                Text(text = num.toString(), fontSize = 20.sp)
                            }
                        }
                    }
                }
            }

            // Last row with 0, Clear, and Check buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { number.value += "0" },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(text = "0", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        number.value = number.value.dropLast(1)
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFC107)
                    )
                ) {
                    Text(text = "⌫", fontSize = 20.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (number.value == ""){
                            return@Button
                        }
                        hint.value = check(rand.value, number.value.toInt())
                        number.value = ""
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Check", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Play Again Button
        Button(
            onClick = {
                rand.value = randNum()
                hint.value = ""
                number.value = ""
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
        ) {
            Text(text = "Play Again", fontSize = 20.sp, color = Color.White)
        }
    }
}

fun check(randomNum: Int, num: Int): String {
    return when {
        randomNum > num -> "$num is Too Low"
        randomNum < num -> "$num is Too High"
        else -> "Your Guess is Correct !"
    }
}

fun randNum(): Int {
    return Random.nextInt(1, 100)
}
