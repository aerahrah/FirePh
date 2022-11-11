package com.example.fireph

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class algorithmActivity : AppCompatActivity() {
    //    var recyclerView: RecyclerView? = null
    private var saveButton: Button? = null
    private var Food: EditText?=null
    private var Bread: EditText?=null
    private var Rice: EditText?=null
    private var Meat: EditText?=null
    private var Fish: EditText?=null
    private var Fruit: EditText?=null
    private var Vegetables: EditText?=null
    private var Restaurant: EditText?=null
    private var Alcoholic: EditText?=null
    private var Tobacco: EditText?=null
    private var Clothes: EditText?=null
    private var Housing: EditText?=null
    private var Medical: EditText?=null
    private var Transportation: EditText?=null
    private var Communication: EditText?=null
    private var Education: EditText?=null
    private var Miscellaneous: EditText?=null
    private var Special: EditText?=null
    var url = "https://fireph.herokuapp.com/predict"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        saveButton = findViewById<View>(R.id.submitButton) as Button
        Food = findViewById<View>(R.id.foodEditText) as EditText
        Bread = findViewById<View>(R.id.breadEditText) as EditText
        Rice = findViewById<View>(R.id.riceEditText) as EditText
        Meat = findViewById<View>(R.id.hiet1) as EditText
        Fish = findViewById<View>(R.id.hiet2) as EditText
        Fruit = findViewById<View>(R.id.hiet3) as EditText
        Vegetables = findViewById<View>(R.id.hiet4) as EditText
        Restaurant = findViewById<View>(R.id.hiet5) as EditText
        Alcoholic = findViewById<View>(R.id.hiet6) as EditText
        Tobacco = findViewById<View>(R.id.hiet7) as EditText
        Clothes = findViewById<View>(R.id.hiet8) as EditText
        Housing = findViewById<View>(R.id.hiet9) as EditText
        Medical = findViewById<View>(R.id.hiet10) as EditText
        Transportation = findViewById<View>(R.id.hiet11) as EditText
        Communication = findViewById<View>(R.id.hiet12) as EditText
        Education = findViewById<View>(R.id.hiet13) as EditText
        Miscellaneous = findViewById<View>(R.id.hiet14) as EditText
        Special = findViewById<View>(R.id.hiet15) as EditText

        saveButton!!.setOnClickListener(View.OnClickListener {
            val stringRequest: StringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        var data = jsonObject.getString("placement")
                        println(data)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error ->
                    Log.e("ERROR", "Error occurred ", error);
                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["totalfood"] = Food!!.getText().toString()
                    params["bread"] = Bread!!.getText().toString()
                    params["rice"] = Rice!!.getText().toString()
                    params["meat"] = Meat!!.getText().toString()
                    params["fish"] = Fish!!.getText().toString()
                    params["fruit"] = Fruit!!.getText().toString()
                    params["vegetable"] = Vegetables!!.getText().toString()
                    params["restaurant"] = Restaurant!!.getText().toString()
                    params["alcohol"] = Alcoholic!!.getText().toString()
                    params["tobacco"] = Tobacco!!.getText().toString()
                    params["clothes"] = Clothes!!.getText().toString()
                    params["housing"] = Housing!!.getText().toString()
                    params["medical"] = Medical!!.getText().toString()
                    params["transportation"] = Transportation!!.getText().toString()
                    params["communication"] = Communication!!.getText().toString()
                    params["education"] = Education!!.getText().toString()
                    params["miscellaneous"] = Miscellaneous!!.getText().toString()
                    params["special"] = Special!!.getText().toString()
                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(stringRequest)
        })
    }
}