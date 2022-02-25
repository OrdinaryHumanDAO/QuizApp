package com.example.quizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import com.example.quizapp.databinding.ActivityTextInputProblemBinding

class TextInputProblem : AppCompatActivity() {

    private lateinit var binding: ActivityTextInputProblemBinding
    private var rightAnswer : String? = null
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
                showNextQuizs()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextInputProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showNextQuizs()

        binding.btnBack.setOnClickListener {
            timer.cancel()
            finish()
        }
    }

    // クイズを出題する
    private fun showNextQuizs() {
        timer.start()
        // カウントラベルの更新
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        // クイズを１問取り出す
        val quiz = quizProblemShuffle[quizCount - 1]

        // 問題をセット
        binding.problemText.text = quiz[0]


        // 正解をセット
        rightAnswer = quiz[1].toString()


    }

    // 解答ボタンが押されたら呼ばれる
    fun checkInputTextAnswer(view: View) {
        timer.cancel()
        // どの解答ボタンが押されたか
        val answerEdit: EditText = findViewById(R.id.et)
        val answerText = answerEdit.text.toString()

        // ダイアログのタイトルを作成
        val alertTitle: String
        if (answerText == rightAnswer) {
            alertTitle = "正解!"
            rightAnswerCount++
        } else {
            alertTitle = "不正解..."
        }

        // ダイアログを作成
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(alertTitle)
        builder.setMessage("答え : $rightAnswer")
        builder.setPositiveButton("OK") { _, _ ->
            checkQuizCount()
            answerEdit.text.clear()
        }
        builder.setCancelable(false)
        builder.show()
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
            showNextQuizs()
        }
    }
}