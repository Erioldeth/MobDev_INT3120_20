package com.homework.mobile

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.homework.mobile.component.Bluetooth
import com.homework.mobile.component.Sensor
import com.homework.mobile.component.TelephonyService
import com.homework.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			ActivityCompat.requestPermissions(
				this,
				arrayOf(
					android.Manifest.permission.BLUETOOTH,
					android.Manifest.permission.BLUETOOTH_ADMIN,
					android.Manifest.permission.BLUETOOTH_SCAN,
					android.Manifest.permission.BLUETOOTH_CONNECT,
					android.Manifest.permission.BLUETOOTH_ADVERTISE,
					android.Manifest.permission.RECEIVE_SMS
				),
				0
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
	MobileTheme {
		var tabIndex by remember { mutableStateOf(0) }
		Column(
			modifier = Modifier.fillMaxWidth()
		) {
			ScrollableTabRow(selectedTabIndex = tabIndex) {
				listOf("Sensor", "Telephony\nService", "Bluetooth")
					.forEachIndexed { index, text ->
						Tab(
							selected = (index == tabIndex),
							onClick = { tabIndex = index },
							text = { Text(text = text, fontSize = 20.sp) }
						)
					}
			}
			when (tabIndex) {
				0 -> Sensor()
				1 -> TelephonyService()
				2 -> Bluetooth()
			}
		}
	}
}