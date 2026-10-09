package com.example.intents_homework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим элементы по их id из XML
        editText = findViewById(R.id.editText)
        val btnOpenSecond = findViewById<Button>(R.id.btnOpenSecond)
        val btnCall = findViewById<Button>(R.id.btnCall)
        val btnShare = findViewById<Button>(R.id.btnShare)

        // ====== 1. ЯВНЫЙ INTENT ======
        btnOpenSecond.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(EXTRA_TEXT, text)
            }
            startActivity(intent)
        }

        // ====== 2. НЕЯВНЫЙ INTENT — звонок ======
        btnCall.setOnClickListener {
            val phone = editText.text.toString().trim()
            if (!isValidPhone(phone)) {
                Toast.makeText(this, "Введите корректный номер телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Приложение звонков недоступно", Toast.LENGTH_SHORT).show()
            }
        }

        // ====== 3. СИСТЕМНЫЙ INTENT — поделиться ======
        btnShare.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(intent, "Поделиться через…"))
        }
    }

    // Проверка телефона: + и 5–15 цифр
    private fun isValidPhone(phone: String): Boolean =
        phone.matches(Regex("^\\+?[0-9]{5,15}$"))

    companion object {
        // Ключ, по которому передаём текст в SecondActivity
        const val EXTRA_TEXT = "EXTRA_TEXT"
    }
}