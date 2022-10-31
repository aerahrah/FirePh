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


class forgetPassword : AppCompatActivity() {
    private lateinit var etUsername: TextInputEditText
    private lateinit var tvLoginPage: TextView
    private lateinit var btnSubmit: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_pass_dialogue)

        etUsername = findViewById(R.id.etUsername)
        tvLoginPage = findViewById(R.id.tvLoginPage)
        btnSubmit = findViewById(R.id.btnSubmit)

        mAuth = FirebaseAuth.getInstance()

        btnSubmit.setOnClickListener {
            val rPassword = etUsername.text.toString()
            if (TextUtils.isEmpty(rPassword)) {
                etUsername!!.error = "Email cannot be empty"
                etUsername!!.requestFocus()
            }else {
                mAuth.sendPasswordResetEmail(rPassword)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Please Check your Email!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                    }
            }
        }
        tvLoginPage.setOnClickListener(View.OnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@forgetPassword,
                    LoginActivity::class.java
                )
            )
        })
    }
}