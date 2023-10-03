package com.homework.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.homework.mobile.component.ExpIntentDemo
import com.homework.mobile.component.ImpIntentDemo

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }
	}
}


@Preview(showBackground = true)
@Composable
fun MainContent() {
	Column(modifier = Modifier.fillMaxWidth()) {
		var tabIndex by remember { mutableStateOf(0) }

		TabRow(selectedTabIndex = tabIndex) {
			listOf("Explicit", "Implicit").forEachIndexed { index, title ->
				Tab(
					selected = (tabIndex == index),
					onClick = { tabIndex = index },
					text = {
						Text(
							text = title,
							fontSize = 20.sp
						)
					},
				)
			}
		}
		when (tabIndex) {
			0 -> ExpIntentDemo()
			1 -> ImpIntentDemo()
		}
	}
}