package com.rmyhal.viewpager2diffutil

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment(R.layout.fragment_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTitle.text = requireArguments().getString(EXTRA_TITLE)
        view.setBackgroundColor(Color.parseColor(requireArguments().getString(EXTRA_COLOR)))
        setValue(requireArguments().getInt(EXTRA_VALUE))
    }

    fun setValue(newValue: Int) {
        txtValue.text = newValue.toString()
    }

    companion object {

        private const val EXTRA_TITLE = "title"
        private const val EXTRA_COLOR = "color"
        private const val EXTRA_VALUE = "value"


        fun newInstance(item: PagerItem): PagerFragment {
            return PagerFragment().apply {
                arguments = Bundle(3).apply {
                    putString(EXTRA_TITLE, item.title)
                    putString(EXTRA_COLOR, item.color)
                    putInt(EXTRA_VALUE, item.value)
                }
            }
        }
    }
}