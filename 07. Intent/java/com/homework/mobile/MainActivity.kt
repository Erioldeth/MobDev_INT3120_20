package com.homework.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.homework.mobile.component.ExpIntentDemo
import com.homework.mobile.component.ImpIntentDemo
import com.homework.mobile.ui.theme.BurntOrange
import com.homework.mobile.ui.theme.Lavender
import com.homework.mobile.ui.theme.Peach
import com.homework.mobile.ui.theme.Teal

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainContent() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "Intent Demo",
						fontSize = 24.sp,
						fontWeight = FontWeight.Bold
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = Peach,
					titleContentColor = BurntOrange,
				)
			)
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(it)
		) {
			TabLayout()
		}
	}
}

@Composable
fun TabLayout() {
	var tabIndex by remember { mutableStateOf(0) }
	val tabs = listOf("Explicit", "Implicit")

	Column(modifier = Modifier.fillMaxWidth()) {
		TabRow(
			selectedTabIndex = tabIndex,
			containerColor = Lavender,
			indicator = {
				TabRowDefaults.Indicator(
					modifier = Modifier.tabIndicatorOffset(it[tabIndex]),
					color = Teal
				)
			}
		) {
			tabs.forEachIndexed { index, title ->
				Tab(
					selected = (tabIndex == index),
					onClick = { tabIndex = index },
					selectedContentColor = Teal,
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