package uz.gita.quizapp.uz.muhammadali.a4picturein1word.ui.play

import uz.gita.quizapp.uz.muhammadali.a4picturein1word.model.QuestionData

class PlayPresenter(private val view:PlayContract.View):PlayContract.Presenter {

    private val model:PlayContract.Model = PlayModel()

    override fun loadNextQuestion() {
        view.describeQuestion(model.loadNextQuestion()!!)
    }

    override fun getCurrentAnswer(): QuestionData {
        return model.getCurrentAnswer()
    }

    override fun setCurrentPosition(currentPos: Int) {
        model.setCurrentPosition(currentPos)
    }

    override fun getCurrentPos(): Int {
        return model.getCurrentPosition()
    }

    override fun addCash() {
        model.addCash()
    }

    override fun showCash() {
        return view.showCash(model.getCash())
    }

    override fun cutCash() {
        model.cutCash()
    }

    override fun getCash(): Int {
        return model.getCash()
    }

    override fun setFinishCurrentPosition(position: Int) {
        model.setFinishCurrentPosition(position)
    }

    override fun setCoin() {
        model.setCoin()
    }

    override fun btnVariantClicked(text: String, index: Int) {
        view.showValueToAnswer(text, index)
    }

    override fun btnAnswerClicked(text: String, index: Int, tagPos: Int) {
        view.showValueToVariant(text, index, tagPos)
    }
}

