package com.example.binarysearchguessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var currentNum = 0
    private var lowerBound = 0
    private var upperBound = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickRandomNumber()
    }

    private fun pickRandomNumber() {
        val randNum = Random.nextInt(0,101)
        this.currentNum = randNum
        return Toast.makeText(this, "Current Number:" + this.currentNum, Toast.LENGTH_LONG).show()
    }

    fun submitNumber(view: View) {
        val userSubmittedNum = findViewById<EditText>(R.id.et_userGuess).text.toString().toInt()
        when {
            isNumberValid(userSubmittedNum) == -1 -> {
                return Toast.makeText(this, "Enter a number more than " + this.lowerBound, Toast.LENGTH_LONG).show()
            }
            isNumberValid(userSubmittedNum) == 0 -> {
                return Toast.makeText(this, "Enter a number less than " + this.upperBound, Toast.LENGTH_LONG).show()
            }
            else -> {
                return when {
                    isNumberCorrect(userSubmittedNum) == 1 -> {
                        // correct answer, display success message
                        Toast.makeText(this, "Correct! The number is " + this.currentNum, Toast.LENGTH_LONG).show()
                    }
                    isNumberCorrect(userSubmittedNum) == -1 -> {
                        // user guessed a number lower than lowerBound
                        Toast.makeText(this, "Incorrect! Choose a number higher than " + this.lowerBound, Toast.LENGTH_LONG).show()
                    }
                    else ->
                        // user guessed a number lower than lowerBound
                        Toast.makeText(this, "Incorrect! Choose a number lower than " + this.upperBound, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isNumberCorrect(userSubmittedNum: Int): Int {
        return when {
            userSubmittedNum == this.currentNum -> {
                resetUserNumberField()
                1
            }
            userSubmittedNum < this.currentNum -> {
                this.lowerBound = userSubmittedNum
                val lowerBoundText = findViewById<TextView>(R.id.tv_lowerBound)
                lowerBoundText.text = this.lowerBound.toString()
                resetUserNumberField()
                -1
            }
            else -> {
                this.upperBound = userSubmittedNum
                val upperBoundText = findViewById<TextView>(R.id.tv_upperBound)
                upperBoundText.text = this.upperBound.toString()
                resetUserNumberField()
                0
            }
        }
    }

    private fun isNumberValid(userSubmittedNum: Int): Int {
        if(userSubmittedNum < this.lowerBound) {
            resetUserNumberField()
            return -1
        } else if (userSubmittedNum > this.upperBound) {
            resetUserNumberField()
            return 0
        }
        return 1
    }

    private fun resetUserNumberField() {
        val userNumberField = findViewById<EditText>(R.id.et_userGuess)
        userNumberField.setText("")
    }

    fun endGame(view: View) {
        // reset user input field
        resetUserNumberField()

        // reset upper and lower bound text to 100 and 0 respectively
        val upperBoundText = findViewById<TextView>(R.id.tv_upperBound)
        upperBoundText.text = "100"
        val lowerBoundText = findViewById<TextView>(R.id.tv_lowerBound)
        lowerBoundText.text = "0"

        // choose a new random number
        pickRandomNumber()
    }
}