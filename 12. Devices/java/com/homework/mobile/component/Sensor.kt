package com.homework.mobile.component

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun Sensor() {
	val context = LocalContext.current
	var temperature by remember { mutableStateOf(0F) }

	val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
	val thermometer = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
	val listener = object : SensorEventListener {
		override fun onSensorChanged(event: SensorEvent) {
			temperature = event.values[0]
		}

		override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
	}

	DisposableEffect(Unit) {
		if (thermometer != null)
			sensorManager.registerListener(listener, thermometer, SensorManager.SENSOR_DELAY_UI)
		else Toast
			.makeText(context, "Sensor is not available", Toast.LENGTH_LONG)
			.show()

		onDispose {
			if (thermometer != null)
				sensorManager.unregisterListener(listener)
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(
			text = "Temperature: $temperature°C",
			fontSize = 28.sp,
			color = when {
				temperature <= 0  -> Color.Blue
				temperature <= 20 -> Color.Cyan
				temperature <= 32 -> Color.Green
				else              -> Color.Red
			}
		)
	}
}