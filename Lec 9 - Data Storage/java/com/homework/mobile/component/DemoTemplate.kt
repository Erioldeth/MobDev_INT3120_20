package com.homework.mobile.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoTemplate(
	text: String,
	onTextChange: (String) -> Unit,
	read: () -> Unit,
	write: () -> Unit,
	delete: () -> Unit,
) {
	val context = LocalContext.current

	LaunchedEffect(Unit) {
		withContext(Dispatchers.IO) {
			read()
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		TextField(
			value = text,
			textStyle = TextStyle(fontSize = 24.sp),
			onValueChange = onTextChange,
			keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
		)

		Spacer(modifier = Modifier.height(16.dp))

		Button(
			onClick = {
				write()
				Toast
					.makeText(context, "Text saved!", Toast.LENGTH_SHORT)
					.show()
			}
		) {
			Text(
				text = "Save",
				fontSize = 24.sp
			)
		}

		Spacer(modifier = Modifier.height(8.dp))

		Button(
			onClick = {
				onTextChange("<-!Unknown!->")
				delete()
				Toast
					.makeText(context, "Text deleted!", Toast.LENGTH_SHORT)
					.show()
			}
		) {
			Text(
				text = "Delete",
				fontSize = 24.sp
			)
		}
	}
}