package com.example.socialmediaapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.HomePostAdapter
import com.example.socialmediaapp.adapter.StatusAdapter
import com.example.socialmediaapp.databinding.FragmentHomeBinding
import com.example.socialmediaapp.model.Post
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.POSTS
import com.example.socialmediaapp.utility.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class Home : Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var postList: ArrayList<Post>
    lateinit var adapter: HomePostAdapter
    lateinit var statusAdapter: StatusAdapter
    lateinit var statusList:ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentHomeBinding.inflate(layoutInflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)


//        val data  = arguments?.getString("profileImg")
//        val uri:Uri = Uri.parse(data)
//        binding.profileStatusImgView.setImageURI(uri)
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {it->
            if(it != null){
                val data:User = it.toObject<User>()!!
                Log.e("TAG",data.image.toString())
                Glide.with(requireContext()).load(data.image).into(binding.profileStatusImgView)
            }
        }
        statusList = arrayListOf<User>()

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {it->
            statusList.clear()
            var userList = arrayListOf<User>()
            for(data in it.documents){
                val user:User = data.toObject<User>()!!
                userList.add(user)
            }
            statusList.addAll(userList)
            adapter.notifyDataSetChanged()

        }

        statusAdapter = StatusAdapter(requireContext(),statusList)
        binding.statusRV.adapter = statusAdapter
        binding.statusRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


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