package com.example.fireph

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var etLoginEmail: TextInputEditText
    private lateinit var etLoginPassword: TextInputEditText
    private lateinit var tvRegisterHere: TextView
    private lateinit var tvForgotPass: TextView
    private lateinit var btnLogin: Button
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etLoginEmail = findViewById(R.id.etUsername)
        etLoginPassword = findViewById(R.id.etLoginPass)
        tvRegisterHere = findViewById(R.id.tvRegisterHere)
        tvForgotPass = findViewById(R.id.tvForgotPass)
        btnLogin = findViewById(R.id.btnLogin)
        mAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener { view -> loginUser() }
        tvRegisterHere.setOnClickListener(View.OnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        })
        tvForgotPass.setOnClickListener (View.OnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@LoginActivity,
                    ForgotPassActivity::class.java
                )
            )
        })
    }
    private fun forgotPassword(username: EditText){
        mAuth = FirebaseAuth.getInstance()
        if (TextUtils.isEmpty(username!!.text.toString())) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username!!.text.toString()).matches()){
            return
        }
        mAuth!!.sendPasswordResetEmail(username!!.text.toString())
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                   Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private fun loginUser() {
        val email = etLoginEmail!!.text.toString()
        val password = etLoginPassword!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            etLoginEmail!!.error = "Email cannot be empty"
            etLoginEmail!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword!!.error = "Password cannot be empty"
            etLoginPassword!!.requestFocus()
        } else {
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "User logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Log in Error: " + task.exception!!.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }
        }
    }
}