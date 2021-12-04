package com.smartchef.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smartchef.R

class TISearchAdapter : RecyclerView.Adapter<TISearchAdapter.ViewHolder>(), Filterable{

    var data = listOf<String>()
        set(value) {
            field = value
            filtered = value
            notifyDataSetChanged()
        }

    private var filtered = listOf<String>()

    var selections = hashMapOf<String, Boolean>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var exclusions = hashMapOf<String, Boolean>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener : OnItemClick? = null

    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.tag_layout, parent, false)
        return ViewHolder(view, selections, exclusions)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filtered[position])
    }

    override fun getItemCount(): Int {
        return filtered.size
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                filtered = filterResults.values as List<String>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.lowercase()
                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    data
                else
                    data.filter {
                        it.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }


    inner class ViewHolder constructor(itemView : View,
                                       private val selections: HashMap<String, Boolean>,
                                       private val exclusions: HashMap<String, Boolean>,
                                       ) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        lateinit var data : String
        val text : TextView = itemView.findViewById(R.id.text)
        val card : CardView = itemView.findViewById(R.id.card)

        init {
            card.setOnClickListener(this)
            card.setOnLongClickListener(this)
        }

        fun bind(data: String){
            this.data = data
            text.text = data
            refreshItem()
        }

        override fun onClick(v: View?) {
            if(exclusions[data] != true){
                selections[data] = selections[data] != true
            }else{
                exclusions[data] = false
            }
            refreshItem(true)
        }

        override fun onLongClick(v: View?): Boolean {
            if(selections[data] != true){
                exclusions[data] = exclusions[data] != true
            }else{
                selections[data] = false
            }
            refreshItem(true)
            return true
        }

        fun refreshItem(clicked:Boolean = false){
            if(selections[data] == true){
                card.setCardBackgroundColor(ContextCompat.getColor(card.context, R.color.green_500))
                text.setTextColor(Color.WHITE)
            } else if(exclusions[data] == true){
                card.setCardBackgroundColor(ContextCompat.getColor(card.context, R.color.orange_800))
                text.setTextColor(Color.WHITE)
            }else{
                card.setCardBackgroundColor(ContextCompat.getColor(card.context, R.color.cream_50))
                text.setTextColor(ContextCompat.getColor(card.context, R.color.green_800))
            }
            if(clicked)
                clickListener?.onItemClicked(data, selections[data] == true, exclusions[data] == true)
        }
    }

    interface OnItemClick{
        fun onItemClicked(data : String, selected : Boolean, excluded : Boolean)
    }

}