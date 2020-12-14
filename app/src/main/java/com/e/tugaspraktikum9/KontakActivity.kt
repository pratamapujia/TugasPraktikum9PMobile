package com.e.tugaspraktikum9

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_kontak.*

class KontakActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.catatanku.EXTRA_ID"
        const val EXTRA_NAMA = "com.example.catatanku.EXTRA_NAMA"
        const val EXTRA_DESKRIPSI = "com.example.catatanku.EXTRA_DESKRIPSI"
        const val EXTRA_TELEPON = "com.example.catatanku.EXTRA_TELEPON"
        const val EXTRA_PRIORITAS = "com.example.catatanku.EXTRA_PRIORITAS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_kontak)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 5

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Kontak"
            edit_text_title.setText(intent.getStringExtra(EXTRA_NAMA))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESKRIPSI))
            edit_text_phone.setText(intent.getStringExtra(EXTRA_TELEPON))
            number_picker_priority.value = intent.getIntExtra(EXTRA_PRIORITAS, 1)
        } else {
            title = "Tambah Kontak"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_kontak_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_kontak -> {
                saveKontak()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveKontak() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank() || edit_text_phone.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Kontak kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAMA, edit_text_title.text.toString())
            putExtra(EXTRA_DESKRIPSI, edit_text_description.text.toString())
            putExtra(EXTRA_TELEPON, edit_text_phone.text.toString())
            putExtra(EXTRA_PRIORITAS, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}