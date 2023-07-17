package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play

import uz.gita.quizapp.uz.muhammadali.a4picturein1word.data.Settings
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData
import uz.gita.quizapp.uz.muhammadali.a4picturein1word.repository.AppRepository

class PlayModel:PlayContract.Model{
    private val repository = AppRepository.getInstance()
    private val list = ArrayList<QuestionData>()
    private var currentPos = repository.getCurrentPosition()

    init {
        list.addAll(repository.getList())
    }
    override fun loadNextQuestion(): QuestionData {
        repository.setCurrentPosition(currentPos)
            return list[currentPos++]
    }

    override fun getQuestionByIndex(index: Int): QuestionData =
        list[index]

    override fun getCurrentAnswer(): QuestionData =
        list[currentPos - 1]

    override fun getCurrentPosition(): Int {
        return repository.getCurrentPosition()
    }

    override fun setCurrentPosition(currentPos: Int) {
        repository.setCurrentPosition(currentPos)
    }

    override fun addCash() {
        repository.addCash()
    }

    override fun getCash(): Int {
        return repository.getCash()
    }

    override fun cutCash() {
        repository.cutCash()
    }

    override fun setFinishCurrentPosition(position: Int) {
        list.clear()
        repository.setFinishCurrentPosition(position)
    }

    override fun setCoin() {
        repository.setCoin()
    }


}