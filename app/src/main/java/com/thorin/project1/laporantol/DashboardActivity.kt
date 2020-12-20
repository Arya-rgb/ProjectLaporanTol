package com.thorin.project1.laporantol

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.thorin.project1.laporantol.menu.HistoryActivity
import com.thorin.project1.laporantol.menu.SetActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.text.SimpleDateFormat
import java.util.*


class DashboardActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        pref.getString("nik", null)
        pref.getString("nama_user", null)
        user_nik.text = pref.getString("nik", null)
        user_nama.text = pref.getString("nama_user", null)

        val today = Calendar.getInstance().time//getting date
        val formatter = SimpleDateFormat("dd-MM-yyyy")//formating according to my need
        val date = formatter.format(today)
        id_date.text = date

    }
    override fun onBackPressed() {
        this@DashboardActivity.moveTaskToBack(true)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_history -> {
                val moveIntent = Intent(this, HistoryActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.action_setting -> {
                val moveIntent = Intent(this, SetActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.action_logout -> {
                logOutVerification()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun clearAppData() {
        try
        {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT)
            {
                (getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData() // note: it has a return value!
            }
            else
            {
                val packageName = applicationContext.packageName
                val runtime = Runtime.getRuntime()
                runtime.exec("pm clear $packageName")
            }
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
    }

    private fun logOutVerification() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Apakah anda yakin ?")
        builder.setMessage("""
            Anda akan keluar aplikasi setelah logout !
        """.trimIndent())
        builder.setPositiveButton("Ya") { _, _ ->
            clearAppData()
        }
        builder.setNegativeButton("Tidak") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


}