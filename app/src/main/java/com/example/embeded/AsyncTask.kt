////import android.os.AsyncTask
//import android.widget.TextView
//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL
//import java.io.InputStreamReader
//import java.io.BufferedReader
//
//class GetBalanceTask(private val textView: TextView) : AsyncTask<String, Void, String>() {
//
//    override fun doInBackground(vararg params: String?): String {
//        val cardNumber = params[0] ?: return "Error: Card number is empty"
//        val urlString = "http://192.168.131.163/embeded.php?card=$cardNumber" // Sesuaikan URL API
//        val url = URL(urlString)
//        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
//        try {
//            val inputStreamReader = InputStreamReader(urlConnection.inputStream)
//            val bufferedReader = BufferedReader(inputStreamReader)
//            val response = StringBuffer()
//
//            var inputLine = bufferedReader.readLine()
//            while (inputLine != null) {
//                response.append(inputLine)
//                inputLine = bufferedReader.readLine()
//            }
//
//            return response.toString()
//        } finally {
//            urlConnection.disconnect()
//        }
//    }
//
//    override fun onPostExecute(result: String?) {
//        super.onPostExecute(result)
//        if (result != null) {
//            try {
//                val jsonResponse = JSONObject(result)
//                val status = jsonResponse.getString("status")
//                if (status == "success") {
//                    val data = jsonResponse.getJSONObject("data")
//                    val balance = data.getString("saldo")
//                    textView.text = "Saldo: Rp $balance"
//                } else {
//                    textView.text = "Error: ${jsonResponse.getString("message")}"
//                }
//            } catch (e: Exception) {
//                textView.text = "Error parsing response"
//            }
//        } else {
//            textView.text = "Error fetching data"
//        }
//    }
//}
