package com.example.skeletonapplication.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.ListSingleItemDetailBinding
import com.example.skeletonapplication.models.Films

class CharacterDetailAdapter : RecyclerView.Adapter<CharacterDetailAdapter.ViewHolder>() {

    private lateinit var binding: ListSingleItemDetailBinding
    private val results = ArrayList<Films>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_single_item_detail, parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        results[position].let{
            holder.listItemBinding.apply {
                filmName.text = it.title
                details.text = it.openingCrawl
            }
        }
    }

    class ViewHolder(val listItemBinding: ListSingleItemDetailBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {
    }

    fun setResults(items: List<Films>) {
        this.results.clear()
        this.results.addAll(items)
        notifyDataSetChanged()
    }
}


