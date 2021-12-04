package com.smartchef.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smartchef.R
import com.smartchef.model.Recipe

class RecipeListAdapter : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    var data = listOf<Recipe>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener : OnItemClick? = null
    lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListAdapter.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var pos:Int = 0
        lateinit var recipe: Recipe
        val image: ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.name)
        val description : TextView = itemView.findViewById(R.id.desc)
        val calories : TextView = itemView.findViewById(R.id.cal)
        val protein : TextView = itemView.findViewById(R.id.protein)
        val fat : TextView = itemView.findViewById(R.id.fat)
        val sugar : TextView = itemView.findViewById(R.id.sugar)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(recipe : Recipe, pos: Int){
            this.pos = pos
            this.recipe = recipe

            Glide.with(image)
                .load(recipe.imgUrl).transition(DrawableTransitionOptions.withCrossFade())
                .into(image)

            val res = name.resources
            name.text = recipe.name
            description.text = recipe.description
            calories.text = res.getString(R.string.cal_q, recipe.cal.toInt())
            protein.text = res.getString(R.string.protein_q, recipe.protein.toInt())
            fat.text = res.getString(R.string.fat_q, recipe.totalFat.toInt())
            sugar.text = res.getString(R.string.sugar_q, recipe.sugar.toInt())

        }

        override fun onClick(v: View?) {
            clickListener?.onItemClick(recipe, pos)
        }

    }


    interface OnItemClick{
        fun onItemClick(recipe : Recipe, pos: Int)
    }
}