package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Activity_third : AppCompatActivity() {

    private var btn_return: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        btn_return = findViewById(R.id.BTN_return_3)

        btn_return?.setOnClickListener {
            finish()
        }
    }
}