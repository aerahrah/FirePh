package com.example.fireph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class pieListAdapter(private val userList: ArrayList<pieAttributes>) : RecyclerView.Adapter<pieListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pielistview,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        val amount = (currentItem.amount!! * 100.0).roundToInt().toDouble() / 100.0
        holder.yourImageView.setColorFilter(currentItem.colors!!)
        holder.name.text = currentItem.name.toString()
        holder.amount.text = amount.toString()+"php"
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tvName)
        val yourImageView: ImageView  = itemView.findViewById(R.id.imageView2)
        val amount : TextView = itemView.findViewById(R.id.tvAmount)
    }

}