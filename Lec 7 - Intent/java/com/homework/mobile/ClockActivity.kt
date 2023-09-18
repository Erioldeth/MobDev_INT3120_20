package com.homework.mobile

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Cyan
import com.homework.mobile.ui.theme.ElectricBlue
import com.homework.mobile.ui.theme.HotPink
import com.homework.mobile.ui.theme.LimeGreen
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ClockActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { ClockContent() }
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ClockContent() {
	val context = LocalContext.current
	val beginTime = LocalTime.MIDNIGHT
	val formatter = DateTimeFormatter.ofPattern("hh:mm a")
	var selectedTime by remember { mutableStateOf(beginTime.format(formatter)) }

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "Clock Activity",
						fontSize = 24.sp,
						fontWeight = FontWeight.Bold
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = LimeGreen,
					titleContentColor = ElectricBlue,
				),
				navigationIcon = {
					IconButton(
						onClick = {
							Intent(context, MainActivity::class.java)
								.apply { putExtra("Time", selectedTime) }
								.let { context.startActivity(it) }
						}
					) {
						Icon(
							imageVector = Icons.Rounded.ArrowBack,
							contentDescription = null
						)
					}
				}
			)
		}
	) {
		Column(modifier = Modifier.padding(it)) {
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
						containerColor = HotPink,
						contentColor = Cyan
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
	}
}