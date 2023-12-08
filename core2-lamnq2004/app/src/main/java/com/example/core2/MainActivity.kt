package com.example.core2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var productImage :ImageView
    private lateinit var productName : TextView
    private lateinit var productRating : RatingBar
    private lateinit var productPrice : TextView
    private lateinit var pgButton : Button
    private lateinit var mButton : Button
    private lateinit var button3 : Button
    private lateinit var nextButton : Button
    private lateinit var rentButton : Button

    private var currentItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productImage = findViewById(R.id.productImage)
        productName = findViewById(R.id.productName)
        productRating = findViewById(R.id.ratingBar)
        productPrice = findViewById(R.id.productPrice)

        pgButton = findViewById(R.id.PGbutton)
        mButton = findViewById(R.id.Mbutton)
        button3 = findViewById(R.id.button3)
        nextButton = findViewById(R.id.nextButton)
        rentButton = findViewById(R.id.rentButton)

        displayProduct()

        nextButton.setOnClickListener{
            if (currentItem < items.size - 1){
                currentItem++
            } else{
                currentItem = 0
            }
            displayProduct()
        }

        rentButton.setOnClickListener {
            navigateToRent()
        }
    }

    private val items = listOf(
        Product("Kirby and the Forgotten Land", 5.toFloat(), listOf("PG", "M", "3"), 5, R.drawable.game_1),
        Product("Legends of Zelda Tears of Kingdom", 4.toFloat(), listOf("3"), 6, R.drawable.game_2),
        Product("MarioKart 8 Deluxe", 4.toFloat(), listOf("3", "M"), 4, R.drawable.game_3),
        Product("Overcooked! All You Can Eat", 3.toFloat(), listOf("3", "M"), 2, R.drawable.game_4),
    )

    private fun displayProduct(){
        if(currentItem in 0 until items.size){
            val item = items[currentItem]

            updateProductDetail(item)
            updateMultipleChoice(item)

            checkRentState(item)
        }
    }

    private fun updateProductDetail(item : Product){
        productImage.setImageResource(item.imageID)
        productName.text = item.name
        productPrice.text = "$${item.price}"
        productRating.rating = item.rating
    }

    private fun updateMultipleChoice(item : Product){
        pgButton.isSelected = item.attributes.contains("PG")
        mButton.isSelected = item.attributes.contains("M")
        button3.isSelected = item.attributes.contains("3")
    }

    private val setForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        val item = items[currentItem]

        if (result.resultCode == Activity.RESULT_CANCELED){
            val rootLayout : ConstraintLayout = findViewById(R.id.activityMain)
            val snackBar = Snackbar.make(rootLayout,"Bye!", Snackbar.LENGTH_SHORT )
            snackBar.show()
        } else{
            val rootLayout : ConstraintLayout = findViewById(R.id.activityMain)
            val snackBar = Snackbar.make(rootLayout,"Thank you!", Snackbar.LENGTH_SHORT )
            snackBar.show()
            val returnDate = result.data?.getStringExtra("RETURN_DATE")
            returnDate?.let{
                item.returnDate = it
                rentButton.text = "Return by $it"
            }
            val rentedState = result.data?.getBooleanExtra("RENTED_STATE", false)
            rentedState?.let{
                item.rented = it
            }
        }
        displayProduct()
    }

    private fun navigateToRent(){
        val intent = Intent(this, RentActivity::class.java)
        intent.putExtra("selectedProduct", items[currentItem])
        setForResult.launch(intent)
    }

    private fun checkRentState(item: Product){
        if (item.rented) {
            rentButton.text = "Return by ${item.returnDate}"
            rentButton.isEnabled = false
        } else {
            rentButton.text = getString(R.string.rent_button)
            rentButton.isEnabled = true
        }
    }
}