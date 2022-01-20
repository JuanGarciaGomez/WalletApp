package com.example.walletapp.adapter

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.walletapp.R
import com.example.walletapp.data.model.AddMoves
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ExpensesAdapter(
    private val context: Context,
    private val addMovesList: MutableList<AddMoves>,
) : RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_expenses, parent, false)
        return ExpensesHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ExpensesHolder, position: Int) {
        holder.expenseAllocation(addMovesList[position], position)
    }

    override fun getItemCount(): Int = addMovesList.size

    inner class ExpensesHolder(val view: View) : RecyclerView.ViewHolder(view) {


        private var image = view.findViewById<ImageView>(R.id.img_expenses)!!
        var name = view.findViewById<TextView>(R.id.txt_name_expenses)!!
        var date = view.findViewById<TextView>(R.id.txt_date_expenses)!!
        private var amount = view.findViewById<TextView>(R.id.txt_amount_expenses)!!
        private var space = view.findViewById<View>(R.id.space)!!

        fun expenseAllocation(addMoves: AddMoves, position: Int) {
            if (position == addMovesList.size - 1) space.visibility = View.VISIBLE

            val localFile: File = File.createTempFile("images", "png")

            val amountFormat = addMoves.amount.toString()
                .substring(0, addMoves.amount.toString().length - 2) + ",00"
            name.text = addMoves.name
            date.text = addMoves.date
            amount.text = "$ $amountFormat"


            val category = addMoves.category

            val imgFile = File("/data/data/com.example.walletapp/app_Imagenes/$category.png")

            if (imgFile.exists()) {

                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                image.setImageBitmap(myBitmap)

            } else {
                selectImage(category).getFile(localFile).addOnSuccessListener {
                    Glide.with(context)
                        .asBitmap()
                        .load(localFile)
                        .centerCrop()
                        // .placeholder(R.drawable.wallet)
                        // .error(R.drawable.exit)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?,
                            ) {
                                image.setImageBitmap(resource)
                                saveImages(context, category, resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                TODO("Not yet implemented")
                            }

                        })
                }

            }

            view.setOnClickListener {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * According the category
     * bring an image of fireBase
     */
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

    fun saveImages(context: Context, name: String, image: Bitmap): String {
        val cw = ContextWrapper(context)
        val dirImage = cw.getDir("Imagenes", Context.MODE_PRIVATE)
        val myPath = File(dirImage, "$name.png")
        val fos: FileOutputStream?
        try {
            fos = FileOutputStream(myPath)
            image.compress(Bitmap.CompressFormat.PNG, 10, fos)
            fos.flush()
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return myPath.absolutePath
    }
}