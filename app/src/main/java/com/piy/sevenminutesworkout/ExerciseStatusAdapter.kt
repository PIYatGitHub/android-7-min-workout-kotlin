package com.piy.sevenminutesworkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

/// items here will be the exercise names list
class ExerciseStatusAdapter(private val items: MutableList<String>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


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
    }

    override fun getItemCount(): Int {
        return items.size
    }
}