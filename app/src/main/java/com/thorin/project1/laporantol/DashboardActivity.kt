package com.thorin.project1.laporantol

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATION")
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

        val htbriClick = findViewById<Button>(R.id.btn_htBRI)
        val htbcaClick = findViewById<Button>(R.id.btn_htBCA)
        val htmandiriClick = findViewById<Button>(R.id.btn_htMANDIRI)

        htbriClick.setOnClickListener {
            val briTotal = BRITotal.text.toString().trim()
            val briTrans = BRITrans.text.toString().trim()
            var isEmptyFields = false
            when {
                briTotal.isEmpty() -> {
                    isEmptyFields = true
                    BRITotal.error = "Field ini tidak boleh kosong"
                }
                briTrans.isEmpty() -> {
                    isEmptyFields = true
                    BRITrans.error = "Field ini tidak boleh kosong"
                }
            }
            if (!isEmptyFields) {
                val briTotalht = Integer.parseInt(BRITotal.text.toString())
                val briTransht = Integer.parseInt(BRITrans.text.toString())
                val resultBri = briTransht * 1500 + briTotalht
                resultBri.toDouble()
                val COUNTRY = "ID"
                 val LANGUAGE = "in"
                 txt_hslBRI.text = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                     .format(resultBri)
                txt_httotalBRI.text = resultBri.toString()

            }
        }

        htbcaClick.setOnClickListener {
            val bcaTotal = BCATotal.text.toString().trim()
            val bcaTrans = BCATrans.text.toString().trim()
            var isEmptyFields = false
            when {
                bcaTotal.isEmpty() -> {
                    isEmptyFields = true
                    BRITotal.error = "Field ini tidak boleh kosong"
                }
                bcaTrans.isEmpty() -> {
                    isEmptyFields = true
                    BRITrans.error = "Field ini tidak boleh kosong"
                }
            }
            if (!isEmptyFields) {
                val bcaTotalht = Integer.parseInt(BCATotal.text.toString())
                val bcaTransht = Integer.parseInt(BCATrans.text.toString())
                val resultBca = bcaTransht * 1500 + bcaTotalht
                resultBca.toDouble()
                val COUNTRY = "ID"
                val LANGUAGE = "in"
                txt_hslBCA.text = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                    .format(resultBca)
                txt_httotalBCA.text = resultBca.toString()
            }
        }
        htmandiriClick.setOnClickListener {
            val mandiriTotal = MANDIRITotal.text.toString().trim()
            val mandiriTrans = MANDIRITrans.text.toString().trim()
            var isEmptyFields = false
            when {
                mandiriTotal.isEmpty() -> {
                    isEmptyFields = true
                    MANDIRITotal.error = "Field ini tidak boleh kosong"
                }
                mandiriTrans.isEmpty() -> {
                    isEmptyFields = true
                    MANDIRITrans.error = "Field ini tidak boleh kosong"
                }
            }
            if (!isEmptyFields) {
                val mandiriTotalht = Integer.parseInt(MANDIRITotal.text.toString())
                val mandiriTransht = Integer.parseInt(MANDIRITrans.text.toString())
                val resultMandiri = mandiriTransht * 1500 + mandiriTotalht
                resultMandiri.toDouble()
                val COUNTRY = "ID"
                val LANGUAGE = "in"
                txt_hslMANDIRI.text = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                   .format(resultMandiri)
                txt_httotalMANDIRI.text =  resultMandiri.toString()
            }
        }
        btn_hitung.setOnClickListener {
            val briTotals = txt_httotalBRI.text.toString().trim()
            val bcaTotals = txt_httotalBCA.text.toString().trim()
            val mandiriTotals = txt_httotalMANDIRI.text.toString().trim()
            var isEmptyFields = false
            when {
                briTotals.isEmpty() -> {
                    isEmptyFields = true
                    Toast.makeText(applicationContext, "Total BRI Belum Di Hitung !", Toast.LENGTH_LONG).show()
                }
                bcaTotals.isEmpty() -> {
                    isEmptyFields = true
                        Toast.makeText(applicationContext, "Total BCA Belum Di Hitung !", Toast.LENGTH_LONG).show()
                }
                mandiriTotals.isEmpty() -> {
                    isEmptyFields = true
                    Toast.makeText(applicationContext, "Total Mandiri Belum Di Hitung !", Toast.LENGTH_LONG).show()
                }
            }
            if (!isEmptyFields) {
                val totalBriht = Integer.parseInt(txt_httotalBRI.text.toString())
                val totalBcaht = Integer.parseInt(txt_httotalBCA.text.toString())
                val totalMandiriht = Integer.parseInt(txt_httotalMANDIRI.text.toString())
                val resultTotal = totalBriht + totalBcaht + totalMandiriht
                resultTotal.toDouble()
                val COUNTRY = "ID"
                val LANGUAGE = "in"
                txt_hasilTotal.text = NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(resultTotal)
            }
        }

        val bClickMe = findViewById<Button>(R.id.exportpdf)
        bClickMe.setOnClickListener {
            pref.getString("nama_user", null)?.let { createPdf(it) }

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
        val COUNTRY = "ID"
        val LANGUAGE = "in"
        val today = Calendar.getInstance().time//getting date
        val formatter = SimpleDateFormat("dd-MM-yyyy")//formating according to my need
        val dateLaporan = formatter.format(today)
        // create a new document
        val document = PdfDocument()
        // crate a page description
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        // start a page
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        canvas.drawText("Tanggal = $dateLaporan", 80F, 50F, paint)
        canvas.drawText(
            """
            Nik User = ${user_nik.text as String} 
        """.trimIndent(), 80F, 70F, paint
        )
        canvas.drawText("Nama User = $namaUser", 80F, 90F, paint)
        canvas.drawText("Shift Laporan = ${shifspnr.selectedItem}", 80F, 110F, paint)
        //bank BRI
        val briTotal = BRITotal.text.toString().toDouble()
        canvas.drawText("Bank BRI", 80F, 140F, paint)
        canvas.drawText("Jumlah Total = ${NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(briTotal)}", 80F, 160F, paint)
        canvas.drawText("Jumlah Transaksi = ${BRITrans.text}", 80F, 180F, paint)
        canvas.drawText("Hasil = ${txt_hslBRI.text as String}", 80F, 200F, paint)
        //bank BCA
        val bcaTotal = BCATotal.text.toString().toDouble()
        canvas.drawText("Bank BCA", 80F, 230F, paint)
        canvas.drawText("Jumlah Total = ${NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(bcaTotal)}", 80F, 250F, paint)
        canvas.drawText("Jumlah Transaksi = ${BCATrans.text}", 80F, 270F, paint)
        canvas.drawText("Hasil = ${txt_hslBCA.text as String}", 80F, 290F, paint)
        //Bank Mandiri
        val mandiriTotal = MANDIRITotal.text.toString().toDouble()
        canvas.drawText("Bank Mandiri", 80F, 320F, paint)
        canvas.drawText("Jumlah Total = ${NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(mandiriTotal)}", 80F, 340F, paint)
        canvas.drawText("Jumlah Transaksi = ${MANDIRITrans.text}", 80F, 360F, paint)
        canvas.drawText("Hasil = ${txt_hslMANDIRI.text as String}", 80F, 380F, paint)
        //Total
        canvas.drawText("Total Semua Bank = ${txt_hasilTotal.text as String}", 80F, 410F, paint)
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
            openFileOption()
            //Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("main", "error $e")
            openSettingOption()
            // Toast.makeText(this, "Something wrong: Izinkan aplikasi untuk akses storage di pengaturan", Toast.LENGTH_LONG).show()
        }
        // close the document
        document.close()
    }


    private fun openSetting() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openFileOption() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sukses")
        builder.setMessage(
            """
            Pdf anda tersimpan di internal storage dalam folder laporanpdf
        """.trimIndent()
        )
        builder.setNegativeButton("OK") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun openSettingOption() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Belum di izinkan.")
        builder.setMessage(
            """
            Izinkan Aplikasi Untuk Mengirim Data Ke Storage.
        """.trimIndent()
        )
        builder.setPositiveButton("Ya") { _, _ ->
            openSetting()
        }
        builder.setNegativeButton("Tidak") { dialog, _ -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


}


