package com.geraud.wallpaperapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //navigate to main activity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}
