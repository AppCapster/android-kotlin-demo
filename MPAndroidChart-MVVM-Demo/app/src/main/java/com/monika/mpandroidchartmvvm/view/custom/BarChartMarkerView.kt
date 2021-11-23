package com.monika.mpandroidchartmvvm.view.custom

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.monika.mpandroidchartmvvm.R

class BarChartMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    private var tvCall: TextView? = null
    private var tvPut: TextView? = null

    init {
        tvCall = findViewById(R.id.tvCall)
        tvPut = findViewById(R.id.tvPut)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e is CandleEntry) {
            tvCall?.text = Utils.formatNumber(e.y, 0, true)
            tvPut?.text = Utils.formatNumber(e.high, 0, true)
        } else {
            e?.let {
                tvPut?.text = Utils.formatNumber(it.y, 0, true)
                tvCall?.text = Utils.formatNumber(it.y, 0, true)
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(((width / 2)).toFloat(), (-height).toFloat())
    }
}