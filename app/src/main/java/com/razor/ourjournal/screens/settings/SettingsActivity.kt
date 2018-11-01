package com.razor.ourjournal.screens.settings

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.razor.ourjournal.R
import com.razor.ourjournal.repository.SharedPreferencesRepository
import com.razor.ourjournal.screens.BaseDrawerActivity
import com.razor.ourjournal.screens.login.view.LoginActivity

class SettingsActivity : BaseDrawerActivity() {

    private lateinit var logoutButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContent(R.layout.activity_settings)

        logoutButton = findViewById(R.id.log_out_button)
        logoutButton.setOnClickListener { showLogOutDialog() }
    }

    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.log_out)
        builder.setMessage(R.string.log_out_message)
        builder.setPositiveButton(R.string.log_out) { _: DialogInterface, _: Int -> logOut()}
        builder.setNegativeButton(R.string.cancel) { _, _ -> }
        builder.show()
    }

    private fun logOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener( { navigateToLogin() } )
    }

    private fun navigateToLogin() {
        SharedPreferencesRepository(this).clearPreferences()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
