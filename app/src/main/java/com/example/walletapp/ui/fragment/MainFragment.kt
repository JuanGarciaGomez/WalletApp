package com.example.walletapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletapp.R
import com.example.walletapp.adapter.ExpensesAdapter
import com.example.walletapp.data.model.Expenses
import com.example.walletapp.databinding.MainFragmentBinding
import com.example.walletapp.ui.viewmodel.MainFragmentViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private var i = 0
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var binding: MainFragmentBinding


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewModelPrueba = mainFragmentViewModel
        binding.lifecycleOwner = this
        Log.e(null, "main fragment")

        initRecycler()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        // TODO: Use the ViewModel
        //setBarChartValues()
    }

    override fun onResume() {
        super.onResume()
        while (i < 1) {
            initRecycler()
            //setBarChartValues()
            i++
        }

    }

    fun setBarChartValues() {

        val entries: ArrayList<BarEntry> = ArrayList()
        val entries2: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(5f, 13f))
        entries.add(BarEntry(6f, 2f))
        entries2.add(BarEntry(10f, 13f))
        entries2.add(BarEntry(9f, 2f))

        val barDataSet = BarDataSet(entries, "AMOUNT")
        val barDataSet2 = BarDataSet(entries2, "AMOUNT2")
        barDataSet.setColors(resources.getColor(R.color.blue))
        barDataSet.barShadowColor = resources.getColor(R.color.white)
        barDataSet2.setColors(resources.getColor(R.color.biometric_error_color))
        barDataSet2.barShadowColor = resources.getColor(R.color.teal_700)

        val data = BarData(barDataSet, barDataSet2)

        val barChart = activity?.findViewById<BarChart>(R.id.barChart)
        if (barChart != null) {
            barChart.data = data

            //Colors and configurations
            barChart.setTouchEnabled(false)
            barChart.axisLeft.textColor = Color.WHITE
            barChart.xAxis.textColor = Color.WHITE

            //hide grid lines
            barChart.axisLeft.setDrawGridLines(false)
            barChart.xAxis.setDrawGridLines(false)
            barChart.xAxis.setDrawAxisLine(false)

            //remove right y-axis
            barChart.axisRight.isEnabled = false

            //remove legend
            barChart.legend.isEnabled = true
            barChart.legend.textColor = Color.WHITE

            //remove description label
            barChart.description.isEnabled = false

            //add animation
            barChart.animateY(1000)

            //draw chart
            barChart.invalidate()
        }
    }


    private fun initRecycler() {
        val db = Firebase.firestore
        val email = Firebase.auth.currentUser?.email
        db.collection("expenses")
            .whereEqualTo("mail", email)
            .get()
            .addOnSuccessListener { expensesDocuments ->
                val expenses = mutableListOf<Expenses>()

                for (expenseDocument in expensesDocuments) {

                    if (expensesDocuments.count() == 0) {
                        //TODO mostrar layout recycler
                        Log.e("aca", "Igual a 0")
                    } else {
                        //TODO esconder layout recycler
                        Log.e("aca", "MENOR a  0")
                    }

                    val name = expenseDocument.data["name"].toString()
                    val amount = expenseDocument.data["amount"].toString()
                    val date = expenseDocument.data["date"].toString()
                    val category = expenseDocument.data["category"].toString()
                    val expense =
                        Expenses(expenseDocument.id, name, "", amount.toDouble(), date, category)

                    expenses.add(expense)

                    val adapter = context?.let { ExpensesAdapter(it, expenses) }

                    binding.listaGastos.layoutManager = LinearLayoutManager(requireContext())
                    binding.listaGastos.adapter = adapter

                }
            }.addOnFailureListener {
                it.printStackTrace()
                Log.e(null, "ERROR LEER DATOS")
            }

    }

}