package com.example.embeded

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class PurchaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<EditText>(R.id.editTextAmount)
        val button = view.findViewById<Button>(R.id.buttonPurchase)

        button.setOnClickListener {
            val amount = editText.text.toString()
            Toast.makeText(requireContext(), "Pembayaran: Rp $amount", Toast.LENGTH_SHORT).show()
        }
    }
}
