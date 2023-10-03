package com.homework.mobile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homework.mobile.ui.theme.Cerise

@Composable
fun RadioGroup(
	options: List<String>,
	selectedOption: String,
	setSelectedOption: (String) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 10.dp)
	) {
		options.forEach { option ->
			Row(
				modifier = Modifier
					.padding(end = 10.dp)
					.selectable(
						selected = (option == selectedOption),
						onClick = { setSelectedOption(option) }
					),
				verticalAlignment = Alignment.CenterVertically
			) {
				RadioButton(
					modifier = Modifier.size(40.dp),
					selected = (option == selectedOption),
					onClick = { setSelectedOption(option) },
					colors = RadioButtonDefaults.colors(selectedColor = Cerise)
				)
				Text(text = option)
			}
		}
	}
}