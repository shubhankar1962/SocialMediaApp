package com.example.socialmediaapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.FragmentAddBinding
import com.example.socialmediaapp.post.PostActivity
import com.example.socialmediaapp.post.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddFragment : BottomSheetDialogFragment(){
    lateinit var binding:FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentAddBinding.inflate(layoutInflater)

        binding.addPostId.setOnClickListener{
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }
        binding.addReelsId.setOnClickListener{
            startActivity(Intent(requireContext(),ReelsActivity::class.java))
            activity?.finish()
        }

        return binding.root
    }


}