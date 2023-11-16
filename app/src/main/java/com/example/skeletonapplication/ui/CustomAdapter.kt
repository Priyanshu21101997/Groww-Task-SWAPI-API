package com.example.skeletonapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.listeners.Onclick
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.ListSingleItemBinding
import com.example.skeletonapplication.models.Result
import java.lang.ref.WeakReference
import java.util.Currency

class CustomAdapter : ListAdapter<Result,CustomAdapter.ViewHolder>(DiffUtil()) {

    private var onClickListener: Onclick? = null
    private lateinit var binding: ListSingleItemBinding

    fun setListener(listener : Onclick?){
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_single_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItem = getItem(position) // Get item method

        holder.listItemBinding.textView.text = listItem.quoteId.toString() + listItem.content.subSequence(0,5)
        holder.listItemBinding.root.setOnClickListener {
            onClickListener?.onCommentClicked(listItem)
        }

    }



    class ViewHolder(val listItemBinding: ListSingleItemBinding) : RecyclerView.ViewHolder(listItemBinding.root) {
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return  oldItem == newItem
        }


    }
}


