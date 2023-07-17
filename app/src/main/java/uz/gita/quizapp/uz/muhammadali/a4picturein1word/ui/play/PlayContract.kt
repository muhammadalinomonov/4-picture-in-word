package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play

import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData

interface PlayContract {
    companion object{
        val MAX_ANSWER_COUNT = 8
        val MAX_VARINANT_COUNT = 16
    }
    interface Model{
        fun loadNextQuestion():QuestionData?
        fun getQuestionByIndex(index:Int):QuestionData
        fun getCurrentAnswer():QuestionData
        fun getCurrentPosition():Int
        fun setCurrentPosition(currentPos:Int)
        fun addCash()
        fun getCash():Int
        fun cutCash()
        fun setFinishCurrentPosition(position:Int)
        fun setCoin()
    }
    interface View{
        fun describeQuestion(questionData: QuestionData)
        fun getFirstEmptyPosition():Int
        fun showCash(cash:Int)
        fun showValueToAnswer(text:String, position: Int)
        fun showValueToVariant(text:String, index:Int, tagPos:Int)
    }
    interface Presenter{
        fun loadNextQuestion()
        fun getCurrentAnswer():QuestionData
        fun setCurrentPosition(currentPos: Int)
        fun getCurrentPos():Int
        fun addCash()
        fun showCash()
        fun getCash():Int
        fun cutCash()
        fun setFinishCurrentPosition(position: Int)
        fun setCoin()
        fun btnVariantClicked(text:String, index:Int)
        fun btnAnswerClicked(text: String, index:Int, tagPos: Int)
    }
}