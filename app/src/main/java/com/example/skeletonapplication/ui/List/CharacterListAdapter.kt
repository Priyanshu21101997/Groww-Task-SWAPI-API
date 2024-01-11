package com.example.skeletonapplication.ui.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.listeners.IOnCharacterClicked
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.ListSingleItemBinding
import com.example.skeletonapplication.models.Results
import com.example.skeletonapplication.utils.AppConstants
import com.example.skeletonapplication.utils.Filter

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    private var onClickListener: IOnCharacterClicked? = null
    private lateinit var binding: ListSingleItemBinding
    private val results = ArrayList<Results>()


    fun setResults(items: List<Results>) {
        this.results.addAll(items)
        notifyDataSetChanged()
    }

    fun setListener(listener: IOnCharacterClicked?) {
        onClickListener = listener
    }

    fun displayListBasedOnFilter(filter: Filter) {
        filterList(filter)
    }

    private fun filterList(filter: Filter) {
        var temporaryList = mutableListOf<Results>()
        temporaryList.addAll(results)


        temporaryList.let { it ->
            when (filter) {
                Filter.UPDATION -> {
                    it.sortBy { it1 -> it1.edited }
                }

                Filter.CREATION -> {
                    it.sortBy { it1 -> it1.created }
                }

                Filter.MALES -> {
                    temporaryList.clear()
                    results.forEach {
                        if (it.gender == AppConstants.male) {
                            temporaryList.add(it)
                        }
                    }
                }

                Filter.FEMALES -> {
                    temporaryList.clear()
                    results.forEach {
                        if (it.gender == AppConstants.female) {
                            temporaryList.add(it)
                        }
                    }
                }

                Filter.NAME -> {
                    it.sortBy { it1 -> it1.name }
                }

                else -> {
                }
            }
        }

        results.clear()
        setResults(temporaryList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_single_item, parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        results[position].let { listItem ->
            holder.listItemBinding.apply {
                textView.text = listItem.name
                height.text = AppConstants.HEIGHT + listItem.height
                mass.text = AppConstants.MASS + listItem.mass
                root.setOnClickListener {
                    onClickListener?.onCharacterClicked(listItem)
                }
            }
        }
    }

    class ViewHolder(val listItemBinding: ListSingleItemBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {
    }

}


