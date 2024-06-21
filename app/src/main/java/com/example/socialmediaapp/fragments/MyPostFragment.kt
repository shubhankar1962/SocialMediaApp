package com.example.socialmediaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.PostAdapter
import com.example.socialmediaapp.databinding.FragmentMyPostBinding
import com.example.socialmediaapp.model.Post
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class MyPostFragment : Fragment() {

    lateinit var binding:FragmentMyPostBinding
    lateinit var imgList:ArrayList<Post>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(layoutInflater)

        imgList = ArrayList()
        var adapter = PostAdapter(requireContext(),imgList)
        binding.myPostRecyclerView.adapter = adapter
        binding.myPostRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                var tempList = arrayListOf<Post>()
                for(data in it.documents)
                {
                    var post:Post = data.toObject<Post>()!!
                    tempList.add(post)
                }
                imgList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }


}