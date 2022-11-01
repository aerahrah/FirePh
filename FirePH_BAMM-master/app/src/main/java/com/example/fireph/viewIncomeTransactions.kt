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

class viewIncomeTransactions : AppCompatActivity() {

    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<viewDataModel>
    private lateinit var buttonExpenses : Button
    private lateinit var totalAmountText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        val intent = intent
        val date : String? = intent.getStringExtra("DATE");
        if(date == null){
            finish()
            return;
        }

        userRecyclerview = findViewById(R.id.categoryList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        buttonExpenses = findViewById(R.id.buttonExpenses)
        totalAmountText = findViewById(R.id.textAmount)

        userArrayList = arrayListOf<viewDataModel>()
        getUserData(date)

        buttonExpenses.setOnClickListener {
            val intent = Intent(this, ViewTransactions::class.java)
            intent.putExtra("DATE", date)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
    }

    private fun getUserData(date: String) {
        var totalAmount = 0.0
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("IncomeHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        System.out.println("Exist")
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(viewDataModel::class.java)
                            if (user != null) {
                                if((user.fireDate).toString().contains(date)){
                                    totalAmount += user!!.fireAmount!!.toFloat()
                                    userArrayList.add(user)
                                }
                            }
                        }
                        userRecyclerview.adapter = MonthlyListAdapter(userArrayList)
                        totalAmountText.setText(totalAmount.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}