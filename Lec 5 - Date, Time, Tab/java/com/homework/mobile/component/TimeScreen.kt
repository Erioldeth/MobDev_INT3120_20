package com.homework.mobile.component

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Aquamarine
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimeScreen() {
	val beginTime = LocalTime.MIDNIGHT
	val formatter = DateTimeFormatter.ofPattern("HH:mm")

	var selectedTime by remember { mutableStateOf(beginTime.format(formatter)) }

	val picker = TimePickerDialog(
		LocalContext.current,
		{ _, hourOfDay, minute ->
			selectedTime = LocalTime.of(hourOfDay, minute).format(formatter)
		},
		beginTime.hour,
		beginTime.minute,
		false
	)

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Button(
			onClick = { picker.show() },
			colors = ButtonDefaults.buttonColors(
				containerColor = Aquamarine,
				contentColor = Color.Black
			)
		) {
			Text(
				text = "Choose Time",
				fontSize = 30.sp
			)
		}
		Spacer(modifier = Modifier.size(20.dp))
		Text(
			text = selectedTime,
			fontSize = 30.sp
		)
	}
}
