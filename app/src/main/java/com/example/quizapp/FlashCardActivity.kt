package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.quizapp.databinding.ActivityFlashCardBinding

class FlashCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlashCardBinding

    private var rightAnswerCount = 0
    private var quizCount = 1
    private val QUIZ_COUNT = 10

    private val FruitEnglishQuizProblem = listOf(
        listOf("apple", "りんご"),
        listOf("strawberry", "いちご"),
        listOf("orange", "オレンジ"),
        listOf("kiwi", "キウィ"),
        listOf("grapefruit", "グレープフルーツ"),
        listOf("cherry", "サクランボ"),
        listOf("pineapple", "パイナップル"),
        listOf("banana", "バナナ"),
        listOf("lemon", "レモン"),
        listOf("muskmelon", "マスクメロン"),
        )

    private var quizProblemShuffle = FruitEnglishQuizProblem.shuffled()

    private val timer = object : CountDownTimer(10000,100) {
        //途中経過・残り時間
        override fun onTick(p0: Long) {
            //残り時間を表示
            binding.progressbar.max = 10000
            binding.progressbar.progress = p0.toInt()
        }
        //終了設定
        override fun onFinish() {
            if(quizCount == QUIZ_COUNT) {
                checkQuizCount()
            } else {
                quizCount++
                showNextQuiz()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNextQuiz()

        binding.btnBack.setOnClickListener {
            timer.cancel()
            finish()
        }
    }
    fun touch(view: View) {
        binding.textView2.text = quizProblemShuffle[quizCount - 1][1].toString()
    }
    // クイズを出題する
    private fun showNextQuiz() {
        timer.start()
        // カウントラベルの更新
        binding.countLabel.text = getString(R.string.count_label, quizCount)
        binding.textView2.text = ""

        // クイズを１問取り出す
        val quiz = quizProblemShuffle[quizCount - 1]

        // 問題をセット
        binding.problemText.text = quiz[0]
    }

    // 解答ボタンが押されたら呼ばれる
    fun checkAnswer(view : View) {
        timer.cancel()

        // どの解答ボタンが押されたか
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        if (btnText == "知ってる") {
            rightAnswerCount++
        }
        checkQuizCount()
    }

    // 出題数をチェックする
    private fun checkQuizCount() {
        if (quizCount == QUIZ_COUNT) {
            // 結果画面を表示
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 300)
        } else {
            quizCount++
            showNextQuiz()
        }
    }
}
