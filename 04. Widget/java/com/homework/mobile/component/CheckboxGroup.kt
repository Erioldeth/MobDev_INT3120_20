package com.homework.mobile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homework.mobile.ui.theme.Cerise

@Composable
fun CheckboxGroup(
	options: List<String>,
	selectedOptions: SnapshotStateMap<String, Boolean>
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 10.dp)
	) {
		options.forEach { option ->
			var checked by remember { mutableStateOf(false) }
			selectedOptions[option] = checked

			Row(
				modifier = Modifier
					.padding(end = 10.dp)
					.selectable(
						selected = checked,
						onClick = { checked = !checked }
					),
				verticalAlignment = Alignment.CenterVertically
			) {
				Checkbox(
					modifier = Modifier.size(38.dp),
					checked = checked,
					onCheckedChange = { checked = it },
					colors = CheckboxDefaults.colors(checkedColor = Cerise)
				)
				Text(text = option)
			}
		}
	}
}