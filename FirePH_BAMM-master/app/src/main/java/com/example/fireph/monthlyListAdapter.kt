package com.example.fireph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class monthlyListAdapter(private val userList : ArrayList<viewDataModel>) : RecyclerView.Adapter<monthlyListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private lateinit var dbRef: DatabaseReference
        //        val empId: TextView = itemView.findViewById(R.id.tvempId)
        val fireAmount: TextView = itemView.findViewById(R.id.tvfireAmount)
        val fireCategory: TextView = itemView.findViewById(R.id.tvfireCategory)
        val fireDate: TextView = itemView.findViewById(R.id.tvfireDate)
        val fireType: TextView = itemView.findViewById(R.id.tvfireType)
        fun bind(property: viewDataModel, index: Int) {
            val button = itemView.findViewById<Button>(R.id.button)

            button.setOnClickListener { deleteItem(index)
                deleteItemDatabase(property)
            }
        }

        fun deleteItemDatabase(currentitem: viewDataModel){

            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            dbRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            dbRef.child(currentitem.empId.toString()).removeValue()
        }
    }
    fun deleteItem(index: Int){
        userList.removeAt(index)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.actity_list,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: monthlyListAdapter.MyViewHolder, position: Int) {

        val currentitem = userList[position]
        holder.bind(userList[position], position)
//        holder.empId.text = currentitem.empId
        holder.fireAmount.text = currentitem.fireAmount
        holder.fireCategory.text = currentitem.fireCategory
        holder.fireDate.text = currentitem.fireDate
        holder.fireType.text = currentitem.fireType
////        holder.category.text = currentitem.Category
////        System.out.println(holder.empId.text)
//        System.out.println(holder.fireAmount.text)
    }
    override fun getItemCount(): Int {

        return userList.size
    }

}