package com.thorin.project1.laporantol.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.thorin.project1.laporantol.R

class SetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        val actionbar = supportActionBar
        actionbar?.title= "Setting"
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun sendEmail(view: View) {
        val i = Intent(Intent.ACTION_SEND)
        i.setType("message/rfc822")
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>("thorin.contact@gmail.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, "Laporan/Masukan")
        i.putExtra(Intent.EXTRA_TEXT, "Hallo Thorin Creative !")
        try
        {
            startActivity(Intent.createChooser(i, "Send mail..."))
        }
        catch (ex:android.content.ActivityNotFoundException) {
            Toast.makeText(this@SetActivity, "Tidak Ada Aplikasi Email Terinstal..", Toast.LENGTH_SHORT).show()
        }
    }
}