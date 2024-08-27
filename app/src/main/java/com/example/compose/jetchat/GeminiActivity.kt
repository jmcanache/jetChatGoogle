package com.example.compose.jetchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class GeminiActivity : AppCompatActivity() {

    val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyDEU__NfTLORjU0JBZceO3z3Uf0btSX72U"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                consumeWindowInsets = false
                setContent {
                    GeminiTest(
                        generativeModel = generativeModel
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiTest(generativeModel: GenerativeModel) {
    var input by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()

    Text(text = "Escribe un texto aqui")
    TextField(
        value = input,
        onValueChange = { input = it }
    )
    Button(
        onClick = {
            coroutine.launch {
                val prompt = input
                val response = generativeModel.generateContent(prompt)
                print(response.text)
            }
        }
    ) {
        Text(text = "Generar respuesta")
    }
}