package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.R

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }
}