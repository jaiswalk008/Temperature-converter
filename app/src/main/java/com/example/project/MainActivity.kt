package com.example.project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.project.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //findViewById<View>(R.id.id_view) with binding.idView
        //findViewById<Button>(R.id.convert_button){ onRadioButtonClicked() }
        binding.tempValue.setOnClickListener { updateTemp() }
    }

    fun onRadioButtonClicked(view: View) {
        select_text.visibility = View.GONE
        val newTemp : String = "The new temperature is: "
        if (view is RadioButton) {
            val checked = view.isChecked
            //val fToC = findViewById<RadioButton>(R.id.F_TO_C)
            //val cToFf = findViewById<RadioButton>(R.id.C_TO_F)
            binding.FTOC.isClickable = true
            binding.CTOF.isClickable = true
            when(view.getId()){
                binding.FTOC.id ->{
                    if (binding.CTOF.isChecked) binding.CTOF.isChecked = false
                    if((binding.tempValue.text.isNotEmpty()) && (binding.tempValue.visibility== VISIBLE)){
                        binding.tempValue.text =
                            (newTemp + BigDecimal(
                                (binding.enterTemp.text.toString()
                                    .toInt() - 32) * 5.0 / 9
                            ).setScale(2, RoundingMode.HALF_EVEN).toString() + " C").toString()
                    }

                    else convertTemperature(1)
                }
                binding.CTOF.id -> {
                    if (binding.FTOC.isChecked) binding.FTOC.isChecked = false
                    if((binding.tempValue.text.isNotEmpty()) && (binding.tempValue.visibility== VISIBLE)){
                        binding.tempValue.text =
                            (newTemp+BigDecimal((binding.enterTemp.text.toString()
                                .toInt() * 9.0 / 5) + 32).setScale(2,RoundingMode.HALF_EVEN).toString() + " F").toString()
                    }
                    else convertTemperature(2)
                }

            }
        }
    }

    private fun convertTemperature(num: Int) {
        var flag = true

        val newTemp : String = "The new temperature is: "
        if (num == 1) binding.convertButton.setOnClickListener {
            if (binding.enterTemp.text.isNotEmpty()) {
                binding.enterTemp.hint = "Enter Temperature"
                binding.tempValue.text =
                    (newTemp + BigDecimal(
                        (binding.enterTemp.text.toString()
                            .toInt() - 32) * 5.0 / 9
                    ).setScale(2, RoundingMode.HALF_EVEN).toString() + " C").toString()
                onRadioButtonClicked(it)
            }


            else {

                binding.enterTemp.hint = "Please enter a value first!"
                flag = false
            }


            if(flag){
                binding.apply {
                    binding.tempValue.visibility = VISIBLE
                    binding.convertButton.visibility = GONE
                    binding.enterTemp.visibility = GONE
                }
            }else{
                convertTemperature(1)
            }
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
        if (num == 2) binding.convertButton.setOnClickListener {
            if(binding.enterTemp.text.isNotEmpty()){
                binding.enterTemp.hint = "Enter Temperature"
                binding.tempValue.text =
                    (newTemp+BigDecimal((binding.enterTemp.text.toString()
                        .toInt() * 9.0 / 5) + 32).setScale(2,RoundingMode.HALF_EVEN).toString() + " F").toString()

                onRadioButtonClicked(it)

            }
            else{
                binding.enterTemp.hint= "Please enter a value first!"
                flag = false

            }
            if(flag){
                binding.apply {
                    binding.tempValue.visibility = VISIBLE
                    binding.convertButton.visibility = GONE
                    binding.enterTemp.visibility = GONE
                }
            }else{
                convertTemperature(2)
            }


            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun updateTemp() {
        binding.apply {
            binding.enterTemp.visibility = VISIBLE
            binding.convertButton.visibility = VISIBLE
            binding.tempValue.visibility = GONE
            binding.enterTemp.requestFocus()
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.enterTemp, 0)
    }
}
