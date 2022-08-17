package com.android.androidstudiorecyclerviewcheckbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.androidstudiorecyclerviewcheckbox.databinding.ItemBinding
import java.util.*
import kotlin.collections.ArrayList


class RecyclerViewAdapter(var list: ArrayList<CheckboxData>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

        return MyViewHolder(ItemBinding.bind(view))

    }
    fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(list, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        var objIncome = list[position];
        viewHolder.binding.checkBox.setOnCheckedChangeListener(null);
        viewHolder.binding.checkBox.isChecked = objIncome.checked
        viewHolder.binding.checkBox.setOnClickListener {
            objIncome.checked= viewHolder.binding.checkBox.isChecked;
        }


    }

    override fun getItemCount()= list.size



}