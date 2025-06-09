package com.example.embeded

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class Profile : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        emailTextView = view.findViewById(R.id.textViewEmail)

        // Load and display the saved email
        loadUserEmail()

        // Find buttons
        val loginButton = view.findViewById<Button>(R.id.button6)
        val registerButton = view.findViewById<Button>(R.id.button7)

        // Handle Login button click
        loginButton.setOnClickListener {
            val intent = Intent(requireContext(), Login::class.java).apply {
                putExtra("fragment_to_show", "login")
            }
            startActivity(intent)
        }

        // Handle Register button click
        registerButton.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java).apply {
                putExtra("fragment_to_show", "register")
            }
            startActivity(intent)
        }

        return view
    }

    private fun loadUserEmail() {
        val savedEmail = sharedPreferences.getString("username", null)
        if (savedEmail != null) {
            emailTextView.text = savedEmail
        } else {
            emailTextView.text = "Not logged in"
        }
    }
}