package com.example.test_internalandexternalstorage

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.test_internalandexternalstorage.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {

        } else {

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addListeners()

    }


    private fun addListeners() {
        binding.saveButton.setOnClickListener {
            val text = binding.saveEt.text.toString()
           if ( ContextCompat.checkSelfPermission(applicationContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)
               ==PackageManager.PERMISSION_GRANTED){
               saveTextToFile(text)
               Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
           } else {
               requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
           }


        }
    }



    private fun saveTextToFile(text: String) {

        // internal path :  " data -> data -> package name "
        //  val path = applicationInfo.dataDir


        // External path :  " mnt -> sdcard -> Android -> data -> package name -> files "
         val path = getExternalFilesDir(null)?.path.toString()

        // External path using permission :  " mnt -> sdcard "
        // val path = Environment.getExternalStorageDirectory().path

        val fileName = "sample.txt"
        val file = File("$path/$fileName")
        file.writeText(text)
    }
}