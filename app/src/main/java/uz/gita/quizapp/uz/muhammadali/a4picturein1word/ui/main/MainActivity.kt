package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.R
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.repository.AppRepository
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play.InfoActivity
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play.PlayActivity

class MainActivity : AppCompatActivity() {

    private val repository:AppRepository = AppRepository.getInstance()
    private val question:QuestionData = repository.getList().get(repository.getCurrentPosition())

    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var image3: ImageView
    private lateinit var image4: ImageView
    private lateinit var tvLevel: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        image1 = findViewById(R.id.iv_pic_1)
        image2 = findViewById(R.id.iv_pic_2)
        image3 = findViewById(R.id.iv_pic_3)
        image4 = findViewById(R.id.iv_pic_4)
        tvLevel = findViewById(R.id.level)


        image1.setImageResource(question.img1)
        image2.setImageResource(question.img2)
        image3.setImageResource(question.img3)
        image4.setImageResource(question.img4)

        tvLevel.text = repository.getCurrentPosition().toString()

        findViewById<CardView>(R.id.btn_play).setOnClickListener{
            startActivity(Intent(this@MainActivity, PlayActivity::class.java))
        }
        findViewById<CardView>(R.id.btn_info).setOnClickListener{
            startActivity(Intent(this, InfoActivity::class.java))
        }
    }
}