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

class CategoryIncomeAdapter : AppCompatActivity() {
    private lateinit var  userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Categories>
    private lateinit var totalAmountText : TextView
    private lateinit var buttonExpenses : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        userRecyclerview = findViewById(R.id.categoryList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        totalAmountText = findViewById(R.id.textAmount)
        buttonExpenses = findViewById(R.id.buttonExpenses)

        userArrayList = arrayListOf<Categories>()
        getUserData()

        buttonExpenses.setOnClickListener {
            val intent = Intent(this, CategoryListAdapter::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
    }

    private fun getUserData() {
        var totalAmount = 0.0
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("IncomeHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            totalAmount += user!!.fireAmount!!.toFloat()
                            userArrayList.add(user!!)
                        }
                        userRecyclerview.adapter = CategoryExpensesAdapter(userArrayList)
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }else{
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}