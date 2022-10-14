package com.burgir.investingfornoobs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // collect all relevant view objects
        val show_info_btn = findViewById<Button>(R.id.i)
        val info_textview = findViewById<TextView>(R.id.info_textview)
        val init_invest_edittext = findViewById<EditText>(R.id.init_invest)
        val reg_add_edittext = findViewById<EditText>(R.id.reg_add)
        val interest_rate_edittext = findViewById<EditText>(R.id.interest_rate)
        val years_to_grow_edittext = findViewById<EditText>(R.id.years_to_grow)
        val calculate_btn = findViewById<Button>(R.id.calculate)
        val result_textview = findViewById<TextView>(R.id.result)

        // CALCULATE Button
        calculate_btn.setOnClickListener() {
            // empty edittext crashes app
            try {
                // get current values from View objects -> EditText
                val init_inv = init_invest_edittext.text.toString().toDouble()
                val interest_rate = (interest_rate_edittext.text.toString().toDouble() / 100 )
                val reg_add = reg_add_edittext.text.toString().toDouble()
                val term_in_years = years_to_grow_edittext.text.toString().toInt()

                //


                val result = init_inv * (1 + interest_rate).pow(term_in_years)

                result_textview.text = result.toString()
            }
            catch (ex: NumberFormatException) {
                //One or more EditText are empty
                // Popup a toast to let them know
                Toast.makeText(this, R.string.toast_empty_field, Toast.LENGTH_SHORT).show()
            }

        }

        // Show Info Button
        var info_activated = false
        show_info_btn.setOnClickListener() {
            if (info_activated) {
                info_textview.setText(R.string.hide_text)
                info_activated = false
            }
            else {
                info_textview.setText(R.string.welcome)
                info_activated = true
            }
        }

    }
}