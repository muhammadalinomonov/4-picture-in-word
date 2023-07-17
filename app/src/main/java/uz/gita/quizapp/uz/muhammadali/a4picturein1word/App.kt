package uz.gita.quizapp.uz.muhammadali.a4picturein1word

import android.app.Application
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.data.Settings

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        Settings.init(this)
    }
}