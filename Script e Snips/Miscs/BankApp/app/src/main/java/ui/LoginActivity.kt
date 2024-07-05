package com.ui

import android.content.Context
import android.content.intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dto.UserDTO
import com.service.DatabaseService

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameField: EditField
    private lateinit var passwordField: EditField

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            try {
                val username = usernameField.text.toString()
                val password = passwordField.text.toString()
                val user = UserDTO(username, password)
                val result = DatabaseService.login(user)
                if (result) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
        }

        private fun showToast(message : String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        companion object {
            fun newIntent(context: Context, userDTO: UserDTO): Intent {
                return Intent(context, DashvoardActivity::class.java).apply {
                    putExtra("userDTO", userDTO)
                }
            }
        }
    }