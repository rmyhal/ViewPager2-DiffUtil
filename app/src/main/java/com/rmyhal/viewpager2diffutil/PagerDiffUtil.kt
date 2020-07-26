package com.rmyhal.viewpager2diffutil

import androidx.recyclerview.widget.DiffUtil

class PagerDiffUtil(private val oldList: List<PagerItem>, private val newList: List<PagerItem>) : DiffUtil.Callback() {

    enum class PayloadKey {
        VALUE
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

        // google's variant
//        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].value == newList[newItemPosition].value

        // google's variant
//        return areItemsTheSame(oldItemPosition, newItemPosition)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return listOf(PayloadKey.VALUE)
    }
}