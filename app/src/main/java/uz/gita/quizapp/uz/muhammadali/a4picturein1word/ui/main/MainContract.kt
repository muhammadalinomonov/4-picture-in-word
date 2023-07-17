package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.main

import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData

interface MainContract {
    interface Model{
        fun getCurrentAnswer(currentPosition:Int) :QuestionData
        fun getCurrentPosition():Int
    }
    interface View{
        fun describeQuestion()
    }
    interface Presenter{
        fun loadQuestion()
        fun currentAnswer(currentPosition: Int):QuestionData
        fun getCurrentPosition():Int
    }
}