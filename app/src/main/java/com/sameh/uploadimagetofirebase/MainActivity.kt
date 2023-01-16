package com.sameh.uploadimagetofirebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sameh.uploadimagetofirebase.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef = FirebaseStorage.getInstance().reference

        binding.btnGetImage.setOnClickListener {
            getImage()
        }

    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data!!.data
                val filePath = storageRef.child(Calendar.getInstance().time.toString())

                binding.progressBar.visibility = View.VISIBLE

                filePath.putFile(uri!!)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Uploaded successful", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                    .addOnCanceledListener {
                        Toast.makeText(this, "unable to upload", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
            } else {
                Toast.makeText(this, "unable to get image", Toast.LENGTH_SHORT).show()
            }
        }

}















