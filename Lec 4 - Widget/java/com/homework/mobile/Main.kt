package com.homework.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.component.CheckboxGroup
import com.homework.mobile.component.Order
import com.homework.mobile.component.RadioGroup
import com.homework.mobile.component.TextField
import com.homework.mobile.ui.theme.MobileTheme
import com.homework.mobile.ui.theme.RoyalBlue

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MobileTheme {
				Surface {
					LayoutPreview()
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "Essentials of Developing Android 5.0",
						fontSize = 20.sp
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = RoyalBlue,
					titleContentColor = Color.White
				)
			)
		}
	) {
		Column(
			modifier = Modifier
				.padding(it)
				.padding(top = 20.dp)
		) {
			val (name, setName) = remember { mutableStateOf("") }
			TextField(
				content = name,
				setContent = setName,
				hint = "Enter your name"
			)

			val (phone, setPhone) = remember { mutableStateOf("") }
			TextField(
				content = phone,
				setContent = setPhone,
				hint = "Enter your phone number",
				inputType = KeyboardType.Number
			)

			val (layer, setLayer) = remember { mutableStateOf("") }
			RadioGroup(
				options = listOf("Cheese", "2 x Cheese", "None"),
				selectedOption = layer,
				setSelectedOption = setLayer
			)

			val (shape, setShape) = remember { mutableStateOf("") }
			RadioGroup(
				options = listOf("Square", "Round"),
				selectedOption = shape,
				setSelectedOption = setShape
			)

			val toppings = remember { mutableStateMapOf<String, Boolean>() }
			CheckboxGroup(
				options = listOf("Peperoni", "Mushroom", "Veggies"),
				selectedOptions = toppings
			)

			Order(
				name = name,
				phone = phone,
				layer = layer,
				shape = shape,
				toppings = toppings
			)
		}
	}
}