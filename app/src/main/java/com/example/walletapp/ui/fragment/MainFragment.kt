package com.example.walletapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletapp.R
import com.example.walletapp.ui.viewmodel.MainFragmentViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.blank_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        // TODO: Use the ViewModel

        setBarChartValues()
        Log.e("ACA", "DENTRO DE MAINFragment")
    }

    fun setBarChartValues() {

        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 4f))
        entries.add(BarEntry(2f, 10f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 19f))
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))

        val barDataSet = BarDataSet(entries, "AMOUNT")
        barDataSet.setColors(resources.getColor(R.color.blue))

        val data = BarData(barDataSet)

        val barChart = activity?.findViewById<BarChart>(R.id.barChart)
        if (barChart != null) {
            barChart.data = data

            //hide grid lines
            barChart.axisLeft.setDrawGridLines(false)
            barChart.xAxis.setDrawGridLines(false)
            barChart.xAxis.setDrawAxisLine(false)

            //remove right y-axis
            barChart.axisRight.isEnabled = false

            //remove legend
            barChart.legend.isEnabled = true


            //remove description label
            barChart.description.isEnabled = false


            //add animation
            barChart.animateY(3000)


            //draw chart
            barChart.invalidate()
        }
    }


}