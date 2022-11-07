package com.example.fireph

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.math.RoundingMode
import java.text.DecimalFormat

class ManageActivity : AppCompatActivity() {
    private lateinit var userArrayList : ArrayList<Categories>
    private lateinit var totalAmountText2 : TextView
    private lateinit var totalAmountText1 : TextView
    private lateinit var textSaving : TextView
    private lateinit var textSavingPercentage : TextView
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
        textSaving = findViewById(R.id.textSaving)
        textSavingPercentage = findViewById(R.id.textSavingPer)
        getUserDataIncome(date)
        getUserDataExpense(date)

    }
    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
    private fun getSavingPercentage(amount1: Double, amount2: Double){
        var savingText = 0.0
        var savingTextPercentage = 0.0
        savingText = amount1 - amount2

        savingTextPercentage = (savingText/amount1)*100
        savingTextPercentage = roundOffDecimal(savingTextPercentage)
        if(savingText < 0){
            textSaving.setTextColor(Color.parseColor("#c92a2a"))
            textSavingPercentage.setTextColor(Color.parseColor("#c92a2a"))
        }else if(savingText > 0){
            textSaving.setTextColor(Color.parseColor("#087f5b"))
            textSavingPercentage.setTextColor(Color.parseColor("#087f5b"))
        }
        textSaving.setText(savingText.toString()+"php")
        textSavingPercentage.setText(savingTextPercentage.toString()+"%")
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
                        getSavingPercentage(totalAmount1, totalAmount2)
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
                        getSavingPercentage(totalAmount1, totalAmount2)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}