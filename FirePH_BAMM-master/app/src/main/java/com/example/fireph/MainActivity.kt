package com.example.fireph

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    private lateinit var btnLogOut: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button
    private lateinit var btnManageData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)
        btnManageData = findViewById(R.id.btnManageData)

        btnLogOut = findViewById(R.id.btnLogout)
        mAuth = FirebaseAuth.getInstance()

        btnLogOut.setOnClickListener { view: View? ->
            mAuth.signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

//        btnFetchData.setOnClickListener {
//            val intent = Intent(this, FetchingActivity::class.java)
//            startActivity(intent)
//        }


        btnManageData.setOnClickListener {
            val intent = Intent(this, categoryListAdapter::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = mAuth.getCurrentUser()

        if (user == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
}