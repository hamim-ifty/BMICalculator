package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Bmi(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BmiCategory(bmi: Float) {
    val (category, color) = when {
        bmi < 18.5 -> Pair("Underweight", Color(0xFF64B5F6)) // Light Blue
        bmi < 25 -> Pair("Normal weight", Color(0xFF81C784)) // Light Green
        bmi < 30 -> Pair("Overweight", Color(0xFFFFB74D)) // Light Orange
        else -> Pair("Obese", Color(0xFFE57373)) // Light Red
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BMI Categories:",
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // BMI Categories
        BmiCategoryRow("Underweight", "< 18.5", Color(0xFF64B5F6))
        BmiCategoryRow("Normal weight", "18.5 - 24.9", Color(0xFF81C784))
        BmiCategoryRow("Overweight", "25 - 29.9", Color(0xFFFFB74D))
        BmiCategoryRow("Obese", "≥ 30", Color(0xFFE57373))

        Spacer(modifier = Modifier.height(16.dp))

        // Current BMI Category
        if (bmi > 0) {
            Text(
                text = "Your category:",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = category,
                modifier = Modifier
                    .background(color, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun BmiCategoryRow(category: String, range: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = MaterialTheme.shapes.small)
        )
        Text(
            text = category,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        )
        Text(text = range)
    }
}

@Composable
fun Bmi(modifier: Modifier = Modifier) {
    var heightInput: String by remember { mutableStateOf("") }
    var weightInput: String by remember { mutableStateOf("") }

    val height = heightInput.toFloatOrNull() ?: 0.0f
    val weight = weightInput.toIntOrNull() ?: 0
    val formatter = DecimalFormat("0.00")
    val bmi = if (weight > 0 && height > 0) weight / (height * height) else 0.0f
    val bmiString = formatter.format(bmi)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth().padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = heightInput,
            onValueChange = { heightInput = it.replace(',','.') },
            label = { Text(stringResource(R.string.height_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it.replace(',','.') },
            label = { Text(stringResource(R.string.weight_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.result, bmiString),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )

        BmiCategory(bmi)
    }
}

@Preview(showBackground = true)
@Composable
fun BmiPreview() {
    MaterialTheme {
        Bmi()
    }
}