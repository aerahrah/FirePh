package com.example.fireph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewTransactions : AppCompatActivity() {

    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<viewDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val intent = intent
        val date : String? = intent.getStringExtra("DATE");
        if(date == null){
            finish()
            return;
        }

        userRecyclerview = findViewById(R.id.categoryList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<viewDataModel>()
        getUserData(date)
    }

    private fun getUserData(date: String) {
        var idString = arrayOf<String>()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        System.out.println("Exist")
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(viewDataModel::class.java)
                            if (user != null) {
                                if((user.fireDate).toString().contains(date)){
                                    userArrayList.add(user)
                                }
                            }
                        }
                        userRecyclerview.adapter = monthlyListAdapter(userArrayList)
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })
    }
}