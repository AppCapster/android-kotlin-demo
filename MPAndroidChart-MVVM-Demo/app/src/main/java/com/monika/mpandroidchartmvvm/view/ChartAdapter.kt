package com.monika.mpandroidchartmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monika.mpandroidchartmvvm.R
import com.monika.mpandroidchartmvvm.model.ChartModel

/**
 * @author Monika
 */
class ChartAdapter(
    private var charts: List<ChartModel>,
    private val listener: (ChartModel) -> Unit
) :
    RecyclerView.Adapter<ChartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ChartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chart, parent, false)
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(vh: ChartViewHolder, position: Int) {
        vh.bind(charts[position], listener)
    }

    override fun getItemCount(): Int {
        return charts.size
    }

    fun update(data: List<ChartModel>) {
        charts = data
        notifyDataSetChanged()
    }
}