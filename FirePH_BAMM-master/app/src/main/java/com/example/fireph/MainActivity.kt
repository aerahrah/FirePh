package com.example.fireph

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogOut: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button
    private lateinit var btnManageData: Button
    private lateinit var btnViewData: Button
    private lateinit var btnReportData: Button
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData = findViewById(R.id.btnFetchData)
        btnManageData = findViewById(R.id.btnManageData)
        btnViewData = findViewById(R.id.btnViewTransaction)
        btnReportData = findViewById(R.id.btnReportData)


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

//        btnManageData.setOnClickListener {
//            val intent = Intent(this, categoryListAdapter::class.java)
//            startActivity(intent)
//        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, CategoryListAdapter::class.java)
            startActivity(intent)
        }

        val calendar = Calendar.getInstance();
        val myMonth = calendar.get(Calendar.MONTH);
        val myYear = calendar.get(Calendar.YEAR);

        btnViewData.setOnClickListener {
            val dialog = datePickerDialog("ViewData")

            dialog.show()

            val day = dialog.findViewById<View>(Resources.getSystem().getIdentifier("android:id/day", null, null))
            if (day != null) {
                day.visibility = View.GONE
            }
        }
        btnManageData.setOnClickListener {
            val dialog = datePickerDialog("ManageData")
            dialog.show()

            val day = dialog.findViewById<View>(Resources.getSystem().getIdentifier("android:id/day", null, null))
            if (day != null) {
                day.visibility = View.GONE
            }
        }
        btnReportData.setOnClickListener {
            val dialog = datePickerDialog("ReportData")
            dialog.show()

            val day = dialog.findViewById<View>(Resources.getSystem().getIdentifier("android:id/day", null, null))
            if (day != null) {
                day.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = mAuth.getCurrentUser()

        if (user == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }
    @SuppressLint("SetTextI18n")
    fun datePickerDialog(i: String): DatePickerDialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this@MainActivity, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            date = (""+year+"-"+(monthOfYear+1))
            System.out.println(date)
            if(i=="ViewData"){
                val intent = Intent(this, ViewTransactions::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            else if(i=="ReportData"){
                val intent = Intent(this, PieExpensesChart::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            else if(i=="ManageData"){
                val intent = Intent(this, ManageActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
        }, year, month, day)

        return datePickerDialog
    }
}