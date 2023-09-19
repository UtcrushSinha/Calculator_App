package com.example.calculator_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator_app.ui.theme.Calculator_AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    InputText()
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun InputText(){


        var text by remember { mutableStateOf("0") }
        var operator by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .height(50.dp)){
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            calculate(text, operator) { result ->
                                text = result
                            }
                            keyboardController?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        textAlign = TextAlign.End)


                )

            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("7") { appendInput("7", text) { newText -> text = newText } }
                CalculatorButton("8") { appendInput("8", text) { newText -> text = newText } }
                CalculatorButton("9") { appendInput("9", text) { newText -> text = newText } }
                CalculatorButton("/") {
                    setOperator(
                        "/",
                        text,
                        { newText -> text = newText },
                        { newOperator -> operator = newOperator })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("4") { appendInput("4", text) { newText -> text = newText } }
                CalculatorButton("5") { appendInput("5", text) { newText -> text = newText } }
                CalculatorButton("6") { appendInput("6", text) { newText -> text = newText } }
                CalculatorButton("*") {
                    setOperator(
                        "*",
                        text,
                        { newText -> text = newText },
                        { newOperator -> operator = newOperator })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("1") { appendInput("1", text) { newText -> text = newText } }
                CalculatorButton("2") { appendInput("2", text) { newText -> text = newText } }
                CalculatorButton("3") { appendInput("3", text) { newText -> text = newText } }
                CalculatorButton("-") {
                    setOperator(
                        "-",
                        text,
                        { newText -> text = newText },
                        { newOperator -> operator = newOperator })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("C") {
                    clear({ newText -> text = newText }, { newOperator -> operator = newOperator })
                }

                CalculatorButton("0") { appendInput("0", text) { newText -> text = newText } }
                CalculatorButton("=") {
                    calculate(text, operator) { result ->
                        text = result
                    }
                    keyboardController?.hide()
                }
                CalculatorButton("+") {
                    setOperator(
                        "+",
                        text,
                        { newText -> text = newText },
                        { newOperator -> operator = newOperator })
                }
            }
        }
        }




    





 fun appendInput(value: String, currentText: String, onTextChange: (String) -> Unit) {
     val newText = if (currentText == "0" || currentText.isEmpty()) {
         value
     } else {
         currentText + value
     }
     onTextChange(newText)
 }


 fun setOperator(op: String, currentText: String, onTextChange: (String) -> Unit, onOperatorChange: (String) -> Unit) {
    if (currentText.isNotEmpty() && currentText != "0") {
        val lastChar = currentText.last().toString()
        if (lastChar !in arrayOf("+", "-", "*", "/")) {
            appendInput(op, currentText, onTextChange)
            onOperatorChange(op)
        } else {

            val newText = currentText.dropLast(1) + op
            onTextChange(newText)
            onOperatorChange(op)
        }
    }
}


 fun calculate(currentText: String, operator: String, onTextChange: (String) -> Unit) {
    if (currentText.isNotEmpty() && currentText != "0") {
        try {
            val result = when (operator) {
                "+" -> currentText.split("+").map { it.toDouble() }.sum()
                "-" -> currentText.split("-").map { it.toDouble() }.reduce { acc, value -> acc - value }
                "*" -> currentText.split("*").map { it.toDouble() }.reduce { acc, value -> acc * value }
                "/" -> currentText.split("/").map { it.toDouble() }.reduce { acc, value -> acc / value }
                else -> currentText.toDouble()
            }
            onTextChange(result.toString())
        } catch (e: Exception) {

            onTextChange("Error")
        }
    }
}


 fun clear(onTextChange: (String) -> Unit, onOperatorChange: (String) -> Unit) {
    onTextChange("0")
    onOperatorChange("")
}

@Composable
fun CalculatorButton(text: String,onClick: () -> Unit){
    
    Button(onClick = onClick,
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp)) {
        Text(text = text, fontSize = 20.sp)


        
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Calculator_AppTheme {
        Calculator()
    }
}