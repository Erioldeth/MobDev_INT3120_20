package com.homework.mobdev

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val numPicker: NumberPicker = findViewById(R.id.numPicker)

		numPicker.minValue = 990
		numPicker.maxValue = 1000
		numPicker.value = 999

		numPicker.wrapSelectorWheel = false
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menu?.add("Khalai")
		menu?.add("Nerazim")
		menu?.add("Purifier")
		menu?.add("Tal'darim")
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val base = when (item.title) {
			"Khalai" -> "Aiur"
			"Nerazim" -> "Shakuras"
			"Purifier" -> "Cybros"
			"Tal'darim" -> "Slayn"
			else -> "Unknown"
		}
		Toast
			.makeText(this, "Base of the ${item.title} is $base", Toast.LENGTH_SHORT)
			.show()
		return super.onOptionsItemSelected(item)
	}
}