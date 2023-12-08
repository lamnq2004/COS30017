package com.example.core2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class RentActivity : AppCompatActivity() {

    private lateinit var saveButton : Button
    private lateinit var cancelButton : Button
    private var totalDay = 0
    private var selectedDay = 0
    private var returnDate: LocalDate? = null
    private var product: Product? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)

        product = intent.getParcelableExtra("selectedProduct", Product::class.java)

        product?.let{
            val productName = findViewById<TextView>(R.id.productName)
            productName.text = it.name

            val productPrice = findViewById<TextView>(R.id.productPrice)
            productPrice.text = "$${it.price}"

            val productImage = findViewById<ImageView>(R.id.productImage)
            productImage.setImageResource(it.imageID)
        }

        cancelButton = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        val slider : Slider = findViewById(R.id.priceSlider)
        val totalPrice : TextView = findViewById(R.id.totalPrice)

        val initialDay = slider.value.toInt()
        val todayDate = LocalDate.now()
        returnDate = todayDate.plusDays(initialDay.toLong())

        slider.addOnChangeListener {_, value, fromUser ->
            if (fromUser) {
                product?.let {
                    selectedDay = value.toInt()
                    totalDay = (it.price * value).toInt()
                    totalPrice.text = "$$totalDay"

                    returnDate = todayDate.plusDays(selectedDay.toLong())
                }
            }
        }

        saveButtonHandler()
    }

    private fun saveButtonHandler(){
        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            if (totalDay > 0) {
                val intent = Intent()
                product?.let{
                    it.rented = true
                    intent.putExtra("RENTED_STATE", it.rented)
                    Log.i("RENTED_STATE", "Returned ${it.rented}")
                }
                intent.putExtra("RETURN_DATE", returnDate.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
                val rootLayout : ConstraintLayout = findViewById(R.id.activityRent)
                val snackBar = Snackbar.make(rootLayout,"You must select at least 1 day!", Snackbar.LENGTH_SHORT)
                snackBar.show()
            }
        }
    }
}
