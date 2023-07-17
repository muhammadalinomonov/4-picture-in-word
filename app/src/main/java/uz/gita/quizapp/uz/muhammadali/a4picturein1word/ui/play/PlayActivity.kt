package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

import android.os.Vibrator

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.R
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData
import java.util.logging.Handler

class PlayActivity : AppCompatActivity(), PlayContract.View {
    private val imagesList = ArrayList<ImageView>(4)
    private val answerButtons = ArrayList<TextView>(8)
    private val variantButtons = ArrayList<TextView>(16)
    private lateinit var level: TextView
    private lateinit var coin: TextView
    private lateinit var answerLine: LinearLayout

    private lateinit var vibrator: Vibrator
    private var counter = 0

    private val presenter: PlayContract.Presenter = PlayPresenter(this)

    private var currentPosition: Int = presenter.getCurrentPos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        answerLine = findViewById(R.id.answerLine)

        loadViews()
        clickVariantButtons()
        clickAnswerButtons()
        presenter.loadNextQuestion()

        findViewById<ImageView>(R.id.lamp).setOnClickListener {
            var answer = ""
            answerButtons.forEach {
                answer += it.text.toString()
            }
            if(presenter.getCash() >= 5 && answer.length != presenter.getCurrentAnswer().answer.length){
                showEnoughMoneyDialog()
            }else{
                showLessMoneyDialog()
            }
//            clickHelp()
        }
    }

    private fun loadViews() {
        coin = findViewById(R.id.dollar)
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        level = findViewById(R.id.level)

        imagesList.add(findViewById(R.id.picture1))
        imagesList.add(findViewById(R.id.picture2))
        imagesList.add(findViewById(R.id.picture3))
        imagesList.add(findViewById(R.id.picture4))

        val answerLine = findViewById<LinearLayout>(R.id.answerLine)

        for (i in 0 until answerLine.childCount) {
            answerButtons.add(answerLine.getChildAt(i) as TextView)
        }
        val variantLine1 = findViewById<LinearLayout>(R.id.variantLine1)
        for (i in 0 until variantLine1.childCount) {
            variantButtons.add(variantLine1.getChildAt(i) as TextView)
        }
        val variantLine2 = findViewById<LinearLayout>(R.id.variantLine2)
        for (i in 0 until variantLine2.childCount) {
            variantButtons.add(variantLine2.getChildAt(i) as TextView)
        }


    }

    private fun clickVariantButtons() {

        for (i in variantButtons.indices) {
            variantButtons[i].setOnClickListener {
                counter++

                answerButtons.forEach{
                    it.setBackgroundResource(R.drawable.bg_answer)
                }
                presenter.btnVariantClicked(variantButtons[i].text.toString(), i)
                if (counter == presenter.getCurrentAnswer().answer.length) {
                    variantButtons.forEach {
                        it.isClickable = false

                    }

                    var text = ""
                    answerButtons.forEach {
                        text += it.text.toString()
                    }
                    checkAnswer(text)
                }
            }
        }
    }


    private fun checkAnswer(text: String) {
        if (text == presenter.getCurrentAnswer().answer) {
            presenter.addCash()
            presenter.showCash()
            currentPosition++
            if (presenter.getCurrentPos() != 9) {
                //


                answerButtons.forEach {
                    it.setBackgroundResource(R.drawable.bg_correct_answer)
                }
                android.os.Handler(Looper.getMainLooper()).postDelayed({

                    answerButtons.forEach {
                        it.setBackgroundResource(R.drawable.bg_answer)
                    }
                    clearData()

                    presenter.loadNextQuestion()
                }, 900)



                //


            } else {
                showFinishDialog()
            }
        } else {
            for (i in answerButtons.indices){
                answerButtons[i].setBackgroundResource(R.drawable.bg_wrong)
            }
            vibrator.vibrate(500L)

            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clickAnswerButtons() {
        for (i in answerButtons.indices) {

            answerButtons[i].setOnClickListener {
                it.setBackgroundResource(R.drawable.bg_answer)
                counter--
                variantButtons.forEach {
                    it.isClickable = true
                }

                val tagPos = answerButtons[i].tag as Int
                presenter.btnAnswerClicked(answerButtons[i].text.toString(), i, tagPos)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun describeQuestion(questionData: QuestionData) {
        coin.text = presenter.getCash().toString()
        level.text = presenter.getCurrentAnswer().id.toString()
        imagesList[0].setImageResource(questionData.img1)
        imagesList[1].setImageResource(questionData.img2)
        imagesList[2].setImageResource(questionData.img3)
        imagesList[3].setImageResource(questionData.img4)

        for (i in variantButtons.indices) {
            variantButtons[i].text = questionData.variant[i].toString()
            variantButtons[i].isEnabled = true
        }

        for (i in answerButtons.indices) {
            answerButtons[i].text = ""
            answerButtons[i].isEnabled = false
        }

        resizeAnswerButtons(questionData.answer)
        describeVariantButtons(questionData.variant)
    }

    private fun clearData() {
        answerButtons.forEach {
            if (it.isVisible) {
                it.text = ""
            }
        }
        variantButtons.forEach {
            it.text = ""
            it.isClickable = true
        }
        counter = 0
    }


    override fun getFirstEmptyPosition(): Int {
        for (i in answerButtons.indices) {
            if (answerButtons[i].text.isEmpty() && answerButtons[i].visibility == View.VISIBLE) {
                return i
            }
        }
        return -1
    }

    override fun showCash(cash: Int) {
        coin.text = cash.toString()
    }

    override fun showValueToAnswer(text: String, position: Int) {
        val index = getFirstEmptyPosition()

        if (index != -1) {
            answerButtons[index].text = text
            answerButtons[index].tag = position
            variantButtons[position].text = ""
            variantButtons[position].isEnabled = false
            answerButtons[index].isEnabled = true
        }
    }

    override fun showValueToVariant(text: String, index: Int, tagPos: Int) {

        variantButtons[tagPos].text = text
        variantButtons[tagPos].isEnabled = true
        answerButtons[index].text = ""
        answerButtons[index].isEnabled = false
    }

    private fun resizeAnswerButtons(answer: String) {

        for (i in answer.indices) {
            answerButtons[i].visibility = View.VISIBLE
        }
        for (i in answer.length until answerButtons.size) {
            answerButtons[i].visibility = View.GONE
        }
    }

    private fun describeVariantButtons(variant: String) {
        for (i in variant.indices) {
            variantButtons[i].text = variant[i].toString()
        }
    }

    private fun showEnoughMoneyDialog(){
        val dialogEnough = Dialog(this)

        dialogEnough.setContentView(R.layout.item_dialog_enough)
        dialogEnough.setCancelable(true)
        dialogEnough.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEnough.show()

        dialogEnough.findViewById<AppCompatButton>(R.id.yes).setOnClickListener {
            clickHelp()
            presenter.cutCash()
            presenter.showCash()
            MediaPlayer.create(this, R.raw.cash_register).start()
            dialogEnough.dismiss()

            var answer = ""
            answerButtons.forEach {
                answer += it.text.toString()
            }

            val currentAnswer = presenter.getCurrentAnswer().answer

        }
        dialogEnough.findViewById<AppCompatButton>(R.id.notNow).setOnClickListener{
            dialogEnough.dismiss()
        }

    }

    private fun showLessMoneyDialog(){
        val dialogLess = Dialog(this)

        dialogLess.setContentView(R.layout.item_dialog_less)

        dialogLess.window !!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLess.setCancelable(true)
        dialogLess.show()

        dialogLess.findViewById<AppCompatButton>(R.id.ok).setOnClickListener{
            dialogLess.dismiss()
        }

    }

    private fun clickHelp() {
        if (counter < presenter.getCurrentAnswer().answer.length)
            if (presenter.getCash() >= 5) {
                var index = getFirstEmptyPosition()
                var answerLetter = presenter.getCurrentAnswer().answer[index].toString()
                for (i in variantButtons.indices) {
                    if (variantButtons[i].text.toString() == answerLetter) {
                        counter++
                        variantButtons[i].isClickable = false
                        variantButtons[i].text = ""

                        if (index != -1) {
                            answerButtons[index].text = answerLetter
                            answerButtons[index].tag = i
                            variantButtons[i].isEnabled = false
                        }

                        if (presenter.getCurrentAnswer().answer.length == counter) {
                            variantButtons.forEach {
                                it.isClickable = false
                            }
                            var text = ""
                            answerButtons.forEach {
                                text += it.text.toString()
                            }
                            checkAnswer(text)
                        }
                        break
                    }
                }
            }
    }
    @SuppressLint("SetTextI18n")
    private fun showFinishDialog() {
        val dialogFinish = Dialog(this)

        dialogFinish.setContentView(R.layout.item_dialog_finish)
        dialogFinish.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFinish.setCancelable(false)
        dialogFinish.show()

        val restartButton = dialogFinish.findViewById<AppCompatButton>(R.id.restart)
        val quitButton = dialogFinish.findViewById<AppCompatButton>(R.id.quit)
        val levelTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.level)
        val coinTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.coin)

        levelTextView.text = (presenter.getCurrentPos() + 1).toString()
        coinTextView.text = presenter.getCash().toString()

        restartButton.setOnClickListener{
            presenter.setCurrentPosition(0)
            dialogFinish.dismiss()
            presenter.setCoin()
            finish()
        }
        quitButton.setOnClickListener{
            dialogFinish.dismiss()
            presenter.setCurrentPosition(0)
            presenter.setCoin()
            finishAffinity()
        }

    }
}
/* private val imagesList = ArrayList<AppCompatImageView>(4)
 private val answerButtons = ArrayList<AppCompatTextView>(8)
 private val variantButtons = ArrayList<AppCompatTextView>(16)
 private lateinit var coin: AppCompatTextView
 private var counter = 0

 private lateinit var answerLine: LinearLayout
 private lateinit var animation: Animation

 private val presenter: PlayContract.Presenter = PlayPresenter(this)
 private var currentPosition: Int = presenter.getCurrentPos()

 private lateinit var tvLevel: TextView

 @SuppressLint("MissingInflatedId")
 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_play)

     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        window.navigationBarColor = resources.getColor(R.color.navigationBarColor)

     answerLine = findViewById(R.id.answerLine)
     loadViews()
     clickVariantButtons()
     clickAnswerButtons()
//        attachClickListeners()
     Log.d("ZZZ", "CurrentPosition -> ${presenter.getCurrentPos()}")
     presenter.loadNextQuestion()

     findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
         finish()
     }

     findViewById<AppCompatImageView>(R.id.lamp).setOnClickListener {

         var answer = ""
         answerButtons.forEach {
             answer += it.text.toString()
         }

         if (presenter.getCash() >= 5 && answer.length != presenter.getCurrentAnswer().answer.length) {
//                showEnoughMoneyDialog()
         } else {
//                showLessMoneyDialog()
         }
     }
 }

 private fun loadViews() {
     coin = findViewById(R.id.dollar)
     tvLevel = findViewById(R.id.level)
     imagesList.add(findViewById(R.id.picture1))
     imagesList.add(findViewById(R.id.picture2))
     imagesList.add(findViewById(R.id.picture3))
     imagesList.add(findViewById(R.id.picture4))

     val answerLine = findViewById<LinearLayout>(R.id.answerLine)

     for (i in 0 until answerLine.childCount) {
         answerButtons.add(answerLine.getChildAt(i) as AppCompatTextView)
     }

     val variantLine1 = findViewById<LinearLayout>(R.id.variantLine1)

     for (i in 0 until variantLine1.childCount) {
         variantButtons.add(variantLine1.getChildAt(i) as AppCompatTextView)
     }

     val variantLine2 = findViewById<LinearLayout>(R.id.variantLine2)

     for (i in 0 until variantLine2.childCount) {
         variantButtons.add(variantLine2.getChildAt(i) as AppCompatTextView)
     }
 }

 private fun clickVariantButtons() {
     *//*for (i in 0 until variantButtons.size) {
            variantButtons[i].setOnClickListener {
                if (getFirstEmptyPosition() != -1) {

                    val text = (it as AppCompatTextView).text.toString()

                    presenter.clickVariantButton(text, i)
                    presenter.btnVariantClicked(text, i)
                    it.text = ""
                    it.isClickable = false
                    counter++

                    if (counter == presenter.getCurrentAnswer().answer.length) {
                        var text = ""
                        answerButtons.forEach { txt ->
                            text += txt.text.toString()
                        }
                        checkAnswer(text)
                    }
                }
            }
        }*//*
        for (i in variantButtons.indices) {
            variantButtons[i].setOnClickListener {
                counter++

                presenter.btnVariantClicked(variantButtons[i].text.toString(), i)
                Log.d("EEE","Counter clickVariantButtons basilg'an kezde -> $counter")

                if (counter == presenter.getCurrentAnswer().answer.length) {

                    variantButtons.forEach {
                        it.isClickable = false
                    }

                    var text = ""
                    answerButtons.forEach { txt ->
                        text += txt.text.toString()
                    }
                    Log.d("EEE","Answer Buttondag'i text -> $text")
                    checkAnswer(text)
                }
            }
        }
    }

    private fun checkAnswer(text: String) {

        Log.d("EEE","User Answer -> $text")

        if (text == presenter.getCurrentAnswer().answer) {
            presenter.addCash()
            presenter.showCash()
//            MediaPlayer.create(this, R.raw.popolnen_balance).start()
            currentPosition++
            presenter.setCurrentPosition(currentPosition)
            Log.d("ZZZ", "CurrentPosition -> ${presenter.getCurrentPos()}")

            if (presenter.getCurrentPos() < 10) {
                clearData()
                presenter.loadNextQuestion()
            } else {
//                showFinishDialog()
            }

//          presenter.loadNextQuestion()

        } else {
//            animation = AnimationUtils.loadAnimation(this, R.anim.animation_shake)
//            answerLine.startAnimation(animation)
        }
    }

    private fun clickAnswerButtons() {
        *//*answerButtons.forEach { answer ->
            answer.setOnClickListener {



                if (answer.text.toString() != "") {
                    val text = (it as AppCompatTextView).text.toString()
                    presenter.clickAnswerButton(text)
                    it.text = ""
                    counter--
                }
            }
        }*//*

        for (i in answerButtons.indices) {
            answerButtons[i].setOnClickListener {
                counter--

                variantButtons.forEach {
                    it.isClickable = true
                }

                Log.d("EEE","Counter clickAnswerButtons basilg'an kezde -> $counter")
                val tagPos = answerButtons[i].tag as Int
                presenter.btnAnswerClicked(answerButtons[i].text.toString(), i, tagPos)
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    override fun describeQuestion(question: QuestionData) {
        coin.text = presenter.getCash().toString()
        tvLevel.text = presenter.getCurrentAnswer().id.toString()
        imagesList[0].setImageResource(question.img1)
        imagesList[1].setImageResource(question.img2)
        imagesList[2].setImageResource(question.img3)
        imagesList[3].setImageResource(question.img4)

        for (i in variantButtons.indices) {
            variantButtons[i].text = question.variant[i].toString()
            variantButtons[i].isEnabled = true
        }
        for (i in answerButtons.indices) {
            answerButtons[i].text = ""
            answerButtons[i].isEnabled = false
        }


        resizeAnswerButton(question.answer)
        describeVariantButtons(question.variant)
    }

    *//*override fun showAnswer(text: String, index: Int) {
        answerButtons[index].text = text

        *//**//* counter++

         if (counter == presenter.getCurrentAnswer().answer.length) {
             var text = ""
             answerButtons.forEach {
                 text += it.text.toString()
             }

             Toast.makeText(this, presenter.getCurrentAnswer().answer, Toast.LENGTH_SHORT).show()

             if (text == presenter.getCurrentAnswer().answer) {

                 Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()

                 clearData()
                 presenter.loadNextQuestion()
             } else {
                 Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show()
             }
         }*//**//*
    }*//*

    private fun clearData() {
        answerButtons.forEach {
            if (it.isVisible) {
                it.text = ""
            }
        }

        variantButtons.forEach {
            it.text = ""
            it.isClickable = true
        }
        counter = 0
    }

    override fun getFirstEmptyPosition(): Int {
        for (i in answerButtons.indices) {
            if (answerButtons[i].text.isEmpty() && answerButtons[i].visibility == View.VISIBLE) {
                return i
            }
        }
        return -1
    }

    *//*override fun showVariant(text: String, index: Int, buttonState: Boolean) {
        variantButtons[index].text = text
        variantButtons[index].isClickable = buttonState
    }*//*

    override fun showCash(cash: Int) {
        coin.text = cash.toString()
    }

    override fun showValueToAnswer(text: String, position: Int) {
        val index = getFirstEmptyPosition()
        if (index != -1) {
            answerButtons[index].text = text
            answerButtons[index].tag = position
            variantButtons[position].text = ""
            variantButtons[position].isEnabled = false
            answerButtons[index].isEnabled = true
        }
    }

    override fun showValueToVariant(text: String, index: Int, tagPos: Int) {
        variantButtons[tagPos].text = text
        variantButtons[tagPos].isEnabled = true
        answerButtons[index].text = ""
        answerButtons[index].isEnabled = false
    }

    private fun resizeAnswerButton(answer: String) {
        for (i in answer.indices) {
            answerButtons[i].visibility = View.VISIBLE
        }

        for (i in answer.length until answerButtons.size) {
            answerButtons[i].visibility = View.GONE
        }
    }

    private fun describeVariantButtons(variant: String) {
        for (i in variant.indices) {
            variantButtons[i].text = variant[i].toString()
        }
    }


//    private fun showEnoughMoneyDialog() {
//
//        val dialogEnough = Dialog(this)
//        dialogEnough.setContentView(R.layout.item_dialog_enough)
//        dialogEnough.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialogEnough.setCancelable(true)
//        dialogEnough.show()
//
//        dialogEnough.findViewById<AppCompatButton>(R.id.yes).setOnClickListener {
//            presenter.cutCash()
//            presenter.showCash()
//            MediaPlayer.create(this, R.raw.cash_register).start()
//            dialogEnough.dismiss()
//
//            var answer = ""
//            answerButtons.forEach {
//                answer += it.text.toString()
//            }
//
//            val currentAnswer = presenter.getCurrentAnswer().answer
//            val helperLetter = findViewById<AppCompatTextView>(R.id.helper_text)
//
//            helperLetter.text = currentAnswer.substring(answer.length, answer.length + 1)
//
//            val hugeSmallAnim = AnimationUtils.loadAnimation(this, R.anim.hugesmall)
//
//            hugeSmallAnim.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(p0: Animation?) {
//                    helperLetter.isVisible = true
//                }
//
//                override fun onAnimationEnd(p0: Animation?) {
//                    helperLetter.isVisible = false
//                }
//
//                override fun onAnimationRepeat(p0: Animation?) {
//                }
//
//            })
//
//            helperLetter.startAnimation(hugeSmallAnim)
//
//        }
//
//
//        dialogEnough.findViewById<AppCompatButton>(R.id.notNow).setOnClickListener {
//            dialogEnough.dismiss()
//        }
//    }

//    private fun showLessMoneyDialog() {
//        val dialogLess = Dialog(this)
//        dialogLess.setContentView(R.layout.item_dialog_less)
//        dialogLess.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialogLess.setCancelable(true)
//        dialogLess.show()
//
//        dialogLess.findViewById<AppCompatButton>(R.id.ok).setOnClickListener {
//            dialogLess.dismiss()
//        }
//    }

   *//* private fun showFinishDialog() {
        val dialogFinish = Dialog(this)
        dialogFinish.setContentView(R.layout.item_dialog_finish)
        dialogFinish.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFinish.setCancelable(false)
        dialogFinish.show()

        val restartButton = dialogFinish.findViewById<AppCompatButton>(R.id.restart)
        val quitButton = dialogFinish.findViewById<AppCompatButton>(R.id.quit)
        val levelTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.level)
        val coinTextView = dialogFinish.findViewById<AppCompatTextView>(R.id.coin)

        levelTextView.text = presenter.getCurrentPos().toString()
        coinTextView.text = presenter.getCash().toString()

        restartButton.setOnClickListener {
            presenter.setCurrentPosition(0)
            dialogFinish.dismiss()
            presenter.setCoin()
            finish()
        }

        quitButton.setOnClickListener {
            dialogFinish.dismiss()
            presenter.setCurrentPosition(0)
            presenter.setCoin()
            finishAffinity()
        }
    }*/


