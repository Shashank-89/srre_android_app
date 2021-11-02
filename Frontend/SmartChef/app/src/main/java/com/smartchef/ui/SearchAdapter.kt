package com.smartchef.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartchef.R

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TagHolder(LayoutInflater.from(parent?.context).inflate(R.layout.tag_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    class TagHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val text : TextView = itemView.findViewById(R.id.text)
    }

}