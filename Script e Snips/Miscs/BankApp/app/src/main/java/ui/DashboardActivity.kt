package com.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dto.UserDTO
import com.service.TransactionService

class DashvoardActivity : AppCompatActivity() {
    private lateinit var UserDTO: UserDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashvoard)
        UserDTO = intent.getParcelableExtra("userDTO") ?: throw IllegalStateException("UserDTO is not specified")

        val userInfoTextView: TextView = findViewById(R.id.userInfoTextView)
        userInfoTextView.text = "Welcome, ${userDTO.username}!"

        val generateCSVButton: Button = findViewById(R.id.generateCSVButton)
        generateCSVButton.setOnClickListener {
            try {
                val transactionService = TransactionService()
                transactionService.generateCSV(this, userDTO)
                Toast.makeText(this, "CSV generated", Toast.LENGTH_SHORT).show()
            }
            catch(Exception e) {
                Toast.makeText(this, e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
        }

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            finish()
            startActivity(LoginActivity.newIntent(this))
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}