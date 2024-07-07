package com.example.trial4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trial4.models.hisoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val TAG = "HistoryActivity"
private const val BASE_URL = "**"
class HistoryActivity : AppCompatActivity() {

    //private lateinit var historyItems : MutableList<hisoryItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyItems = mutableListOf<hisoryItem>()

        val hRecyclerView : RecyclerView= findViewById(R.id.historyrecyclerview)
        hRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = HistoryAdapter(this,historyItems)
        hRecyclerView.adapter = adapter

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val historyService = retrofit.create(HistoryService::class.java)
        historyService.getHistory().enqueue(object : Callback<List<hisoryItem>>{
            override fun onResponse(
                call: Call<List<hisoryItem>>,
                response: Response<List<hisoryItem>>
            ) {

                historyItems.addAll(response.body() as List<hisoryItem>)
                Log.i(TAG,"response is $response")
                Log.i(TAG,"history items variable is $historyItems")
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<hisoryItem>>, t: Throwable) {

            }

        })


    }

}