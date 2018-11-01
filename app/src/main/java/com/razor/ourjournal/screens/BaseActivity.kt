package com.razor.ourjournal.screens

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity : AppCompatActivity() {

    private var back_pressed: Long = 0

    override fun onBackPressed() {
        if (System.currentTimeMillis() < back_pressed + 2000)
            super.onBackPressed()
        else
            Toast.makeText(getBaseContext(), "Press back once more to exit", Toast.LENGTH_SHORT).show()

        back_pressed = System.currentTimeMillis()
    }
}