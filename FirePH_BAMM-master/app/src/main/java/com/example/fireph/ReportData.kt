package com.example.fireph

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.github.mikephil.charting.animation.Easing
//import com.github.mikephil.charting.charts.PieChart
//import com.github.mikephil.charting.components.Description
//import com.github.mikephil.charting.components.Legend
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.PieData
//import com.github.mikephil.charting.data.PieDataSet
//import com.github.mikephil.charting.data.PieEntry
//import com.github.mikephil.charting.formatter.PercentFormatter
//import com.github.mikephil.charting.utils.ColorTemplate
//import com.github.mikephil.charting.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReportData : AppCompatActivity() {
}
//    lateinit var pieChart:PieChart
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.piechart)
//
//        val intent = intent
//        val date : String? = intent.getStringExtra("DATE");
//        if(date == null){
//            finish()
//            return;
//        }
//
//        pieChart=findViewById(R.id.displayPieChart)
//
//        var idString = arrayOf<String>()
//        val uid = FirebaseAuth.getInstance().currentUser!!.uid
//        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("ExpensesHistory")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()){
//                        for (userSnapshot in snapshot.children){
//                            val user = userSnapshot.getValue(viewDataModel::class.java)
//                            if (user != null) {
//                                if((user.fireDate).toString().contains(date)){
//                                    userArrayList.add(user)
//                                }
//                            }
//                        }
//                    }
//                }
//                override fun onCancelled(databaseError: DatabaseError) {
//                    throw databaseError.toException()
//                }
//            })
//        val list:ArrayList<PieEntry> = ArrayList()
//
//        list.add(PieEntry(100f,"100"))
//        list.add(PieEntry(101f,"101"))
//        list.add(PieEntry(102f,"102"))
//        list.add(PieEntry(103f,"103"))
//        list.add(PieEntry(104f,"104"))
//
//        val pieDataSet= PieDataSet(list,"List")
//
//        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
//        pieDataSet.valueTextColor= Color.BLACK
//        pieDataSet.valueTextSize=15f
//
//        val pieData= PieData(pieDataSet)
//
//        pieChart.data= pieData
//        pieChart.description.text= "Pie Chart"
//        pieChart.centerText="List"
//        pieChart.animateY(2000)
//
//    }
//}
////    private var pieChart: PieChart?= null
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
////        Utils.init(this)
////        pieChart = findViewById(R.id.activity_main_piechart)
//////        setupPieChart()
////        loadPieChartData()
////    }
////
//////    private fun setupPieChart() {
//////        val descChartDescription = Description()
//////        descChartDescription.isEnabled = true
//////        pieChart!!.description = descChartDescription
//////        pieChart!!.isDrawHoleEnabled = true
//////        pieChart!!.setUsePercentValues(true)
//////        pieChart!!.setEntryLabelTextSize(12f)
//////        pieChart!!.setEntryLabelColor(Color.BLACK)
//////        pieChart!!.centerText = "Spending by Category"
//////        pieChart!!.setCenterTextSize(24f)
//////        pieChart!!.description.isEnabled = false
//////        val l = pieChart!!.legend
//////        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//////        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
//////        l.orientation = Legend.LegendOrientation.VERTICAL
//////        l.setDrawInside(false)
//////        l.isEnabled = true
//////    }
////
////    private fun loadPieChartData() {
////        val entries = ArrayList<PieEntry>()
////        entries.add(PieEntry(0.2f, "Food & Dining"))
////        entries.add(PieEntry(0.15f, "Medical"))
////        entries.add(PieEntry(0.10f, "Entertainment"))
////        entries.add(PieEntry(0.25f, "Electricity and Gas"))
////        entries.add(PieEntry(0.3f, "Housing"))
////        val colors = ArrayList<Int>()
////        for (color in ColorTemplate.MATERIAL_COLORS) {
////            colors.add(color)
////        }
////        for (color in ColorTemplate.VORDIPLOM_COLORS) {
////            colors.add(color)
////        }
////        val dataSet = PieDataSet(entries, "Expense Category")
////        dataSet.colors = colors
////        val data = PieData(dataSet)
////        data.setDrawValues(true)
////        data.setValueFormatter(PercentFormatter(pieChart))
////        data.setValueTextSize(12f)
////        data.setValueTextColor(Color.BLACK)
////        pieChart!!.data = data
////        pieChart!!.invalidate()
////        pieChart!!.animateY(1400, Easing.EaseInOutQuad)
////    }
////}