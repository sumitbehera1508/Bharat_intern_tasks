package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    // this is a comment

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllclerar(view: View) {
        binding.ResultText.visibility = View.INVISIBLE
        binding.ResultText.text = ""
        binding.inputText.text = "0"
        stateError = false
        lastNumeric = false
        lastDot = false
    }
    fun onClear(view: View) {
        binding.inputText.text = binding.inputText.text.dropLast(1)
        try {
            val lastChar = binding.inputText.text.toString().last()
            if(lastChar.isDigit()){
                onEqual()
            }
        }catch (e:Exception){
            binding.ResultText.text = ""
            binding.ResultText.visibility = View.INVISIBLE
            Log.e("Last char delete",e.toString())
        }
    }
    fun onPercentage(view: View) {
        val txt = binding.inputText.text
        try{
            if(txt!="" && txt!="0"){
                binding.inputText.text = "$txt /100"
                onEqual()
                binding.inputText.text=binding.ResultText.text.toString().drop(1)
            }
        }catch (e:Exception){
            Log.e("Percentage Error",e.toString())
        }
    }
    fun onOperation(view: View) {
        if(!stateError && lastNumeric){
            binding.inputText.append((view as Button).text)
            lastDot = false
            lastNumeric = false

        }
    }
    fun onDigitClick(view: View) {
        if(stateError){
            binding.inputText.text  = (view as Button).text
            stateError = false
        }else{
            binding.inputText.append((view as Button).text)
        }

        lastNumeric=true
        onEqual()
    }
    fun onEqualClick(view: View) {
        onEqual()
        binding.inputText.text = binding.ResultText.text.drop(1)
        binding.ResultText.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt = binding.inputText.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.ResultText.text = "=$result"
                binding.ResultText.visibility = View.VISIBLE

            }catch (ex:ArithmeticException){
                binding.ResultText.text = "Error"
                stateError=true
                lastNumeric=false
            }
        }
    }

    fun onSignChange(view: View) {
        val txt = binding.inputText.text
        try{
            if(txt!="" && txt!="0"){
                binding.inputText.text = "-$txt"
                onEqual()
            }
        }catch (e:Exception){
            Log.e("SIgn Change",e.toString())
        }
    }

    fun onMultiply(view: View) {
        if(!stateError && lastNumeric){
            binding.inputText.append("*")
            lastDot = false
            lastNumeric = false

        }
    }
    fun onDivide(view: View) {
        if(!stateError && lastNumeric){
            binding.inputText.append("/")
            lastDot = false
            lastNumeric = false

        }
    }
}