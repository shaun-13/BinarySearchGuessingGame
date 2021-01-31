package com.example.binarysearchguessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private var numberToGuess = 0
    private var lowerBound = 0
    private var upperBound = 100
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickRandomNumber()
    }

    private fun pickRandomNumber() {
        val randNum = Random.nextInt(101)
        this.numberToGuess = randNum
    }

    fun submitNumber(view: View) {
        val userSubmittedNum = findViewById<EditText>(R.id.et_userGuess).text.toString().toInt()
        numberOfGuesses++
        when {
            isNumberValid(userSubmittedNum) == -1 -> {
                return Toast.makeText(this, "Enter a number more than $lowerBound", Toast.LENGTH_SHORT).show()
            }
            isNumberValid(userSubmittedNum) == 0 -> {
                return Toast.makeText(this, "Enter a number less than $upperBound", Toast.LENGTH_SHORT).show()
            }
            else -> {
                return when {
                    isNumberCorrect(userSubmittedNum) == 1 -> {
                        // correct answer, display success message
                        Toast.makeText(this, "Correct! The number is $numberToGuess! You took $numberOfGuesses guesses!", Toast.LENGTH_LONG).show()
                        val submitBtn = findViewById<Button>(R.id.btn_submitNum)
                        submitBtn.isEnabled = false
                        
                    }
                    isNumberCorrect(userSubmittedNum) == -1 -> {
                        // user guessed a number lower than lowerBound
                        Toast.makeText(this,
                            "Incorrect! Choose a number higher than $lowerBound", Toast.LENGTH_SHORT).show()

                    }
                    else ->
                        // user guessed a number lower than lowerBound
                        Toast.makeText(this,
                            "Incorrect! Choose a number lower than $upperBound", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isNumberCorrect(userSubmittedNum: Int): Int {
        return when {
            userSubmittedNum == numberToGuess -> {
                resetUserNumberField()
                1
            }
            userSubmittedNum < this.numberToGuess -> {
                lowerBound = userSubmittedNum
                val lowerBoundText = findViewById<TextView>(R.id.tv_lowerBound)
                lowerBoundText.text = "$lowerBound"
                resetUserNumberField()
                -1
            }
            else -> {
                upperBound = userSubmittedNum
                val upperBoundText = findViewById<TextView>(R.id.tv_upperBound)
                upperBoundText.text = "$upperBound"
                resetUserNumberField()
                0
            }
        }
    }

    private fun isNumberValid(userSubmittedNum: Int): Int {
        if(userSubmittedNum < lowerBound) {
            resetUserNumberField()
            return -1
        } else if (userSubmittedNum > upperBound) {
            resetUserNumberField()
            return 0
        }
        return 1
    }

    private fun resetUserNumberField() {
        val userNumberField = findViewById<EditText>(R.id.et_userGuess)
        userNumberField.setText("")
    }

    // to restart the whole game
    fun restartGame(view: View) {
        // reset user input field
        resetUserNumberField()

        // reset upper and lower bound text to 100 and 0 respectively
        val upperBoundText = findViewById<TextView>(R.id.tv_upperBound)
        upperBoundText.text = "100"
        val lowerBoundText = findViewById<TextView>(R.id.tv_lowerBound)
        lowerBoundText.text = "0"

        lowerBound = 0
        upperBound = 100

        // choose a new random number
        pickRandomNumber()
        Toast.makeText(this, "Game has restarted!", Toast.LENGTH_SHORT).show()
    }

    fun exitApp(view: View) {
        // Exits the game app
        exitProcess(0)
    }
}