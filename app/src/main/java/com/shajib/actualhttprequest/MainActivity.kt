package com.shajib.actualhttprequest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.paulvarry.jsonviewer.JsonViewer
import com.shajib.actualhttprequest.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val apikey = "7c9IMmgxvAHxlgBmEVVF9bBV6hVHPmrL"
    private lateinit var jsonViewer: JsonViewer //a library for displaying JSON data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize JsonViewer from the layout
        jsonViewer = findViewById(R.id.jsonViewer)
        // Fetch regions from API

        // Set click listener for the button
        binding.btn1.setOnClickListener {
            getRegionsByApi()
            jsonViewer.expandJson()
        }
        binding.btn2.setOnClickListener {
            getRegionsByApi()
            jsonViewer.collapseJson()
        }

    }

    private fun getRegionsByApi() {
        RetrofitClient.getInstance()
            ?.getApi()
            ?.getRegions(apikey)
            ?.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        try {
                            val responseBody = response.body()?.string()
                            // Parse response body to JSONArray
                            val jsonArray = JSONArray(responseBody) //jsonViewer library can only handle JSONArray, So, we need to convert it to JSONArray
                            // Set JSON to JsonViewer
                            jsonViewer.setJson(jsonArray)
                            Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@MainActivity, "Error parsing JSON: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        try {
                            val errorBody = response.errorBody()?.string()
                            Toast.makeText(this@MainActivity, "Unsuccessful: $errorBody", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}
