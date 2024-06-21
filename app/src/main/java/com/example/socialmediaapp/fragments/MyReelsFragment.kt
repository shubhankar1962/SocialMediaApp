package com.example.socialmediaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.PostAdapter
import com.example.socialmediaapp.adapter.ReelAdapter
import com.example.socialmediaapp.databinding.FragmentMyReelsBinding
import com.example.socialmediaapp.model.Post
import com.example.socialmediaapp.model.Reel
import com.example.socialmediaapp.utility.REEL
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MyReelsFragment : Fragment() {

    lateinit var binding:FragmentMyReelsBinding
    lateinit var vidList:ArrayList<Reel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(layoutInflater)

        vidList = ArrayList()
        var adapter = ReelAdapter(requireContext(),vidList!!)
        binding.reelsRecyclerView.adapter = adapter
        binding.reelsRecyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL)
            .get()
            .addOnSuccessListener {
                var tempList = arrayListOf<Reel>()
                for(data in it.documents)
                {
                    var post: Reel = data.toObject<Reel>()!!
                    tempList.add(post)
                }
                vidList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }


        return binding.root
    }


}