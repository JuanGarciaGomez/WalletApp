package com.example.walletapp.ui.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.walletapp.R
import com.example.walletapp.adapter.ExpensesAdapter
import com.example.walletapp.data.model.AddMoves
import com.example.walletapp.databinding.MainFragmentBinding
import com.example.walletapp.ui.viewmodel.MainFragmentViewModel
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.example.walletapp.utils.Utils
import com.example.walletapp.utils.Utils.Companion.move
import com.example.walletapp.utils.Utils.Companion.onBack
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    /**
     * This fragment is responsible about show  different recyclerView
     * according to the need
     */
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
        binding.viewModelMainFragment = mainFragmentViewModel
        binding.lifecycleOwner = this

        Log.e("onCreateView",move)
        binding.txtBalance.text = move

        mainFragmentViewModel.navigation.observe(this, {
            when (it) {
                NAVIGATION.GO_ADD_TAP_SUCCESS -> {
                    onBack = 1
                    showDialog()
                }
            }
        })
        initRecycler()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        // TODO: Use the ViewModel
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


    @SuppressLint("SetTextI18n")
    private fun initRecycler() {
        Log.e("entro","Recycler")
        val db = Firebase.firestore
        val email = Firebase.auth.currentUser?.email
        var totalAmount = 0.0;
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("addMoves")
                .whereEqualTo("mail", email)
                .whereEqualTo("move", move)
                .get()
                .addOnSuccessListener { expensesDocuments ->
                    val expenses = mutableListOf<AddMoves>()

                    for (expenseDocument in expensesDocuments) {

                        if (expensesDocuments.count() == 0) {
                            binding.progressCircular.visibility = View.GONE
                        } else binding.imageEmpty.visibility = View.GONE

                        val name = expenseDocument.data["name"].toString()
                        val amount = expenseDocument.data["amount"].toString()
                        val date = Utils.dataDBtoUI(expenseDocument.data["date"].toString())
                        val category = expenseDocument.data["category"].toString()
                        val expense =
                            AddMoves(expenseDocument.id,
                                name,
                                "",
                                amount.toDouble(),
                                date,
                                category)
                        totalAmount += amount.toDouble()

                        expenses.add(expense)
                    }

                    expenses.sortByDescending {
                        it.date
                    }
                    activity?.runOnUiThread {
                        if (expenses.isEmpty()) binding.imageEmpty.visibility = View.VISIBLE
                        val adapter = context?.let { ExpensesAdapter(it, expenses) }

                        binding.listaGastos.layoutManager = LinearLayoutManager(requireContext())
                        binding.listaGastos.adapter = adapter
                        binding.txtAmount.text =
                            "$ ${
                                totalAmount.toString()
                                    .substring(0, totalAmount.toString().length - 2)
                            },00"

                        binding.progressCircular.visibility = View.GONE

                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                    Log.e(null, "ERROR LEER DATOS")
                }
        }


    }

    private fun showDialog() {
        val isLargeLayout = resources.getBoolean(R.bool.large_layout)
        val fragmentManager = requireActivity().supportFragmentManager
        val newFragment = AddFragment()
        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction
                .add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}