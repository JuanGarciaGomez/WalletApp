package com.example.walletapp.ui.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletapp.R
import com.example.walletapp.data.extension_functions.intentTo
import com.example.walletapp.data.extension_functions.toast
import com.example.walletapp.databinding.FragmentAddBinding
import com.example.walletapp.ui.view.MainView
import com.example.walletapp.ui.viewmodel.AddViewModel
import com.example.walletapp.ui.viewmodel.ERROR
import com.example.walletapp.ui.viewmodel.NAVIGATION
import com.example.walletapp.ui.viewmodel.SUCCESS
import com.example.walletapp.utils.Utils.Companion.onBack
import com.google.android.material.datepicker.MaterialDatePicker

class AddFragment : DialogFragment() {

    /**
     * This fragment is responsible about show the form of add expenses
     */

    private lateinit var dialogViewModel: AddViewModel

    private lateinit var binding: FragmentAddBinding

    private val context = this

    companion object {
        fun newInstance() = AddFragment()
    }

    /** The system calls this to get the DialogFragment's layout, regardless
    of whether it's being displayed as a dialog or an embedded fragment. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        dialogViewModel = ViewModelProvider(this)[AddViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.viewModelDialog = dialogViewModel
        binding.lifecycleOwner = this

        categoryPicker()

        dialogViewModel.success.observe(context, {
            when (it) {
                SUCCESS.CLOSE_SUCCESS -> {
                    intentTo(MainView::class.java)
                    onBack = 0
                }
                SUCCESS.ADD_SUCCESS -> {
                    toast("Expenses Add :)")
                }
                SUCCESS.DATE_PICKER -> {
                    materialDatePicker()
                }
            }
        })

        dialogViewModel.error.observe(context, {
            when (it) {
                ERROR.EMPTY_FIELDS -> {
                    toast("Campos vacios")

                }
                ERROR.ERROR_ADD_EXPENSES -> {
                    toast("Error to add expenses")
                }
            }
        })

        dialogViewModel.navigation.observe(context, {
            when (it) {
                NAVIGATION.GO_MAIN_VIEW -> {
                    intentTo(MainView::class.java)
                }
            }
        })
        return binding.root

    }

    /**
     * Options about category, you need the layout to implements this
     *  list_item.xml
     */
    private fun categoryPicker() {
        val items = listOf("Health", "Liqueur", "Gym", "Restaurant", "Shopping", "Market")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.spinnerEdit as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    /**
     * Is a dataPicker from materialDesign2
     */
    private fun materialDatePicker() {

        val datePickerMaterial =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setTheme(R.style.MaterialCalendarTheme)
                .build()

        datePickerMaterial.show(
            requireFragmentManager(), "datePicker"
        )
        datePickerMaterial.addOnPositiveButtonClickListener {
            binding.dateEdit.setText(datePickerMaterial.headerText)
        }

    }

    fun afterTextChanged() {
        try {

            val s = String.format("%,d", binding.amountAdd.toString().toLong());
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Log.e(null, "onCreateDialog")
        return dialog
    }
}