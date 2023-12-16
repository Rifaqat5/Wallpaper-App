@file:Suppress("DEPRECATION")

package com.rifaqat.wallpaper.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rifaqat.wallpaper.adapters.WallpaperAdapter
import com.rifaqat.wallpaper.api.RequestManager
import com.rifaqat.wallpaper.api.SearchResponseListener
import com.rifaqat.wallpaper.api.WallpaperResponseListener
import com.rifaqat.wallpaper.databinding.FragmentHomeBinding
import com.rifaqat.wallpaper.listeners.RecyclerViewItemClick
import com.rifaqat.wallpaper.model.Images
import com.rifaqat.wallpaper.model.Search
import com.rifaqat.wallpaper.model.Wallpaper

@Suppress("DEPRECATION")
class HomeFragment : Fragment(), RecyclerViewItemClick {
    private lateinit var binding: FragmentHomeBinding
    private var adapter: WallpaperAdapter? = null
    private var dialog: ProgressDialog? = null
    private var manager: RequestManager? = null
    private var page=0
    private var navController: NavController?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController=findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        defining dialog and manager
        activity.let {
            dialog = ProgressDialog(it)
            manager = it?.let { it1 -> RequestManager(it1) }
        }
        manager?.getWallpapers(listener, "1")

//        click listeners on next and previous buttons
        binding.next.setOnClickListener {
            val nextPage= page+1
            manager?.getWallpapers(listener,nextPage.toString())
            dialog?.show()
        }
        binding.previous.setOnClickListener {
            if (page>1){
                val previousPage=page-1
                manager?.getWallpapers(listener,previousPage.toString())
            }
        }

//        Search specific wallpaper
        binding.search.setOnQueryTextListener(object :OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                dialog?.show()
                if (query != null) {
                    manager?.searchWallpapers(searchListener,"1", query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    manager?.searchWallpapers(searchListener,"1", query)
                }
                return true
            }

        })
    }

    private val listener = object : WallpaperResponseListener {
        override fun onFetch(response: Wallpaper, message: String) {
            dialog?.dismiss()
            if (response.photos.isEmpty()) {
                activity.let {
                    Toast.makeText(it, "No Image", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            page=response.page
            showData(response.photos)
        }

        override fun onError(message: String) {
            dialog?.show()
            activity.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val searchListener=object :SearchResponseListener{
        override fun onFetch(response: Search, message: String) {
            dialog?.dismiss()
            if(response.photos.isEmpty()){
                activity.let {
                    Toast.makeText(it, "No Image found", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            showData(response.photos)
        }

        override fun onError(message: String) {
            dialog?.dismiss()
            dialog?.show()
            activity.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showData(photos: List<Images>) {
        adapter = WallpaperAdapter(requireContext(), photos, this@HomeFragment)
        activity.let {
            binding.wallpaperRv.setHasFixedSize(true)
            binding.wallpaperRv.layoutManager = GridLayoutManager(it, 2)
            binding.wallpaperRv.adapter=adapter
        }
    }

    override fun rvItemClick(photo: Images) {
        //Send photo to detailed Fragment through navController
        val action=HomeFragmentDirections.actionHomeFragmentToDetailFragment(photo)
        navController?.navigate(action)
    }
}