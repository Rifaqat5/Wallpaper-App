package com.rifaqat.wallpaper.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.Directory
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rifaqat.wallpaper.R
import com.rifaqat.wallpaper.databinding.FragmentDetailBinding
import com.rifaqat.wallpaper.model.Images
import com.squareup.picasso.Picasso
import java.io.File

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args:DetailFragmentArgs by navArgs()
    private var photo:Images?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo=args.photo
        Picasso.get().load(photo?.src?.original)
            .placeholder(R.drawable.placeholder)
            .into(binding.detailImage)

        binding.download.setOnClickListener {
            try{
                downloadImage()
            }catch (e:Exception){
                Log.d("exceptionDuringDownload", "Failed to download due to $e: Exception.")
            }
        }
        binding.setWallpaper.setOnClickListener {
            setWallpaper()
        }
    }



    private fun setWallpaper() {
        val wallpaperManager=WallpaperManager.getInstance(requireContext())
        val bitmap: Bitmap? = (binding.detailImage.drawable as? BitmapDrawable)?.bitmap
        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(requireContext(), "Wallpaper Set Successfully...", Toast.LENGTH_SHORT).show()
        }
        catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(requireContext(), "Couldn't Set Wallpaper Successfully...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadImage() {
        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(photo?.src?.large2x)

        // Create a directory in the external storage where the image will be saved
        val storageDir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("downloadWallpaperOn :::","Image Download on Android 10 or Upper.")
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        }else{
            Log.d("downloadWallpaperOn :::","Image Download on less than Android 10.")
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/Wallpapers")
        }
        val fileName = "Wallpaper_${photo?.photographer}.jpg"
        val destinationFile = File(storageDir, fileName)




        val request = DownloadManager.Request(uri)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setAllowedOverRoaming(false)
            .setTitle("Wallpaper ${photo?.photographer}")
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(destinationFile))

        downloadManager.enqueue(request)
    }


}