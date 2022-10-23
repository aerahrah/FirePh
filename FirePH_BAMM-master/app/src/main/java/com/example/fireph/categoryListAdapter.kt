package com.example.fireph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class categoryListAdapter : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
//    private lateinit var userRecyclerview : RecyclerView
//    private lateinit var userArrayList : ArrayList<Categories>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

//        userRecyclerview = findViewById(R.id.categoryList)
//        userRecyclerview.layoutManager = LinearLayoutManager(this)
//        userRecyclerview.setHasFixedSize(true)
//
//        userArrayList = arrayListOf<Categories>()
        getUserData()

    }

    private fun getUserData() {

//        dbref = FirebaseDatabase.getInstance().getReference("Employees")
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory").child("-NF2OIArMS1BYRtxpbkO")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val retrievedAmount = dataSnapshot.child("fireAmount").value!!!!
                    val retrievedCategory = dataSnapshot.child("fireCategory").value!!!!
                    val retrievedDate = dataSnapshot.child("fireDate").value!!!!
                    val retrievedType = dataSnapshot.child("fireType").value!!!!
                    System.out.println("HIII " + retrievedAmount)
                    System.out.println("HIII " + retrievedCategory)
                    System.out.println("HIII " + retrievedDate)
                    System.out.println("HIII " + retrievedType)
                }

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