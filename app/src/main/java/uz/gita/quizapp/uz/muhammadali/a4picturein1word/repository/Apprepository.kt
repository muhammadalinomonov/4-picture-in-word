package uz.gita.quizapp.uz.muhammadali.a4picturein1word.repository

import uz.gita.quizapp.uz.muhammadali.a4picturein1word.R
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.data.Settings
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData
import java.util.Collections

class AppRepository private constructor() {
    private var sharedPref = Settings.getInstance()

    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized)) {
                instance = AppRepository()
            }

            return instance
        }
    }

    private var list = ArrayList<QuestionData>()
    fun getList() = list
    init {
        load()
    }

    private fun load() {
        list.add(
            QuestionData(
                1,
                R.drawable.eatofusa,
                R.drawable.p3ofusa,
                R.drawable.p4ofusa,
                R.drawable.eat2ofusa,
                "MRBKFDGAHIOPIECA",
                "AMERICA"
            )
        )

        list.add(
            QuestionData(
                2,
                R.drawable.p1arabia,
                R.drawable.p4arabia,
                R.drawable.p3arabia,
                R.drawable.p2arabia,
                "SAABTWKFDHPUITCK",
                "SAUDIA"
            )
        )

        list.add(
            QuestionData(
                3,
                R.drawable.p1ofuzb,
                R.drawable.p4uzb,
                R.drawable.p3uzb,
                R.drawable.p2uzb,
                "NZAFRBTWKEIETUCK",
                "UZB"
            )
        )

        list.add(
            QuestionData(
                4,
                R.drawable.p1france,
                R.drawable.p4france,
                R.drawable.p3france,
                R.drawable.p2france,
                "NBGACFRBEIEUTNFK",
                "FRANCE"
            )
        )

        list.add(
            QuestionData(
                5,
                R.drawable.p4koreas,
                R.drawable.p1korea,
                R.drawable.p2korea,
                R.drawable.p3koreasouth,
                "RNBGEOAEULTNLCKW",
                "KOREA"
            )
        )

        list.add(
            QuestionData(
                6,
                R.drawable.p4italy,
                R.drawable.p1italy,
                R.drawable.p2italy,
                R.drawable.p3italy,
                "YNBIGENOPAEULKTN",
                "ITALY"
            )
        )

        list.add(
            QuestionData(
                7,
                R.drawable.p1japan,
                R.drawable.p4japan,
                R.drawable.p3japan,
                R.drawable.p2japan,
                "ANABIRPEULPKLTEJ",
                "JAPAN"
            )
        )

        list.add(
            QuestionData(
                8,
                R.drawable.p4mexico,
                R.drawable.p2mexico,
                R.drawable.p1mexico,
                R.drawable.p3mexico,
                "ECIRNPEGAMLRPOEX",
                "MEXICO"
            )
        )

        list.add(
            QuestionData(
                9,
                R.drawable.p1turkey,
                R.drawable.p3turkey,
                R.drawable.p4turkey,
                R.drawable.p2turkey,
                "RNRPEGTAUKREPOYE",
                "TURKEY"
            )
        )

        list.add(
            QuestionData(
                10,
                R.drawable.p1poland,
                R.drawable.p4poland,
                R.drawable.p3poland,
                R.drawable.p2poland,
                "WRPENGOADBLOYERN",
                "POLAND"
            )
        )
    }

    fun setCurrentPosition(currentPos: Int) {
        sharedPref.currentPos = currentPos
    }

    fun getCurrentPosition(): Int {
        return sharedPref.currentPos
    }

    fun addCash() {
        sharedPref.cash += 10
    }

    fun getCash(): Int {
        return sharedPref.cash
    }

    fun cutCash() {
        sharedPref.cash -= 5
    }

    fun setFinishCurrentPosition(position: Int) {
        sharedPref.currentPos = position
    }

    fun setCoin() {
        sharedPref.cash = 50
    }
}