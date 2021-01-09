package com.thorin.project1.laporantol

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        val btn_log = findViewById<Button>(R.id.btn_login)
        val sp:SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        val intent = intent


        if(sp.getBoolean("logged", false)){
            val i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
        }

        btn_log.setOnClickListener {
            val nik = id_nik.text.toString()
            val password = id_password.text.toString()
            showLoading(true)
            // Response received from the server
            val responseListener =
                Response.Listener<String> { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        if (success) {
                            btn_log.isClickable = false
                            val nik = jsonResponse.getString("nik")
                            val nama_user = jsonResponse.getString("nama_user")
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            intent.putExtra("nik", nik)
                            intent.putExtra("nama_user", nama_user)
                            val pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString("nik", nik) // Saving string
                            editor.putString("nama_user", nama_user) // Saving string
                            editor.apply() // commit changes}
                            sp.edit().putBoolean("logged",true).apply()
                            this@LoginActivity.startActivity(intent)
                            finish()
                            showLoading(false)
                        } else {
                            val builder = AlertDialog.Builder(this@LoginActivity)
                            builder.setMessage("Nik atau password tidak terdaftar")
                                .setNegativeButton("Ok", null)
                                .create()
                                .show()
                            showLoading(false)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            val loginRequest = LoginRequest(nik, password, responseListener)
            val queue = Volley.newRequestQueue(this@LoginActivity)
            queue.add(loginRequest)
        }

    }
    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

}