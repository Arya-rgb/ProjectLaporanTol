package com.thorin.project1.laporantol.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thorin.project1.laporantol.R

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val actionbar = supportActionBar
        actionbar?.title= "History Laporan"
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}