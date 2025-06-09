package com.example.embeded

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {

    private lateinit var saldoTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflasi layout fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Ambil referensi ke TextView tempat saldo akan ditampilkan
        saldoTextView = view.findViewById(R.id.textView5)

        // Asumsikan Anda sudah memiliki nomor kartu pengguna
        val cardNumber = "635C7E0F"

        // Panggil hanya sekali dengan dua parameter
        fetchBalance(cardNumber)

        // Ambil saldo dengan memanggil AsyncTask
         return view
    }

    private fun fetchBalance(cardNumber: String){
        CoroutineScope(Dispatchers.IO).launch{
            try {


                val urlString = "http://192.168.102.163/embeded/get_saldo.php?card=$cardNumber"
                val url = URL(urlString)
                val urlConnection = url.openConnection() as HttpURLConnection
                val response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                Log.e("JSON_RESPONSE", response) // 4 adalah indentasi supaya rapi

                withContext(Dispatchers.Main) {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    if (status == "success") {
                        val data = jsonResponse.getJSONObject("data")
                        Log.d("DATA_OBJECT", data.toString())
                        val balance = data.getString("saldo")
                        saldoTextView.text = "Saldo: Rp $balance"
                    } else {
                        saldoTextView.text = "Error: ${jsonResponse.getString("message")}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    saldoTextView.text = "Gagal mengambil data: ${e.message}"
                    Log.e("FETCH_ERROR", "Exception saat fetch: ", e)
                }
            }

        }
    }
}
