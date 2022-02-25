package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChoiceStart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnInputStart.setOnClickListener {
            val intent = Intent(this, TextInputProblem::class.java)
            startActivity(intent)
        }
        binding.btnFlashCard.setOnClickListener {
            val intent = Intent(this, FlashCardActivity::class.java)
            startActivity(intent)
        }
    }
}