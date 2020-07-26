package com.rmyhal.viewpager2diffutil

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var pagerAdapter: PagerAdapter
    private var pagerItems: MutableList<PagerItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        pagerItems = generatePagerItems()
        pagerAdapter.setItems(pagerItems)

        spinner.adapter = createSpinnerAdapter()

        btnIncrement.setOnClickListener {
            val oldItem = pagerItems[spinner.selectedItemPosition]
            val newItem = oldItem.copy(value = oldItem.value + 1)
            pagerItems[spinner.selectedItemPosition] = newItem
            pagerAdapter.setItems(pagerItems)
        }
    }

    override fun onDestroy() {
        viewPager.adapter = null
        super.onDestroy()
    }

    private fun createSpinnerAdapter(): BaseAdapter {
        return object : BaseAdapter() {
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
    }

    private fun generatePagerItems(): MutableList<PagerItem> {
        return (1..ITEMS_COUNT).map {
            val color = if (it < colors.size) it else it % colors.size
            PagerItem(it, "Item $it", colors[color], 0)
        }.toMutableList()
    }

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