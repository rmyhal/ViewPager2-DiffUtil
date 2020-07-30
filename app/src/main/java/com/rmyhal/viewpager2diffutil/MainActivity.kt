package com.rmyhal.viewpager2diffutil

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val pagerAdapter by lazy {
        PagerAdapter(this)
    }

    private val pagerItems by lazy(::generatePagerItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPager.adapter = pagerAdapter
        pagerAdapter.setPagerItems(pagerItems)

        spinner.adapter = createSpinnerAdapter()

        btnIncrement.setOnClickListener {
            val position = spinner.selectedItemPosition
            val oldItem = pagerItems[position]
            pagerItems[position] = oldItem.copy(value = oldItem.value + 1)
            pagerAdapter.setPagerItems(pagerItems)
        }
    }

    override fun onDestroy() {
        viewPager.adapter = null
        super.onDestroy()
    }

    private fun createSpinnerAdapter() = object : BaseAdapter() {

        override fun getCount() = ITEMS_COUNT

        override fun getItem(position: Int): Any {
            return pagerItems[position].title
        }

        override fun getItemId(position: Int): Long {
            return pagerItems[position].id.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            return ((convertView as? TextView) ?: TextView(parent.context)).apply {
                text = pagerItems[position].title
            }
        }
    }

    private fun generatePagerItems() = (1..ITEMS_COUNT).map {
        val color = if (it < colors.size) it else it % colors.size
        PagerItem(it, "Item $it", colors[color], 0)
    }.toTypedArray()

    companion object {

        private const val ITEMS_COUNT = 10
        private val colors = listOf(
            "#e53935",
            "#d81b60",
            "#8e24aa",
            "#2196f3",
            "#df78ef",
            "#d3b8ae"
        )
    }
}