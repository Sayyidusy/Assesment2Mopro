package com.assesment2.mopro

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.assesment2.mopro.databinding.ListItemBinding
import com.assesment2.mopro.model.DataApi
import com.assesment2.mopro.network.AplikasiApi
import com.bumptech.glide.Glide

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val data = mutableListOf<DataApi>()

    fun updateData(newData: List<DataApi>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(aplikasi: DataApi) = with(binding) {
            namaTextView.text = aplikasi.dataTitle
            infoTextView.text = aplikasi.desc
            Glide.with(imageView.context)
                .load(AplikasiApi.getAplikasiUrl(aplikasi.dataImage))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)

            root.setOnClickListener {
                Toast.makeText(root.context, aplikasi.dataTitle, Toast.LENGTH_LONG).show()
            }
        }
    }
}