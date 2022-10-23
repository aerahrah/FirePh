package com.example.fireph

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fireph.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {
    private lateinit var etRegEmail: TextInputEditText
    private lateinit var etRegPassword: TextInputEditText
    private lateinit var tvLoginHere: TextView
    private lateinit var btnRegister: Button
    private lateinit var dbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        etRegEmail = findViewById(R.id.etRegEmail)
        etRegPassword = findViewById(R.id.etRegPass)
        tvLoginHere = findViewById(R.id.tvLoginHere)
        btnRegister = findViewById(R.id.btnRegister)
        mAuth = FirebaseAuth.getInstance()
        btnRegister.setOnClickListener { view -> createUser() }
        tvLoginHere.setOnClickListener(View.OnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        })
    }

    private fun createUser() {
        dbRef = FirebaseDatabase.getInstance("https://budgetfireph-default-rtdb.firebaseio.com/")
            .getReference("Users")
        val email = etRegEmail!!.text.toString()
        val password = etRegPassword!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            etRegEmail!!.error = "Email cannot be empty"
            etRegEmail!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword!!.error = "Password cannot be empty"
            etRegPassword!!.requestFocus()
        } else {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    val empId = FirebaseAuth.getInstance().currentUser!!.uid
                    val inputData  = inputUserModel(empId,email,password)

                    dbRef.child(empId).child("UserInfo").setValue(inputData).addOnCompleteListener {task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }}.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Error: ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}