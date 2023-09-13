package com.homework.mobile.component

import android.app.DatePickerDialog
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateScreen() {
	val beginDate = LocalDate.of(2000, 1, 1)
	val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")

	var selectedDate by remember { mutableStateOf(beginDate.format(formatter)) }

	val picker = DatePickerDialog(
		LocalContext.current,
		{ _, year, month, dayOfMonth ->
			selectedDate = LocalDate.of(year, month + 1, dayOfMonth).format(formatter)
		},
		beginDate.year,
		beginDate.monthValue - 1,
		beginDate.dayOfMonth
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
				text = "Choose Date",
				fontSize = 30.sp
			)
		}
		Spacer(modifier = Modifier.size(20.dp))
		Text(
			text = buildAnnotatedString {
				append("Selected Date: ")
				withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
					append(selectedDate)
				}
			},
			fontSize = 30.sp
		)
	}
}