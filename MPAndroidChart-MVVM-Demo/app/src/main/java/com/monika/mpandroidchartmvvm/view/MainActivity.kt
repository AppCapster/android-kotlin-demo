package com.monika.mpandroidchartmvvm.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monika.mpandroidchartmvvm.R
import com.monika.mpandroidchartmvvm.di.Injection
import com.monika.mpandroidchartmvvm.model.ChartModel
import com.monika.mpandroidchartmvvm.viewmodel.ChartViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ChartViewModel> {
        Injection.provideViewModelFactory()
    }
    private lateinit var adapter: ChartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        adapter = ChartAdapter(
            viewModel.charts.value ?: emptyList()
        ) { viewModel.onChartClickAction(it) }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    //view model
    private fun setupViewModel() {
        viewModel.charts.observe(this, renderCharts)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderCharts = Observer<List<ChartModel>> {
        Log.v(TAG, "data updated $it")
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
    }

    private val emptyListObserver = Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
    }

    //If you require updated data, you can call the method "loadChart" here
    override fun onResume() {
        super.onResume()
        viewModel.loadCharts()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }
}