package com.example.socialmediaapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.socialmediaapp.adapter.ViewPagerAdapter
import com.example.socialmediaapp.auth.SignUpActivity
import com.example.socialmediaapp.databinding.FragmentProfileBinding
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.USER_NODE

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    lateinit var binding:FragmentProfileBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context))

        binding.editBtn.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(),"MyPost")
        viewPagerAdapter.addFragments(MyReelsFragment(),"MyReels")
        binding.viewPagerid.adapter = viewPagerAdapter
        binding.tabLayoutId.setupWithViewPager(binding.viewPagerid)
//        TabLayoutMediator(binding.tabLayoutId,binding.viewPagerid){tab,position->
//            tab.text = when(position){
//                0 -> "MYPost"
//                1-> "MyReels"
//                else ->{
//                    throw IndexOutOfBoundsException("Invalid Index")
//                }
//            }
//
//        }.attach()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!

                binding.Name.text = user.name.toString()
                binding.Bio.text = user.bio.toString()

                Glide.with(requireContext()).load(user.image).into(binding.circleImageView)
            }
    }

}