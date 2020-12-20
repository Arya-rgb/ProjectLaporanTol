package com.thorin.project1.laporantol.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}