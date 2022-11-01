package com.example.fireph

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class PieExpensesChart : AppCompatActivity() {

    lateinit var pieChart:PieChart
    private lateinit var pieRecyclerview : RecyclerView
    private lateinit var buttonIncome : Button
    private lateinit var userArrayList : ArrayList<PiechartAttributesModel>
    private lateinit var date : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.piechart_expenses)

        pieRecyclerview = findViewById(R.id.categoryPieList)
        pieRecyclerview.layoutManager = LinearLayoutManager(this)
        pieRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<PiechartAttributesModel>()
        buttonIncome = findViewById(R.id.buttonIncome)

        val intent = intent
        date = intent.getStringExtra("DATE").toString();
        if(date == null){
            finish()
            return;
        }

        pieChart=findViewById(R.id.displayPieChart)

        val theMap = HashMap<String, Float>()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (userSnapshot in snapshot.children){
                            val user = userSnapshot.getValue(viewDataModel::class.java)
                            if (user != null) {
                                if((user.fireDate).toString().contains(date)){
                                    if(theMap.containsKey(user.fireCategory.toString())){
                                        val total = theMap[user.fireCategory.toString()]!!.toFloat() + (user.fireAmount.toString().toFloat())
                                        theMap[user.fireCategory.toString()] = total
                                    }
                                    else {
                                        theMap[(user.fireCategory).toString()] = ((user.fireAmount).toString()).toFloat()
                                    }
                                }
                            }
                        }
                        if(theMap.isNotEmpty()){
                            saveData(theMap)
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    throw databaseError.toException()
                }
            })

        buttonIncome.setOnClickListener {
            val intent = Intent(this, PieIncomeChart::class.java)
            intent.putExtra("DATE", date)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveData(theMap: HashMap<String, Float>) {
        val DUTCH_COLORS = intArrayOf(
            ColorTemplate.rgb("#e60049"),
            ColorTemplate.rgb("#0bb4ff"),
            ColorTemplate.rgb("#50e991"),
            ColorTemplate.rgb("#e6d800"),
            ColorTemplate.rgb("#9b19f5"),
            ColorTemplate.rgb("#ffa300"),
            ColorTemplate.rgb("#dc0ab4"),
            ColorTemplate.rgb("#b3d4ff"),
            ColorTemplate.rgb("#00bfa0"),
            ColorTemplate.rgb("#3498db")
        )

        val list:ArrayList<PieEntry> = ArrayList()
        var i = 0
        theMap.forEach { (k, v) ->
            val colors = DUTCH_COLORS[i]
            val user=PiechartAttributesModel(name = k, amount = v,colors=colors)
            userArrayList.add(user)
            list.add(PieEntry(v,k))
            pieRecyclerview.adapter = pieListAdapter(userArrayList)
            i += 1
        }

        val pieDataSet= PieDataSet(list,"List")
        pieDataSet.setColors(DUTCH_COLORS,255)
        pieDataSet.valueTextColor= Color.BLACK
        pieDataSet.valueTextSize=15f


        val month = date.substring((5)).toInt()
        var monthName: String = ""
        when (month) {
            1 -> {monthName="January"}
            2 -> {monthName="February"}
            3 -> {monthName="March"}
            4 -> {monthName="April"}
            5 -> {monthName="May"}
            6 -> {monthName="June"}
            7 -> {monthName="July"}
            8 -> {monthName="August"}
            9 -> {monthName="September"}
            10 -> {monthName="October"}
            11 -> {monthName="November"}
            12 -> {monthName="December"}

        }
        val pieData= PieData(pieDataSet)
        pieChart.data= pieData
        pieChart.setUsePercentValues(true)
        pieChart.centerText = monthName
        pieChart.setCenterTextSize(25f)
        pieChart.legend.isEnabled = false
        pieChart.animateY(2000)
        pieChart.setDrawEntryLabels(false)
        pieChart.description.isEnabled = false
        pieDataSet.valueFormatter = PercentFormatter(pieChart)
        pieChart.setUsePercentValues(true)

    }
}