package com.example.walletapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walletapp.R
import com.example.walletapp.data.model.Expenses
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ExpensesAdapter(
    private val context: Context,
    private val expensesList: MutableList<Expenses>,
) : RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_expenses, parent, false)
        return ExpensesHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ExpensesHolder, position: Int) {
        holder.expenseAllocation(expensesList[position], position)
    }

    override fun getItemCount(): Int = expensesList.size

    inner class ExpensesHolder(val view: View) : RecyclerView.ViewHolder(view) {


        private var image = view.findViewById<ImageView>(R.id.img_expenses)!!
        var name = view.findViewById<TextView>(R.id.txt_name_expenses)!!
        var date = view.findViewById<TextView>(R.id.txt_date_expenses)!!
        private var amount = view.findViewById<TextView>(R.id.txt_amount_expenses)!!


        fun expenseAllocation(expenses: Expenses, position: Int) {

            val localFile: File = File.createTempFile("images", "png")

            val amountFormat = expenses.amount.toString()
                .substring(0, expenses.amount.toString().length - 2) + ",00"
            name.text = expenses.name
            date.text = expenses.date
            amount.text = "$ $amountFormat"
            val category = expenses.category

            selectImage(category).getFile(localFile).addOnSuccessListener {
                Glide.with(context).load(localFile).into(image)
            }

        }

    }

    private fun selectImage(category: String): StorageReference {
        val mStorageRef: StorageReference?
        var child = "images/"
        mStorageRef = FirebaseStorage.getInstance().reference

        when (category) {
            "Liqueur" -> {
                child += "beer.png"
            }
            "Gym" -> {
                child += "gym.png"
            }
            "Restaurant" -> {
                child += "restaurant.png"
            }
            "Health" -> {
                child += "hospital.png"
            }
            "Shopping" -> {
                child += "shopping-bag.png"
            }
            "Market" -> {
                child += "shopping-cart.png"
            }
        }
        return mStorageRef.child(child)

    }

}