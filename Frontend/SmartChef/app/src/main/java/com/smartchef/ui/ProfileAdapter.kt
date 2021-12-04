package com.smartchef.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.smartchef.R
import com.smartchef.model.Profile


class ProfileAdapter(context: Context, resource: Int) : ArrayAdapter<Profile>(context, resource) {

    var data = listOf<Profile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Profile? {
        return data[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // convertView which is recyclable view
        val view: TextView = convertView as TextView?
            ?: LayoutInflater.from(context).inflate(R.layout.profile_dropdown, parent, false) as TextView

        view.text = getItem(position)?.name
        return view
    }
//
//    lateinit var layoutInflater: LayoutInflater
//    var clickListener : OnItemClick? = null
//
//    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
//
//        lateinit var profile: Profile
//        val text : TextView = itemView.findViewById(R.id.profile_item)
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View?) {
//            clickListener?.onItemClick(profile)
//        }
//
//        fun bind(profile: Profile) {
//            text.text = profile.name
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        layoutInflater = LayoutInflater.from(parent.context)
//        val view = layoutInflater.inflate(R.layout.profile_dropdown, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(data[position])
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    interface OnItemClick{
//        fun onItemClick(profile: Profile)
//    }
//
//    override fun getItem(position: Int): Any {
//        TODO("Not yet implemented")
//    }
}