package com.a203110026.fanianggita_room

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.a203110026.fanianggita_room.databinding.FragmentHomeBinding
import com.a203110026.fanianggita_room.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
// TODO 6: Membuat kelas adapter dengan menerapkan fragment untuk mangatur item pada recyclerview
@AndroidEntryPoint
class FragmentBeranda : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    // Menginisialisasi kelas CatatanAdapter
    private lateinit var catatanAdapter: CatatanAdapter

    // kelas view holder membungkus senuah item pada RecylerView
    private val viewModel by viewModels<ViewModelBeranda>()

    /*
    fungsi atau member variabel di suatu kelas agar bisa dipanggil tanpa melalui sebuah objek, kita dapat melakukannya dengan menulis member atau method tersebut di dalam companion object suatu kelas.
     */
    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentBeranda().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catatanRV()
        kumpulanCatatan()


        // Jika tombol btnTambahCatatan maka akan ganti ke fragment 'FragmentTambahCatatan'
        binding.btnTambahCatatan.setOnClickListener {
            pergantianFragment(FragmentTambahCatatan.newInstance(), true)
        }

        // Tombol untuk query data catatan
        binding.searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.pencarianQueryChanged(p0.toString())
                    return true
                }
            })
    }

    // Proses peergantian fragment ke 'FragmentTambahCatatan'
    private val onClicked = object : CatatanAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int) {
            val fragment: Fragment
            val bundle = Bundle()
            bundle.putInt("noteId", notesId)
            fragment = FragmentTambahCatatan.newInstance()
            fragment.arguments = bundle
            pergantianFragment(fragment, true)
        }
    }

    // Mengambil semua data Catatan
    private fun kumpulanCatatan() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.notes.collectLatest {
            catatanAdapter.submitList(it)
        }
    }

    // Mengembalikan data Catatan ke fragment
    private fun catatanRV() = binding.apply {
        /*
       Pengelola tata letak mengatur elemen individual dalam list.
       Kita dapat menggunakan salah satu pengelola tata letak yang disediakan oleh library RecyclerView, atau menentukannya sendiri.
       Pengelola tata letak semuanya didasarkan pada class abstrak LayoutManager library.
        */
        catatanAdapter = CatatanAdapter().apply { setOnClickListener(onClicked) }
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = catatanAdapter
    }

    // Pergantian fragment ke 'fragmentutama'
    fun pergantianFragment(fragment: Fragment, istransition: Boolean) {

        val transisifragment = requireActivity().supportFragmentManager.beginTransaction()
        transisifragment.replace(R.id.fragmentutama, fragment)
            .addToBackStack(fragment.javaClass.simpleName)
        transisifragment.commit()
    }
}
