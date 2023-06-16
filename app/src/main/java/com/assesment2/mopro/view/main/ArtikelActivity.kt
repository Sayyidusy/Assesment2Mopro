package com.assesment2.mopro.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assesment2.mopro.MainViewModel
import com.assesment2.mopro.R
import com.assesment2.mopro.data.AdapterClass
import com.assesment2.mopro.model.DataClass
import com.assesment2.mopro.databinding.ActivityArtikelBinding
import java.util.*
import kotlin.collections.ArrayList

class ArtikelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtikelBinding
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
    private lateinit var descList: Array<String>
    private lateinit var searchList: ArrayList<DataClass>
    private lateinit var viewModel: MainViewModel

    companion object {
        const val NAME = "NAME"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Disini kita membuat variable untuk switch material
        val switchMaterial = binding.switchMaterial

        switchMaterial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.artikel_title)

        // Kumpulan gambar
        imageList = arrayOf(
            R.drawable.ps,
            R.drawable.lr,
            R.drawable.psc,
            R.drawable.pr,
            R.drawable.an,
            R.drawable.ae,
            R.drawable.ai,
            R.drawable.id,
            R.drawable.xd,
            R.drawable.fg
        )
        // Kumpulan title dan desc dari strings.xml
        titleList = resources.getStringArray(R.array.title_array)
        descList = resources.getStringArray(R.array.description_array)


        // Inisialisasi RecyclerView dan mengatur layout manager dan ukuran RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        dataList = arrayListOf()
        // Fungsi untuk mencari data
        searchList = arrayListOf()


        binding.search.clearFocus()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    dataList.forEach {
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            searchList.add(it)
                        }
                    }
                    binding.recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    searchList.clear()
                    searchList.addAll(dataList)
                    binding.recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })

        // Mengatur adapter RecyclerView
        dataList = arrayListOf()
        searchList = arrayListOf()
        getData()
    }
    private fun getData() {
        for (i in imageList.indices) {
            val dataClass = DataClass(imageList[i], titleList[i], descList[i])
            dataList.add(dataClass)
        }
        searchList.addAll(dataList)
        binding.recyclerView.adapter = AdapterClass(searchList)
    }

}