package com.example.skeletonapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.skeletonapplication.listeners.Onclick
import com.example.skeletonapplication.R
import com.example.skeletonapplication.databinding.ListSingleItemBinding
import com.example.skeletonapplication.models.Result
import java.util.Currency

class CustomAdapter(private var mList: ArrayList<Result>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var onClickListener: Onclick
    private lateinit var binding: ListSingleItemBinding




    fun setListener(listener : Onclick){
        onClickListener = listener
    }

    fun updateData(list: List<Result>){
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_single_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listItem = mList[position]


        holder.listItemBinding.textView.text = listItem.quoteId.toString() + listItem.content.subSequence(0,5)
        holder.listItemBinding.root.setOnClickListener {
            onClickListener.onCommentClicked(mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val listItemBinding: ListSingleItemBinding) : RecyclerView.ViewHolder(listItemBinding.root) {

    }
}


