package com.example.fireph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
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
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Categories>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

//        chart = findViewById(R.id.pieChart)
//
//        configChartView()

        userRecyclerview = findViewById(R.id.categoryList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<Categories>()
        getUserData()

    }



//    private fun configChartView() {
//        val pie : Pie = AnyChart.pie()
//
//        val dataPieChart: MutableList<DataEntry> = mutableListOf()
//
//        for (index in salary.indices){
//            dataPieChart.add(ValueDataEntry(month.elementAt(index),salary.elementAt(index)))
//        }
//
//        pie.data(dataPieChart)
//        pie.title("Salaries Overview")
//        chart!!.setChart(pie)
//
//    }
    private fun getUserData() {
            var idString = arrayOf<String>()

//        dbref = FirebaseDatabase.getInstance().getReference("Employees")
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
//                fun onChildAdded(snapshot: DataSnapshot?, p1: String?) {
//                    val children = snapshot!!.children
//                    children.forEach {
//                        println(it.toString())
//                    }
//                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        System.out.println("Exist")
                        for (userSnapshot in snapshot.children){


                            val user = userSnapshot.getValue(Categories::class.java)
                            System.out.println(user)
                            System.out.println(user!!)

                            userArrayList.add(user!!)

                        }

                        userRecyclerview.adapter = categoryAdapter(userArrayList)


                    }

                }
//                    val children = snapshot!!.children
//                    children.forEach {
//                        var retrievedID = it.child("empId").value!!!!
//                        val retrievedAmount = it.child("fireAmount").value!!!!
//                        val retrievedCategory = it.child("fireCategory").value!!!!
//                        val retrievedDate = it.child("fireDate").value!!!!
//                        val retrievedType = it.child("fireType").value!!!!
//                        append(idString, retrievedID.toString())

//                        userArrayList.add(retrievedID)
//                        System.out.println(retrievedID.toString())
//                        System.out.println(retrievedAmount.toString())
//                        System.out.println(retrievedCategory.toString())
//                        System.out.println(retrievedDate.toString())
//                        System.out.println(retrievedType.toString())
//                        retrievedID = retrievedID.toString()
//                        System.out.println(idString.size)
//                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory").child(retrievedID)
//                            .addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                                    val retrievedAmount = dataSnapshot.child("fireAmount").value!!!!
//                                    val retrievedCategory = dataSnapshot.child("fireCategory").value!!!!
//                                    val retrievedDate = dataSnapshot.child("fireDate").value!!!!
//                                    val retrievedType = dataSnapshot.child("fireType").value!!!!
//                                    System.out.println("Amount: " + retrievedAmount)
//                                    System.out.println("Category: " + retrievedCategory)
//                                    System.out.println("Date: " + retrievedDate)
//                                    System.out.println("Type: " + retrievedType)
//                                }
//
//                                override fun onCancelled(databaseError: DatabaseError) {
//                                    throw databaseError.toException()
//                                }
//                            })
//                    }


                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })


//        dbref.addValueEventListener(object : ValueEventListener{
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()){
//
//                    for (userSnapshot in snapshot.children){
//
//
//                        val user = userSnapshot.getValue(Categories::class.java)
//                        userArrayList.add(user!!)
//
//                    }
//
//                    userRecyclerview.adapter = categoryAdapter(userArrayList)
//
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })

    }
}