package com.e.tugaspraktikum9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.e.tugaspraktikum9.Kontak.Kontak
import com.e.tugaspraktikum9.Kontak.KontakRepository

class KontakViewModel (application: Application) : AndroidViewModel(application)
{
    private var repository: KontakRepository =
        KontakRepository(application)
    private var allKontak: LiveData<List<Kontak>> = repository.getAllKontak()

    fun insert(kontak: Kontak) {
        repository.insert(kontak)
    }

    fun update(kontak: Kontak) {
        repository.update(kontak)
    }

    fun delete(kontak: Kontak) {
        repository.delete(kontak)
    }

    fun deleteAllKontak() {
        repository.deleteAllKontak()
    }

    fun getAllKontak(): LiveData<List<Kontak>> {
        return allKontak
    }
}