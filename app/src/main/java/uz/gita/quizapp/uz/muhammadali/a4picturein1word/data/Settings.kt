package uz.gita.quizapp.uz.muhammadali.a4picturein1word.data

import android.content.Context
import android.content.SharedPreferences

class Settings private constructor(context: Context) {
    private val SHARED_PREF = "SHARED_PREF"
    private val CURRENT_POS = "CURRENT_POS"
    private val CASH = "CASH"

    private val pref: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = pref.edit()

    var currentPos: Int
        set(value) = editor.putInt(CURRENT_POS, value).apply()
        get() = pref.getInt(CURRENT_POS, 0)
    var cash:Int
    get() = pref.getInt(CASH, 50)
    set(value) = editor.putInt(CASH, value).apply()

    companion object{
        private lateinit var instance:Settings

        fun init(context: Context){
            instance = Settings(context)
        }
        fun getInstance() = instance
    }


}
