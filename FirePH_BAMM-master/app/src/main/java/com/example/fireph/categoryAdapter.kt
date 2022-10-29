package com.example.fireph

import android.app.AlertDialog
import android.util.Property
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class categoryAdapter(private val userList : ArrayList<Categories>) : RecyclerView.Adapter<categoryAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private lateinit var dbRef: DatabaseReference
        val empId: TextView = itemView.findViewById(R.id.tvempId)
        val fireAmount: TextView = itemView.findViewById(R.id.tvfireAmount)
        val fireCategory: TextView = itemView.findViewById(R.id.tvfireCategory)
        val fireDate: TextView = itemView.findViewById(R.id.tvfireDate)
        val fireType: TextView = itemView.findViewById(R.id.tvfireType)
        fun bind(property: Categories, index: Int) {
            val button = itemView.findViewById<Button>(R.id.button)

            button.setOnClickListener { deleteItem(index)
             deleteItemDatabase(property)
            }
        }

        fun deleteItemDatabase(currentitem: Categories){

            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            dbRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            dbRef.child(currentitem.empId.toString()).removeValue()
        }
    }
    fun deleteItem(index: Int){
        userList.removeAt(index)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.actity_list,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]
        holder.bind(userList[position], position)
        holder.empId.text = currentitem.empId
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