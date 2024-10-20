package com.example.bodymass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bodymass.ui.theme.BodymassTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodymassTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Bodymass()
                }
            }
        }
    }
}

class BodyMassViewModel : ViewModel() {
    var heightInput = mutableStateOf("")
    var weightInput = mutableStateOf("")
    val bmi: Float
        get() {
            val height = heightInput.value.toFloatOrNull() ?: 0f
            val weight = weightInput.value.toFloatOrNull() ?: 0f
            return if (height > 0 && weight > 0) weight / (height * height) else 0f
        }

    fun onHeightChange(newHeight: String) {
        heightInput.value = newHeight.replace(',', '.')
    }

    fun onWeightChange(newWeight: String) {
        weightInput.value = newWeight.replace(',', '.')
    }
}

@Composable
fun Bodymass(vm: BodyMassViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.body_mass_index),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = vm.heightInput.value,
            onValueChange = { vm.onHeightChange(it) },
            label = { Text(stringResource(R.string.height)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = vm.weightInput.value,
            onValueChange = { vm.onWeightChange(it) },
            label = { Text(stringResource(R.string.weight)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.result, String.format(Locale.getDefault(), "%.2f", vm.bmi))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BodymassTheme {
        Bodymass()
    }
}
