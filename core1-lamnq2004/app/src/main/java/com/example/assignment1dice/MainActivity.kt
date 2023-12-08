package com.example.assignment1dice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

const val TOTAL_RESULT = "total_result"
const val DICE_VALUE = "dice_value"
const val ROLL_BUTTON_STATE = "roll_state"

class MainActivity : AppCompatActivity() {

    private lateinit var rollButton : Button
    private lateinit var addButton : Button
    private lateinit var subtractButton : Button
    private lateinit var total : TextView
    private lateinit var resetButton : Button
    private lateinit var diceImage : ImageView

    private var diceValue = 0
    private var totalResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("LIFE_CYCLE", "running")

        rollButton = findViewById(R.id.roll)
        addButton = findViewById(R.id.add)
        subtractButton = findViewById(R.id.subtract)
        total = findViewById(R.id.total)
        resetButton = findViewById(R.id.reset)

        rollButton.setOnClickListener {
            diceValue = rollDice()
            rollButtonOff()
        }

        addButton.setOnClickListener {
            add()
            updateDisplay()
            rollButtonOn()
        }

        subtractButton.setOnClickListener {
            subtract()
            updateDisplay()
            rollButtonOn()
        }

        resetButton.setOnClickListener {
            resetValue()
            updateDisplay()
            rollButtonOn()
        }

        //Restore the value
        if (savedInstanceState != null) {
            totalResult = savedInstanceState.getInt(TOTAL_RESULT, 0)
            diceValue = savedInstanceState.getInt(DICE_VALUE, 0)
            val rollButtonState = savedInstanceState.getBoolean(ROLL_BUTTON_STATE, true)
            rollButton.isEnabled = rollButtonState

            updateDisplay()
            Log.i("STATE", "restored")
        }
    }

    //Save the value
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(TOTAL_RESULT, totalResult)
        outState.putInt(DICE_VALUE, diceValue)
        outState.putBoolean(ROLL_BUTTON_STATE, rollButton.isEnabled)

        Log.i("STATE", "saved")
    }

    private fun rollDice():Int{
        val randomNumber = generateRandomNumber()
        val drawableResource = setDiceImage(randomNumber)
        drawDiceImage(drawableResource)

        return randomNumber
    }

    private fun generateRandomNumber(): Int {
        val randomNumber = kotlin.random.Random(1)
        return randomNumber.nextInt(1, 7)

        //For testing the random method//
        //return (1..6).random()
    }

    private fun setDiceImage(value :Int):Int{
        return when (value){
            1 -> R.drawable.dice_01
            2 -> R.drawable.dice_02
            3 -> R.drawable.dice_03
            4 -> R.drawable.dice_04
            5 -> R.drawable.dice_05
            else -> R.drawable.dice_06
        }
    }

    private fun drawDiceImage(value :Int){
        diceImage = findViewById(R.id.diceImage)
        diceImage.setImageResource(value)
    }

    private fun add(){
        totalResult += diceValue
    }

    private fun subtract(){
        totalResult -= diceValue
    }

    private fun resetValue(){
        totalResult = 0
    }

    private fun updateResult(){
        total.text ="$totalResult"
    }

    private fun updateText(){
        if(totalResult < 20){
            total.setTextColor(Color.parseColor("#000000"))
        } else if(totalResult > 20){
            total.setTextColor(Color.parseColor("#FF4033"))
        } else{
            total.setTextColor(Color.parseColor("#3CB043"))
        }
    }

    private fun rollButtonOn(){
        rollButton.isEnabled = true
    }

    private fun rollButtonOff(){
        rollButton.isEnabled = false
    }

    private fun updateDisplay(){
        updateResult()
        updateText()
    }
}