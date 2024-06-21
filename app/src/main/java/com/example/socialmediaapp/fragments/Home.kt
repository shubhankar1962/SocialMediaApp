package com.example.socialmediaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.HomePostAdapter
import com.example.socialmediaapp.databinding.FragmentHomeBinding
import com.example.socialmediaapp.model.Post
import com.example.socialmediaapp.utility.POSTS
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class Home : Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var postList: ArrayList<Post>
    lateinit var adapter: HomePostAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentHomeBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        postList = arrayListOf<Post>()
        adapter = HomePostAdapter(requireContext(),postList);
        binding.homePagePostsRv.adapter = adapter
        binding.homePagePostsRv.layoutManager = LinearLayoutManager(requireContext())

        Firebase.firestore.collection(POSTS).get()
            .addOnSuccessListener {
                postList.clear()
                var tempList = arrayListOf<Post>()
                for(data in it.documents){
                    val post:Post = data.toObject<Post>()!!
                    tempList.add(post)
                }
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}