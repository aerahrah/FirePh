package com.example.fireph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ManageActivity : AppCompatActivity() {
    private lateinit var userArrayList : ArrayList<Categories>
    private lateinit var totalAmountText : TextView
    private lateinit var buttonExpenses : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        totalAmountText = findViewById(R.id.textAmount)
        getUserData()
    }

    private fun getUserData() {
        var totalAmount = 0.0
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("expenseHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            totalAmount += user!!.fireAmount!!.toFloat()
                        }
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("IncomeHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            totalAmount += user!!.fireAmount!!.toFloat()
                        }
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}