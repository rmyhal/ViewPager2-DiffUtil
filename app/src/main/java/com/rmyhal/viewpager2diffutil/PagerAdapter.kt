package com.rmyhal.viewpager2diffutil

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import kotlin.properties.Delegates

class PagerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private var items by Delegates.observable(emptyList<PagerItem>()) {_, oldList, newList ->
        PagerDiffUtil.withItems(oldList, newList).also { callback ->
            DiffUtil.calculateDiff(callback).apply {
                dispatchUpdatesTo(this@PagerAdapter)
            }
        }
    }

    fun setPagerItems(items: Array<PagerItem>) {
        this.items = items.toList()
    }

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
            (activity.supportFragmentManager.findFragmentByTag(tag) as? PagerFragment)?.run {
                setValue(items[position].value)
            } ?: super.onBindViewHolder(holder, position, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }

    }
}