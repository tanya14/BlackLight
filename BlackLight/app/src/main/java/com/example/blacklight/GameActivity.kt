package com.example.blacklight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {

    private val colorLayout1 by lazy {
        findViewById<View?>(R.id.colorLayout1)
    }
    private val colorLayout2 by lazy {
        findViewById<View?>(R.id.colorLayout2)
    }
    private val colorLayout3 by lazy {
        findViewById<View?>(R.id.colorLayout3)
    }
    private val colorLayout4 by lazy {
        findViewById<View?>(R.id.colorLayout4)
    }
    private val scoreTV by lazy {
        findViewById<TextView?>(R.id.scoreTV)
    }
    private var alertDialog: AlertDialog? = null

    private val handler: Handler? = Handler()
    private var runnable: Runnable? = null
    private var isGreyClicked: Boolean = true
    private var randomNumber: Int = 0
    private var score: Int = 0
    private var doubleBackToExitPressedOnce: Boolean = false

    companion object {
        private const val GRAYTIMEINTERVAL: Long = 1000
        private const val BACKTIMEINTERVAL: Long = 2000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnClickListener()
        showAlert(getString(R.string.launch), getString(R.string.start)) { isDismissed ->
            if (isDismissed) {
                score = 0
                val str = "${getString(R.string.score)} $score"
                scoreTV?.text = str
                selectRandomNumber(randomNumber)
                setHandler()
            } else {
                finish()
            }
        }
    }

    private fun selectRandomNumber(previousNumber: Int): Int {
        randomNumber = (1..4).random()
        while (previousNumber == randomNumber) {
            randomNumber = (1..4).random()
        }
        return randomNumber
    }

    private fun colorViewToGray(randomNumber: Int) {
        when (randomNumber) {
            resources.getInteger(R.integer.constant1) -> {
                colorLayout1?.background = ContextCompat.getDrawable(this, R.color.grey)
                colorLayout2?.background = ContextCompat.getDrawable(this, R.color.lightBlue)
                colorLayout3?.background = ContextCompat.getDrawable(this, R.color.yellow)
                colorLayout4?.background = ContextCompat.getDrawable(this, R.color.greenLite)
            }
            resources.getInteger(R.integer.constant2) -> {
                colorLayout2?.background = ContextCompat.getDrawable(this, R.color.grey)
                colorLayout1?.background = ContextCompat.getDrawable(this, R.color.orange)
                colorLayout3?.background = ContextCompat.getDrawable(this, R.color.yellow)
                colorLayout4?.background = ContextCompat.getDrawable(this, R.color.greenLite)
            }
            resources.getInteger(R.integer.constant3) -> {
                colorLayout3?.background = ContextCompat.getDrawable(this, R.color.grey)
                colorLayout1?.background = ContextCompat.getDrawable(this, R.color.orange)
                colorLayout2?.background = ContextCompat.getDrawable(this, R.color.lightBlue)
                colorLayout4?.background = ContextCompat.getDrawable(this, R.color.greenLite)
            }
            resources.getInteger(R.integer.constant4) -> {
                colorLayout4?.background = ContextCompat.getDrawable(this, R.color.grey)
                colorLayout1?.background = ContextCompat.getDrawable(this, R.color.orange)
                colorLayout2?.background = ContextCompat.getDrawable(this, R.color.lightBlue)
                colorLayout3?.background = ContextCompat.getDrawable(this, R.color.yellow)
            }
            else -> {
                colorLayout1?.background = ContextCompat.getDrawable(this, R.color.orange)
                colorLayout2?.background = ContextCompat.getDrawable(this, R.color.lightBlue)
                colorLayout3?.background = ContextCompat.getDrawable(this, R.color.yellow)
                colorLayout4?.background = ContextCompat.getDrawable(this, R.color.greenLite)
            }
        }
    }

    private fun setOnClickListener() {
        val onClickListener: View.OnClickListener = View.OnClickListener {
            when (it.id) {
                R.id.colorLayout1 -> {
                    if (randomNumber == 1) {
                        val str = "${getString(R.string.score)} ${++score}"
                        scoreTV?.text = str
                        isGreyClicked = true
                    } else {
                        isGreyClicked = false
                    }
                }
                R.id.colorLayout2 -> {
                    if (randomNumber == 2) {
                        val str = "${getString(R.string.score)} ${++score}"
                        scoreTV?.text = str
                        isGreyClicked = true

                    } else {
                        isGreyClicked = false
                    }
                }
                R.id.colorLayout3 -> {
                    if (randomNumber == 3) {
                        val str = "${getString(R.string.score)} ${++score}"
                        scoreTV?.text = str
                        isGreyClicked = true

                    } else {
                        isGreyClicked = false
                    }
                }
                R.id.colorLayout4 -> {
                    if (randomNumber == 4) {
                        val str = "${getString(R.string.score)} ${++score}"
                        scoreTV?.text = str
                        isGreyClicked = true

                    } else {
                        isGreyClicked = false
                    }
                }
            }
        }
        colorLayout1?.setOnClickListener(onClickListener)
        colorLayout2?.setOnClickListener(onClickListener)
        colorLayout3?.setOnClickListener(onClickListener)
        colorLayout4?.setOnClickListener(onClickListener)
    }

    private fun setHandler() {
        runnable = Runnable {
            if (isGreyClicked) {
                colorViewToGray(selectRandomNumber(randomNumber))
                isGreyClicked = false
                setHandler()
            } else {
                restartGame()
            }
        }
        handler?.postDelayed(runnable!!, GRAYTIMEINTERVAL)
    }


    private fun restartGame() {
        randomNumber = 0
        colorViewToGray(randomNumber)
        showAlert(
            "${getString(R.string.gameOverMsg)} $score",
            getString(R.string.restart)
        ) { isDismissed ->
            if (isDismissed) {
                isGreyClicked = true
                score = 0
                val str = "${getString(R.string.score)} $score"
                scoreTV?.text = str
                selectRandomNumber(randomNumber)
                setHandler()
            } else {
                finish()
            }
        }
    }

    private fun showAlert(
        message: String,
        positiveBtnText: String,
        dismissalCallback: (isDismissed: Boolean) -> Unit
    ) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(getString(R.string.app_name))
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(positiveBtnText) { dialog, _ ->
                dismissalCallback.invoke(true)
                dialog?.dismiss()
            }
            .setNegativeButton(getString(R.string.quit)) { dialog, _ ->
                dismissalCallback.invoke(false)
                dialog?.dismiss()
            }

        alertDialog = alertDialogBuilder.create()
        alertDialog?.show()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (runnable != null) {
                handler?.removeCallbacks(runnable!!)
            }
            alertDialog?.dismiss()
            finish()
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.backPressMsg), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, BACKTIMEINTERVAL)
        return
    }

    override fun onDestroy() {
        super.onDestroy()
        if (runnable != null) {
            handler?.removeCallbacks(runnable!!)
        }
        alertDialog?.dismiss()
    }
}

