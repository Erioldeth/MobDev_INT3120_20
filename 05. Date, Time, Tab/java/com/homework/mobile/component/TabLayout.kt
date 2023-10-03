package com.homework.mobile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Apricot
import com.homework.mobile.ui.theme.Aquamarine
import com.homework.mobile.ui.theme.Coral

@Composable
fun TabLayout() {
	var tabIndex by remember { mutableStateOf(0) }
	val tabs = listOf("Date", "Time")

	Column(modifier = Modifier.fillMaxWidth()) {
		TabRow(
			selectedTabIndex = tabIndex,
			containerColor = Aquamarine
		) {
			tabs.forEachIndexed { index, title ->
				Tab(
					selected = (tabIndex == index),
					onClick = { tabIndex = index },
					selectedContentColor = Coral,
					unselectedContentColor = Apricot,
					text = {
						Text(
							text = title,
							fontSize = 20.sp
						)
					},
					icon = {
						when (index) {
							0 -> Icon(
								imageVector = calendar(),
								contentDescription = null
							)

							1 -> Icon(
								imageVector = clock(),
								contentDescription = null
							)
						}
					},
				)
			}
		}
		when (tabIndex) {
			0 -> DateScreen()
			1 -> TimeScreen()
		}
	}
}