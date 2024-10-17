package com.example.roomdbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var etName : EditText
    lateinit var etEmail : EditText
    lateinit var btnInsert : Button
    lateinit var btnView : Button
    lateinit var tvResult : TextView

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        btnInsert = findViewById(R.id.btnInsert)
        btnView = findViewById(R.id.btnView)
        tvResult = findViewById(R.id.tvResult)

        // Create database and repository
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())

        // ViewModel
        userViewModel = ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]

        // Insert user
        btnInsert.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val user = User(name = name, email = email)
                userViewModel.insertUser(user)
                Toast.makeText(this, "User inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill out all details", Toast.LENGTH_SHORT).show()
            }
        }

        // View all users
        btnView.setOnClickListener {
            userViewModel.getAllUsers { users ->
                if (users.isNotEmpty()) {
                    val result = StringBuilder()
                    users.forEach{user ->
                        result.append("ID: ${user.id}, Name: ${user.name}, Email: ${user.email}\n")
                    }
                    tvResult.text = result.toString()
                } else {
                    tvResult.text = "No users found."
                }
            }
        }
    }
}