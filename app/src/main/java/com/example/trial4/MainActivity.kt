package com.example.trial4

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var imagePickerLauncher : ActivityResultLauncher<Intent>

    private lateinit var  selectedImageUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if(result.resultCode== RESULT_OK){
                val data: Intent? = result.data
                if (data!=null){
                    selectedImageUri  = data.data!!
                    Log.i(TAG,"selectedImageUri $selectedImageUri")
                    val imageView = findViewById<ImageView>(R.id.imageView)
                    imageView.setImageURI(selectedImageUri)
                }
            }

        }
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)


        // Start with the home activity
        bottomNavigationView.selectedItemId = R.id.home

        val btnChoose = findViewById<Button>(R.id.btnChoose)
        btnChoose.setOnClickListener {
            Log.i(TAG,"Open up Image picker on device")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if(imagePickerIntent.resolveActivity(packageManager)!=null){
                imagePickerLauncher.launch(imagePickerIntent)
            }
        }

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            // Check if an image has been selected
            if (selectedImageUri != null) {
                // Start the new activity and pass the selectedImageUri
                val intent = Intent(this@MainActivity, SuscheckActivity::class.java)
                intent.putExtra("imageUri", selectedImageUri.toString())
                startActivity(intent)
            } else {
                // Handle the case where no image has been selected
                Toast.makeText(this@MainActivity, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }

        setupNavigation()





    }

    private fun setupNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                    true
                }
                R.id.history -> {
                    startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                    true
                }
                R.id.about -> {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }


}