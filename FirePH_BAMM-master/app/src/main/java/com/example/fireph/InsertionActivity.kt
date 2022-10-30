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
    private var item: String = ""
    private var type: String = ""
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
        etType = findViewById(R.id.etRadioGroup)
        btnSaveData = findViewById(R.id.btnSave)
        etCategory = findViewById(R.id.etCategory)

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

        val items = arrayOf("Rent And Mortgage Payments", "Utilities", "Transportation Costs",
            "Household Expenses","Insurance And Related Medical Costs",
            "Recurring Subscriptions","Child Care","Pet Care",
            "Entertainment","Miscellaneous", "Groceries And Personal Care")
        val income = arrayOf("Salary", "Bonus", "Allowance", "Benefits")

        etType.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId == R.id.etRadio1){
                saveType("Income", income)
            }
            if(checkedId == R.id.etRadio2){
                saveType("Expenses", items)
            }
        }

        btnSaveData.setOnClickListener {
            saveData()
        }
    }

    private fun saveType(radio: String, array: Array<String>) {

        type = radio
        autoCompleteTxt = findViewById(R.id.auto_complete_txt)
        adapterItems = ArrayAdapter(this, R.layout.list_menu, array)
        autoCompleteTxt.setText("")
        autoCompleteTxt.setAdapter(adapterItems)
        item=""

        autoCompleteTxt.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                hideSoftKeyboard() // Using activity extension function
            }
        }

        autoCompleteTxt.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val category = parent.getItemAtPosition(position).toString()
                saveItem(category)
            }
    }

    private fun saveItem(category: String) {
        item = category
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
        val fireType = type
        val fireCategory = item
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        if (fireDate.isEmpty()) {
            etDate.error = "Please enter date"
            return
        }
        if(fireAmount.isEmpty()){
            etAmount.error = "Please enter Amount"
            return
        }
        if(fireType.isEmpty()){
            Toast.makeText(this, "Please select Type", Toast.LENGTH_SHORT).show();
            return
        }
        if(fireCategory.isEmpty()){
            Toast.makeText(this, "Please select Category", Toast.LENGTH_SHORT).show();
            return
        }

        val moneyId = dbRef.push().key!!
        val inputData  = inputDataModel(moneyId,fireType,fireDate,fireAmount,fireCategory)
        if(fireType == "Income"){
//            dbRef.child(uid).child("ID").push().setValue(inputID)
            dbRef.child(uid).child("IncomeHistory").child(moneyId).setValue(inputData).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }}.addOnFailureListener { err ->
                System.out.println("HUUU")
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

        }
        else if(fireType == "Expenses"){
//            dbRef.child(uid).child("ID").push().setValue(inputID)
            dbRef.child(uid).child("ExpensesHistory").child(moneyId).setValue(inputData).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }}.addOnFailureListener { err ->
                System.out.println("HUUU")
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
