package com.a203110026.fanianggita_room

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.a203110026.fanianggita_room.databinding.FragmentCreateNoteBinding
import com.a203110026.fanianggita_room.data.CatatanEntitas
import com.a203110026.fanianggita_room.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.*

// TODO 7: Membuat kelas adapter dengan menerapkan fragment untuk mangatur tambah Catatan
// @AndroidEntryPoint menghasilkan komponen Hilt individual untuk setiap class Android.
@AndroidEntryPoint
class FragmentTambahCatatan :

    // Melakukan bidung ke fragment 'fragment_create_note'
    Fragment(R.layout.fragment_create_note){
    private val binding by viewBinding(FragmentCreateNoteBinding::bind)
    private val viewModel by viewModels<ViewModelCatatanBaru>()

    // Menginisiasliasi tanggal
    private var currentTime: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().getInt("noteId", -1).also {
            if(it != -1) viewModel.setNoteId(it)
        }
    }

    /*
  fungsi atau member variabel di suatu kelas agar bisa dipanggil tanpa melalui sebuah objek,
  kita dapat melakukannya dengan menulis member atau method tersebut di dalam companion object suatu kelas.
   */
    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentTambahCatatan().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        kumpulanCatatan()
    }

    // Untuk mengatur viewLifeCycleOwneryang mewakili lifecycleTampilan Fragmen.
    private fun kumpulanCatatan() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.note.collectLatest {
            it?.let(this@FragmentTambahCatatan::setCatatanUI)
        }
    }

    // set data pada form 'editJudulCatatan' dan 'editDesCatatan'
    private fun setCatatanUI(note: CatatanEntitas) = binding.apply {
        editJudulCatatan.setText(note.judulcatatan)
        editDesCatatan.setText(note.desCatatan)

    }

    private fun initViews() = binding.apply {
        // Inisiasilisasi tanggal
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
        currentTime = sdf.format(Date())

        // Jika tombol 'tombolSave' ditekan makan akan menyimpan / update catatan
        tombolSave.setOnClickListener {
            viewModel.note.value?.let { updateNote(it) } ?: saveNote()
        }

    }

    // Fungsi dalam proses memperbarui catatan
    private fun updateNote(note: CatatanEntitas) = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
       // Inisiasiasi data sebelum diupdate
        note.apply {
            judulcatatan = binding.editJudulCatatan.text.toString()
            desCatatan = binding.editDesCatatan.text.toString()
            dateTime = currentTime


        }.also {
            viewModel.updateNote(it)
        }
        binding.editJudulCatatan.setText("")
        binding.editDesCatatan.setText("")

        requireActivity().supportFragmentManager.popBackStack()
    }


    // fungsi untuk menyimpan catatan baru
    private fun saveNote() {
        // Membaca form 'editJudulCatatan' dan 'editDesCatatan'
        val editJudulCatatan = view?.findViewById<EditText>(R.id.editJudulCatatan)
        val editDesCatatan = view?.findViewById<EditText>(R.id.editDesCatatan)

        when {
            // Jika 'editJudulCatatan' tidak diiisi
            editJudulCatatan?.text.isNullOrEmpty() -> {
                Snackbar.make(requireView(), "Mohon isi judul!", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbarok)) { null }
                    .show()
            }
            // Jika 'editDesCatatan' tidak diiisi
            editDesCatatan?.text.isNullOrEmpty() -> {
                Snackbar.make(
                    requireView(),
                    "Catatan tidak boleh kosong!",
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.snackbarok)) { null }
                    .show()
            }

            // Jika semua data diisi, maka akan dilakukan proses simpan
            else -> {
                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    CatatanEntitas().apply {
                        // Melakukan konversi ke string
                        judulcatatan = editJudulCatatan?.text.toString()
                        desCatatan = editDesCatatan?.text.toString()
                        // Mengambil tanggal saat ini
                        dateTime = currentTime

                    }.also {
                        // Menyimpan data ke database
                        viewModel.saveNote(it)
                    }
                    editJudulCatatan?.setText("")
                    editDesCatatan?.setText("")

                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }


}
