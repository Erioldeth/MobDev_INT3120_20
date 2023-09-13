package com.homework.mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.AshGray
import com.homework.mobile.ui.theme.Platinum
import com.homework.mobile.ui.theme.Silver

@Composable
fun Order(
	name: String,
	phone: String,
	layer: String,
	shape: String,
	toppings: SnapshotStateMap<String, Boolean>
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp)
	) {
		var ordered by remember { mutableStateOf(false) }

		val orderInfo = when {
			!ordered -> ""
			name.isEmpty() || phone.isEmpty() -> "Please provide name and phone number"
			layer.isEmpty() -> "Please choose layer"
			shape.isEmpty() -> "Please choose shape"
			else -> {
				val layerInfo = if (layer != "None") "with $layer layer " else ""

				var toppingInfo = toppings
					.filterValues { it }
					.keys
					.joinToString(", ")
				if (toppingInfo.isNotEmpty())
					toppingInfo =
						"${if (layerInfo.isEmpty()) "with" else "and"} $toppingInfo for topping(s)"

				"$name, $phone\n$shape pizza $layerInfo$toppingInfo".lowercase()
			}
		}

		Text(
			modifier = Modifier.padding(top = 10.dp),
			text = "Your ordering:",
			fontSize = 18.sp,
			color = Silver
		)
		Text(
			modifier = Modifier
				.fillMaxWidth()
				.heightIn(min = 144.dp)
				.padding(bottom = 5.dp)
				.background(color = Platinum)
				.padding(horizontal = 4.dp),
			text = orderInfo,
			fontSize = 20.sp,
			lineHeight = 24.sp,
			maxLines = 6,
		)
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			listOf("Exit", "SMS - PLACE YOUR ORDER").forEach {
				Button(
					onClick = { ordered = (it != "Exit") },
					colors = ButtonDefaults.buttonColors(
						containerColor = AshGray,
						contentColor = Color.Black
					),
					shape = RoundedCornerShape(corner = CornerSize(4.dp))
				) {
					Text(text = it)
				}
			}
		}
	}
}