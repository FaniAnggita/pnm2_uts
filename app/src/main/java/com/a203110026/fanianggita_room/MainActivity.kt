package com.a203110026.fanianggita_room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.a203110026.fanianggita_room.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
// @AndroidEntryPoint menghasilkan komponen Hilt individual untuk setiap class Android.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Membuat objek binding untuk inflate untuk memastikan hierarki tampilan fragment.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pergantianFragment(FragmentBeranda.newInstance(), true)
    }

    // Fungsi untuk mengganti ke fragment 'fragmentutama'
    fun pergantianFragment(fragment: Fragment, istransition: Boolean) {
        // Memulai menjalankan transaksi fragment
        val transisifragment = supportFragmentManager.beginTransaction()
        // mengganti ke fragment 'fragmentutama'
        transisifragment.replace(R.id.fragmentutama, fragment).addToBackStack(fragment.javaClass.simpleName)
        // menetapkan fragment
        transisifragment.commit()
    }

    // untuk menangani peristiwa tombol Kembali
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
