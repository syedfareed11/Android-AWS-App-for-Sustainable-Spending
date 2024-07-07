package com.example.trial4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KeyValueAdapter(private val responseMap: MutableMap<String, String>) : RecyclerView.Adapter<KeyValueAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textKey: TextView = itemView.findViewById(R.id.text_key)
        val textValue: TextView = itemView.findViewById(R.id.text_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_key_value, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return responseMap.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val key = responseMap.keys.elementAt(position)
        val value = responseMap[key]
        holder.textKey.text = key
        holder.textValue.text = value
    }

}
