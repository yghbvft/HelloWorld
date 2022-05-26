package com.example.helloworld

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    private var edit_text: EditText? = null
    private var btn_hello: Button? = null
    private var text_view: TextView? = null
    private var btn_second: Button? = null
    private var btn_third: Button? = null
    private var switch_flashlight: Switch? = null

    private var IsLightEnabled: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_text = findViewById(R.id.editText_source)
        btn_hello = findViewById(R.id.BTN_hello)
        text_view = findViewById(R.id.textView_result)
        btn_second = findViewById(R.id.BTN_second)
        btn_third = findViewById(R.id.BTN_third)
        switch_flashlight = findViewById(R.id.switch_flashlight)


        btn_hello?.setOnClickListener {
            if (edit_text?.text?.toString()?.trim()?.equals("")!!) {
                Toast.makeText(this, "Поле не может быть пустым!", Toast.LENGTH_LONG).show()
            }
            else {
                val text: String = edit_text?.text.toString()
                text_view?.text = text
            }
        }

        btn_second?.setOnClickListener {
            var intent_second = Intent(this, Activity_second::class.java)
            startActivity(intent_second)
        }

        btn_third?.setOnClickListener {
            var intent_third = Intent(this, Activity_third::class.java)
            startActivity(intent_third)
        }

        switch_flashlight?.setOnClickListener {
            openFlashLight()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openFlashLight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!IsLightEnabled) {
            try {
                cameraManager.setTorchMode(cameraId, true)
                IsLightEnabled = true

            } catch (e: CameraAccessException) { }
        } else {
            try {
                cameraManager.setTorchMode(cameraId, false)
                IsLightEnabled = false
            } catch (e: CameraAccessException) { }
        }
    }

}