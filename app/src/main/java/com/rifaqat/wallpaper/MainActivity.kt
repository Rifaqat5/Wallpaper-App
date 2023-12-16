package com.rifaqat.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import com.rifaqat.wallpaper.R
import com.rifaqat.wallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        try{
            val navController = Navigation.findNavController(this, R.id.fragment)
            navController.navigate(R.id.parentLayout)
        }

        catch (e:Exception){
            Log.d("e", "onCreate: $e")
        }

    }
}