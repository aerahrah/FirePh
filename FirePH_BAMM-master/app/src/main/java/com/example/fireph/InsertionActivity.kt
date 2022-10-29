package com.example.fireph

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class InsertionActivity : AppCompatActivity() {

    private lateinit var etDate: EditText
    private lateinit var etAmount: EditText
    private lateinit var etTest: EditText
    private lateinit var etCategory: TextInputLayout
    private lateinit var etType: RadioGroup
    private lateinit var btnSaveData: Button
    private lateinit var autoCompleteTxt: AutoCompleteTextView
    private lateinit var adapterItems: ArrayAdapter<String?>

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etDate = findViewById(R.id.etDate)
        etAmount = findViewById(R.id.etAmount)
        btnSaveData = findViewById(R.id.btnSave)
        etCategory = findViewById(R.id.etCategory)
        etType = findViewById(R.id.etRadioGroup)
//        etTest = findViewById(R.id.etTest)

        val calendar = Calendar.getInstance();
        val myMonth = calendar.get(Calendar.MONTH);
        val myYear = calendar.get(Calendar.YEAR);
        val day = calendar.get(Calendar.DAY_OF_MONTH);

        etAmount.setFilters(arrayOf<InputFilter>(DecimalDigitsInputFilter(16, 2)))
        etDate.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth,
                -> etDate.setText(""+ year + "-" + (month+1) + "-" + dayOfMonth);
            },myYear,myMonth,day)
            datePickerDialog.show()
        }

        val items = arrayOf("Material", "Design", "Components", "Android", "5.0 Lollipop")
        val income = arrayOf("Salary", "Bonus", "Allowance", "Benefits"   )

        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.activity_categories, items)
        autoCompleteTxt.setAdapter(adapterItems)

        autoCompleteTxt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideSoftKeyboard() // Using activity extension function
            }
        }

        autoCompleteTxt.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
            }

//        etTest.setOnClickListener {
//            val intent = Intent(this, InsertionActivity::class.java)
//            startActivity(intent)
//        }

        btnSaveData.setOnClickListener {
            saveData()
        }
    }

    fun Activity.hideSoftKeyboard() {
        val inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = this.currentFocus
        val windowToken = this.window?.decorView?.rootView?.windowToken
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        inputMethodManager.hideSoftInputFromWindow(
            windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    private fun saveData() {

        dbRef = FirebaseDatabase.getInstance("https://budgetfireph-default-rtdb.firebaseio.com/").getReference("Users")

        val fireDate = etDate.text.toString()
        val fireAmount = etAmount.text.toString()
        val fireType = etType.checkedRadioButtonId.toString()
        var fireCategory = ""
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        autoCompleteTxt.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                fireCategory = parent.getItemAtPosition(position).toString()
                System.out.print(fireCategory)
            }
        if (fireDate.isEmpty()) {
            etDate.error = "Please enter date"
        }
        if(fireAmount.isEmpty()){
            etAmount.error = "Please enter Amount"
        }
        if(fireType.isEmpty()){
            Toast.makeText(this, "Please select Type", Toast.LENGTH_SHORT).show();

        }
        if(fireCategory.isEmpty()){
            Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
        }

        val moneyId = dbRef.push().key!!
        val inputData  = inputDataModel(moneyId,fireType,fireDate,fireAmount,fireCategory)
        val inputID = inputIdModel(moneyId)
        if(fireType == "1"){
//            dbRef.child(uid).child("ID").push().setValue(inputID)
            dbRef.child(uid).child("IncomeHistory").child(moneyId).setValue(inputData).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }}.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

        }
        else if(fireType == "2"){
//            dbRef.child(uid).child("ID").push().setValue(inputID)
            dbRef.child(uid).child("ExpensesHistory").child(moneyId).setValue(inputData).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }}.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
        }



    }

}
internal class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    private val mPattern: Pattern
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }

    init {
        mPattern =
            Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(.)?")
    }
}