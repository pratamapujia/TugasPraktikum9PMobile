package com.e.tugaspraktikum9

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.tugaspraktikum9.Kontak.Kontak
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_KONTAK_REQUEST = 1
        const val EDIT_KONTAK_REQUEST = 2
    }

    private lateinit var kontakViewModel: KontakViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, KontakActivity::class.java),
                ADD_KONTAK_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = KontakAdapter()
        recycler_view.adapter = adapter

        kontakViewModel = ViewModelProviders.of(this).get(KontakViewModel::class.java)
        kontakViewModel.getAllKontak().observe(this, Observer<List<Kontak>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                kontakViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Kontak dihapus!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : KontakAdapter.OnItemClickListener {
            override fun onItemClick(kontak: Kontak) {
                val intent = Intent(baseContext, KontakActivity::class.java)
                intent.putExtra(KontakActivity.EXTRA_ID, kontak.id)
                intent.putExtra(KontakActivity.EXTRA_NAMA, kontak.Nama)
                intent.putExtra(KontakActivity.EXTRA_DESKRIPSI, kontak.Description)
                intent.putExtra(KontakActivity.EXTRA_TELEPON, kontak.Phone)
                intent.putExtra(KontakActivity.EXTRA_PRIORITAS, kontak.Priority)

                startActivityForResult(intent, EDIT_KONTAK_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_allKontak -> {
                kontakViewModel.deleteAllKontak()
                Toast.makeText(this, "Semua Kontak sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_KONTAK_REQUEST && resultCode == Activity.RESULT_OK) {
            val newKontak = Kontak(
                data!!.getStringExtra(KontakActivity.EXTRA_NAMA),
                data.getStringExtra(KontakActivity.EXTRA_DESKRIPSI),
                data.getStringExtra(KontakActivity.EXTRA_TELEPON),
                data.getIntExtra(KontakActivity.EXTRA_PRIORITAS, 1)
            )
            kontakViewModel.insert(newKontak)
            Toast.makeText(this, "Kontak berhasil disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_KONTAK_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(KontakActivity.EXTRA_ID, 1)

            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val updateKontak = Kontak(
                data!!.getStringExtra(KontakActivity.EXTRA_NAMA),
                data.getStringExtra(KontakActivity.EXTRA_DESKRIPSI),
                data.getStringExtra(KontakActivity.EXTRA_TELEPON),
                data.getIntExtra(KontakActivity.EXTRA_PRIORITAS, 1)
            )
            updateKontak.id = data.getIntExtra(KontakActivity.EXTRA_ID, -1)
            kontakViewModel.update(updateKontak)
        } else {
            Toast.makeText(this, "Kontak tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}