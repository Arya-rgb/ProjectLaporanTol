package com.thorin.project1.laporantol

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.thorin.project1.laporantol.menu.HistoryActivity
import com.thorin.project1.laporantol.menu.SetActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        var pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        pref.getString("nik", null)
        pref.getString("nama_user", null)
        user_nik.text = pref.getString("nik", null)
        user_nama.text = pref.getString("nama_user", null)

        var today = Calendar.getInstance().time//getting date
        var formatter = SimpleDateFormat("dd-MM-yyyy")//formating according to my need
        var date = formatter.format(today)
        id_date.text = date


        val bClickMe = findViewById<Button>(R.id.exportpdf)
        bClickMe.setOnClickListener { pref.getString("nama_user", null)?.let { createPdf(it) }
            openFileOption()
        }

    }

    override fun onBackPressed() {
        this@DashboardActivity.moveTaskToBack(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                (getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData() // note: it has a return value!
            } else {
                val packageName = applicationContext.packageName
                val runtime = Runtime.getRuntime()
                runtime.exec("pm clear $packageName")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logOutVerification() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Apakah anda yakin ?")
        builder.setMessage(
            """
            Anda akan keluar aplikasi setelah logout !
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { _, _ ->
            clearAppData()
        }
        builder.setNegativeButton("Tidak") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun createPdf(namaUser: String) {
        val today = Calendar.getInstance().time//getting date
        val formatter = SimpleDateFormat("dd-MM-yyyy")//formating according to my need
        val dateLaporan = formatter.format(today)
        // create a new document
        val document = PdfDocument()
        // crate a page description
        var pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        // start a page
        var page = document.startPage(pageInfo)
        var canvas = page.getCanvas()
        var paint = Paint()
        canvas.drawText(namaUser, 80F, 50F, paint)
        canvas.drawText("""
            Nik User = ${user_nik.text as String} 
            
            Tanggal Sekarang = $dateLaporan
        """.trimIndent(), 80F, 70F, paint)
        //canvas.drawt
        // finish the page
        document.finishPage(page)
        // draw text on the graphics object of the page

        // write the document content
        val directory_path = Environment.getExternalStorageDirectory().path + "/laporanpdf/"
        val file = File(directory_path)
        if (!file.exists()) {
            file.mkdirs()
        }
        val targetPdf = directory_path + "Laporan-$dateLaporan.pdf"
        val filePath = File(targetPdf)
        try {
            document.writeTo(FileOutputStream(filePath))
            Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("main", "error $e")
            Toast.makeText(this, "Something wrong: Izinkan aplikasi untuk akses storage di pengaturan", Toast.LENGTH_LONG).show()
        }
        // close the document
        document.close()
    }

    fun openFolder() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val uri = Uri.parse(
            Environment.getExternalStorageDirectory().path + "/laporanpdf/"
        )
        intent.setDataAndType(uri, "resource/folder")
        startActivity(Intent.createChooser(intent, "Open folder"))
    }

    private fun openFileOption() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Buka Foldernya ?")
        builder.setMessage(
            """
            Pdf anda tersimpan di internal storage dalam folder laporanpdf
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { _, _ ->
            openFolder()
        }
        builder.setNegativeButton("Tidak") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}
