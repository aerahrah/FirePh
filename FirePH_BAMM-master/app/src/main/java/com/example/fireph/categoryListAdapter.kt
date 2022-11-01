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

class categoryListAdapter : AppCompatActivity() {
    fun append(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        System.out.println(element)
        list.add(element)
        return list.toTypedArray()
    }
    private lateinit var  userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Categories>
    private lateinit var totalAmountText : TextView
    private lateinit var buttonIncome : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        userRecyclerview = findViewById(R.id.categoryList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        totalAmountText = findViewById(R.id.textAmount)
        buttonIncome = findViewById(R.id.buttonIncome)

        userArrayList = arrayListOf<Categories>()
        getUserData()

        buttonIncome.setOnClickListener {
            val intent = Intent(this, CategoryIncomeAdapter::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }

    private fun getUserData() {
        var totalAmount = 0.0
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            totalAmount += user!!.fireAmount!!.toFloat()
                            userArrayList.add(user!!)
                        }
                        userRecyclerview.adapter = categoryAdapter(userArrayList)
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}