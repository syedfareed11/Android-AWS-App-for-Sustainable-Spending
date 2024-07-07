package com.example.trial4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trial4.models.hisoryItem

class HistoryAdapter (val context: Context, val dynamoResponse: List<hisoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.historyitem,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dynamoResponse.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dynamoResponse[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(hisoryItem: hisoryItem) {


            itemView.findViewById<TextView>(R.id.tvResult).text = hisoryItem.result
            itemView.findViewById<TextView>(R.id.tvDate).text = hisoryItem.date
            Glide.with(context).load(hisoryItem.imageUrl).into(itemView.findViewById(R.id.imageView3))
        }
    }
}