package com.homework.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.homework.mobile.component.TabLayout
import com.homework.mobile.ui.theme.Iris
import com.homework.mobile.ui.theme.LemonYellow
import com.homework.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MobileTheme {
				Surface {
					LayoutPreview()
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LayoutPreview() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "Tab layout example",
						fontSize = 24.sp
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = Iris,
					titleContentColor = LemonYellow
				)
			)
		}
	) {
		Column(modifier = Modifier.padding(it)) {
			TabLayout()
		}
	}
}