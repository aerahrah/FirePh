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
    private lateinit var totalAmountText2 : TextView
    private lateinit var totalAmountText1 : TextView
    private lateinit var buttonExpenses : Button
    var totalAmount1 = 0.0
    var totalAmount2 = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        val date : String? = intent.getStringExtra("DATE");
        if(date == null){
            finish()
            return;
        }
        totalAmountText2 = findViewById(R.id.textAmount2)
        totalAmountText1 = findViewById(R.id.textAmount1)
        getUserDataIncome(date)
        getUserDataExpense(date)
        getSavingPercentage(totalAmount1, totalAmount2)
    }
    private fun getSavingPercentage(amount1: Double, amount2: Double){

    }
    private fun getUserDataIncome(date: String) {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("IncomeHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        System.out.println("Exist")
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(Categories::class.java)
                            if (user != null) {
                                if ((user.fireDate).toString().contains(date)) {
                                    totalAmount1 += user!!.fireAmount!!.toFloat()
                                }
                            }
                        }
                        totalAmountText1.setText(totalAmount1.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
    private fun getUserDataExpense(date: String) {

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        System.out.println("Exist")
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            if (user != null) {
                                if((user.fireDate).toString().contains(date)){
                                    totalAmount2 += user!!.fireAmount!!.toFloat()
                                }
                            }
                        }
                        totalAmountText2.setText(totalAmount2.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}