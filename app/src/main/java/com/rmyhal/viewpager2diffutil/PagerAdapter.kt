package com.rmyhal.viewpager2diffutil

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class PagerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val items: ArrayList<PagerItem> = arrayListOf()

    override fun createFragment(position: Int): Fragment =
        PagerFragment.newInstance(items[position])

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return items.any { it.id.toLong() == itemId }
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val tag = "f" + holder.itemId
            val fragment = activity.supportFragmentManager.findFragmentByTag(tag)
            // safe check ,but fragment should not be null
            if (fragment != null) {
                (fragment as PagerFragment).setValue(items[position].value)
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setItems(newItems: List<PagerItem>) {
        val callback = PagerDiffUtil(items, newItems)
        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)

        items.clear()
        items.addAll(newItems)
    }
}