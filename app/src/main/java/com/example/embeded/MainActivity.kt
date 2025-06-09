package com.example.embeded

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.activity.enableEdgeToEdge


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val homeButton: Button = findViewById(R.id.button2)
        val purchaseButton: Button = findViewById(R.id.button3)
        val profileButton: Button = findViewById(R.id.button4)
        val BookingButton: Button = findViewById(R.id.button8)

        // Default fragment saat pertama kali dibuka
        replaceFragment(HomeFragment())

        homeButton.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        purchaseButton.setOnClickListener {
            replaceFragment(PurchaseFragment())
        }

        profileButton.setOnClickListener {
            replaceFragment(Profile())
        }
        BookingButton.setOnClickListener {
            replaceFragment(Booking())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}
