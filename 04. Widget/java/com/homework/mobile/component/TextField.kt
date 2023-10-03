package com.homework.mobile.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Cerise
import com.homework.mobile.ui.theme.Silver

@Composable
fun TextField(
	content: String,
	setContent: (String) -> Unit,
	hint: String,
	inputType: KeyboardType = KeyboardType.Text
) {
	var stateColor by remember { mutableStateOf(Silver) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(
				horizontal = 20.dp,
				vertical = 10.dp
			)
	) {
		BasicTextField(
			modifier = Modifier
				.fillMaxWidth()
				.onFocusChanged { stateColor = if (it.isFocused) Cerise else Silver },
			value = content,
			onValueChange = setContent,
			textStyle = TextStyle(fontSize = 18.sp),
			singleLine = true,
			keyboardOptions = KeyboardOptions(
				keyboardType = inputType,
				imeAction = ImeAction.Done
			),
			decorationBox = {
				Box {
					if (content.isEmpty()) {
						Text(
							text = hint,
							fontSize = 18.sp,
							color = Silver,
						)
					}
				}
				it()
			}
		)
		Divider(
			modifier = Modifier.fillMaxWidth(),
			thickness = 1.dp,
			color = stateColor
		)
	}
}