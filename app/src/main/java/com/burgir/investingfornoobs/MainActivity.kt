package com.burgir.investingfornoobs

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // collect all relevant view objects
        val show_info_btn = findViewById<FloatingActionButton>(R.id.i)
        val info_textview = findViewById<TextView>(R.id.info_textview)
        val init_invest_edittext = findViewById<EditText>(R.id.init_invest)
        val reg_add_edittext = findViewById<EditText>(R.id.reg_add)
        val interest_rate_edittext = findViewById<EditText>(R.id.interest_rate)
        val years_to_grow_edittext = findViewById<EditText>(R.id.years_to_grow)
        val calculate_btn = findViewById<Button>(R.id.calculate)
        val result_textview = findViewById<TextView>(R.id.result)

        fun calculate(init_inv:Double, interest_rate:Double, term_in_years:Int) {
            // get results
            val result = init_inv * (1 + interest_rate).pow(term_in_years)
            val formattedPrin = NumberFormat.getCurrencyInstance().format(800)
            val formattedInterests = NumberFormat.getCurrencyInstance().format(result - 800)
            val formattedTotal = NumberFormat.getCurrencyInstance().format(result)

            // display results
            result_textview.text = getString(R.string.result, formattedPrin, formattedInterests, formattedTotal)
        }

        // CALCULATE Button
        calculate_btn.setOnClickListener() {
            // get current values from View objects -> EditText
            // initializing
            var init_inv = 0.0
            var interest_rate = 0.0
            var reg_add = 0.0
            var term_in_years = 0

            init_inv = try {
                init_invest_edittext.text.toString().toDouble()
            } catch (ex: NumberFormatException) {
                0.0
            }
            interest_rate = try {
                (interest_rate_edittext.text.toString().toDouble() / 100 )
            } catch (ex: NumberFormatException) {
                0.0
            }
            reg_add = try {
                reg_add_edittext.text.toString().toDouble()
            } catch (ex: NumberFormatException) {
                0.0
            }
            term_in_years = try {
                years_to_grow_edittext.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                0
            }

            calculate(init_inv, interest_rate, term_in_years)
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