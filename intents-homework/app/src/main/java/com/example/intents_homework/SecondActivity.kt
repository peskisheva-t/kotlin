package com.example.intents_homework

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Достаём переданный текст
        val text = intent.getStringExtra(MainActivity.EXTRA_TEXT)

        val tv = findViewById<TextView>(R.id.textView)
        tv.text = text ?: "Текст не передан"
    }
}