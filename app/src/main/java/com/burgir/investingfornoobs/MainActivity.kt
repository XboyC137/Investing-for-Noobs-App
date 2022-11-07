package com.burgir.investingfornoobs

import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import kotlin.math.pow

// https://stackoverflow.com/questions/70994512/how-to-fix-the-error-resource-mipmap-ic-launcher-aka-com-example-myappnamemi

const val TAG = "MainActivity.kt"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // collect all relevant view objects
        val init_invest_edittext = findViewById<EditText>(R.id.init_invest)
        val reg_add_edittext = findViewById<EditText>(R.id.reg_add)
        val interest_rate_edittext = findViewById<EditText>(R.id.interest_rate)
        val years_to_grow_edittext = findViewById<EditText>(R.id.years_to_grow)
        val calculate_btn = findViewById<Button>(R.id.calculate)
        val result_textview = findViewById<TextView>(R.id.result)
        val reg_add_radio_group  = findViewById<RadioGroup>(R.id.reg_add_freq)
        val reset_btn = findViewById<Button>(R.id.reset)

        fun reset() {
            // reset selections
            init_invest_edittext.text.clear()
            reg_add_edittext.text.clear()
            interest_rate_edittext.text.clear()
            years_to_grow_edittext.text.clear()
            reg_add_radio_group.check(R.id.yearly_radio)

            // reset text
            result_textview.text = ""
        }

        reset_btn.setOnClickListener() {
            reset()
        }

        fun calculateLoop(p:Double, r:Double, t:Int, pmt:Double) {
            /*
            / p   : Initial investment
            / r   : Interest rate (yearly)
            / t   : term in years
            / pmt : Regular contribution in capital
            */

            var result = p

            for (i in 1..t) {
                result = result * (1+r) + pmt
            }

            Log.w(TAG, "result is $result")
            val investedPrin = p + (pmt * t)
            Log.w(TAG,  "investedPrin is $investedPrin")
            val formattedPrin = NumberFormat.getCurrencyInstance().format(investedPrin)
            val formattedInterests = NumberFormat.getCurrencyInstance().format(result - investedPrin)
            val formattedTotal = NumberFormat.getCurrencyInstance().format(result)

            // display results
            result_textview.text = getString(R.string.result, formattedPrin, formattedInterests, formattedTotal)
        }

        fun calculate(p:Double, r:Double, t:Int, pmt:Double) {
            /*
            / p   : Initial investment
            / r   : Interest rate (yearly)
            / t   : term in years
            / pmt : Regular contribution in capital
            */
            Log.i(TAG, "p is : $p")
            Log.i(TAG, "r is : $r")
            Log.i(TAG, "t is : $t")
            Log.i(TAG, "pmt is : $pmt")

            val result = if (pmt != 0.0 && r != 0.0) {
                p * (1 + r).pow(t) + ((pmt * (1 + r).pow(t) - 1) / r)
            }
            else if (pmt != 0.0) {
                pmt * t + p
            }
            else /* pmt == 0 */ {
                p * (1 + r).pow(t)
            }

            Log.w(TAG, "result is $result")
            val investedPrin = p + (pmt * t)
            Log.w(TAG,  "investedPrin is $investedPrin")
            val formattedPrin = NumberFormat.getCurrencyInstance().format(investedPrin)
            val formattedInterests = NumberFormat.getCurrencyInstance().format(result - investedPrin)
            val formattedTotal = NumberFormat.getCurrencyInstance().format(result)

            // display results
            result_textview.text = getString(R.string.result, formattedPrin, formattedInterests, formattedTotal)
        }


        // CALCULATE Button
        calculate_btn.setOnClickListener() {
            // get current values from View objects -> EditText

            val init_inv = try {
                init_invest_edittext.text.toString().toDouble()
            } catch (ex: NumberFormatException) {
                0.0
            }
            val interest_rate = try {
                (interest_rate_edittext.text.toString().toDouble() / 100 )
            } catch (ex: NumberFormatException) {
                0.0
            }
            var reg_add = try {
                reg_add_edittext.text.toString().toDouble()
            } catch (ex: NumberFormatException) {
                0.0
            }
            val term_in_years = try {
                years_to_grow_edittext.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                0
            }

            // now convert reg_add to the correct amount according to selected radio button
            reg_add = when(reg_add_radio_group.checkedRadioButtonId) {
                R.id.monthly_radio -> reg_add * 12
                R.id.weekly_radio -> reg_add * 52
                else -> {reg_add}
            }

            // Log.w(TAG, "reg_add is now $reg_add")

            calculateLoop(init_inv, interest_rate, term_in_years, reg_add)
        }


    }
}