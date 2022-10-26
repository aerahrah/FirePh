package com.example.fireph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class categoryAdapter(private val userList : ArrayList<Categories>) : RecyclerView.Adapter<categoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.actity_list,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.empId.text = currentitem.empId
        holder.fireAmount.text = currentitem.fireAmount
        holder.fireCategory.text = currentitem.fireCategory
        holder.fireDate.text = currentitem.fireDate
        holder.fireType.text = currentitem.fireType
//        holder.category.text = currentitem.Category
//        System.out.println(holder.empId.text)
        System.out.println(holder.fireAmount.text)
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val empId : TextView = itemView.findViewById(R.id.tvempId)
        val fireAmount : TextView = itemView.findViewById(R.id.tvfireAmount)
        val fireCategory : TextView = itemView.findViewById(R.id.tvfireCategory)
        val fireDate : TextView = itemView.findViewById(R.id.tvfireDate)
        val fireType : TextView = itemView.findViewById(R.id.tvfireType)
//        val category : TextView = itemView.findViewById(R.id.categoryList)


    }

}