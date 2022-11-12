package com.example.fireph

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class algorithmActivity : AppCompatActivity() {
    //    var recyclerView: RecyclerView? = null
    private var saveButton: Button? = null
    private var incomeLastMonth: TextView?=null
    private var predictedtotalsavings: TextView?=null
    private var expensesLastMonth: TextView?=null
    private var predictedtotalexpenses: TextView?=null
//    private var totalAm1:Double = 0.0
//    private var totalAm2:Double = 0.0
    var url = "https://fireph.herokuapp.com/forecast"
    val year = Calendar.getInstance().get(Calendar.YEAR).toString()
    val month = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()
    var totalincome = 0.0
    var totalexpense = 0.0
    var totalAmount1 = 0.0
    var totalAmount2 = 0.0

    val date = year+"-"+month
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        incomeLastMonth= findViewById<View>(R.id.incomeData) as TextView
        expensesLastMonth  = findViewById<View>(R.id.textAmount2) as TextView
        predictedtotalsavings= findViewById<View>(R.id.predictedSavings) as TextView
        predictedtotalexpenses = findViewById<View>(R.id.predictedExpenses) as TextView

        getUserDataExpense(date)
        getUserDataIncome(date)
    }

    private fun predict(totalAm1: Double, totalAm2: Double){
        val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    var data = jsonObject.getString("placement")
                    var data2 = jsonObject.getString("placement2")
                    println(data)
                    predictedtotalexpenses?.setText(data +"php")
                    predictedtotalsavings?.setText(data2 +"php")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                Log.e("ERROR", "Error occurred ", error);
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["income"] = totalAm1.toString()
                System.out.println(totalAm1)
                params["totalincome"] = totalAm1.toString()
                params["totalexpenses"] = totalAm2.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(stringRequest)
    }
    private fun getUserDataIncome(date: String){


        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("IncomeHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(Categories::class.java)
                            if (user != null) {
                                if ((user.fireDate).toString().contains(date)) {
                                    totalAmount1 += user!!.fireAmount!!.toFloat()
//                                    saveMoney1(totalAmount1)
                                }
                            }
                        }
                        incomeLastMonth?.setText(totalAmount1.toString()+"php")
                    }else{
                          incomeLastMonth?.setText(totalAmount1.toString()+"php")
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
        println(totalincome.toString()+"alksdfjalsdkfjasd;lkfd")
    }
    private fun getUserDataExpense(date: String){


        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(Categories::class.java)
                            if (user != null) {
                                if((user.fireDate).toString().contains(date)){
                                    totalAmount2 += user!!.fireAmount!!.toFloat()
//                                    saveMoney(totalAmount2)
                                }
                            }
                        }
                        expensesLastMonth?.setText(totalAmount2.toString()+"php")
                    }else{
                         expensesLastMonth?.setText(totalAmount2.toString()+"php")
                    }
                    predict(totalAmount1, totalAmount2)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
//    private fun saveMoney(totalAmount2: Double) {
//        totalAm2=totalAmount2
//
//    }
//
//    private fun saveMoney1(totalAmount1: Double) {
//        totalAm1=totalAmount1
//    }

}