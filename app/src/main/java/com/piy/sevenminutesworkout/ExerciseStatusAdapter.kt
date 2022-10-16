package com.piy.sevenminutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

/// items here will be the exercise names list
class ExerciseStatusAdapter(private val items: MutableList<Boolean>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    val a = true
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem = view.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.item_exercise_status,
                    parent,
                    false
                )
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItem.text = "${position+1}"
        val maxTrueIndex= items.lastIndexOf(true) //0
        println("WHAT is the max true idx $maxTrueIndex")

        if(items[position]){ // 0
            if(position < maxTrueIndex) {
                //this means that the exercise has been completed already
                holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#ffffff"))
            }

            if(position == maxTrueIndex) {
                //this means this is the exercise we are on now.
                holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.imtem_cthin_colaccnt_border)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}