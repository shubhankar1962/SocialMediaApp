package com.example.socialmediaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.socialmediaapp.adapter.SearchUsersAdapter
import com.example.socialmediaapp.databinding.FragmentSearchBinding
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app


class SearchFragment : Fragment() {

    lateinit var binding:FragmentSearchBinding
    lateinit var adapter: SearchUsersAdapter
    lateinit var userList:ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)

        userList = arrayListOf<User>()
        adapter = SearchUsersAdapter(requireContext(), userList)
        binding.searchUserRecyclerView.adapter =adapter
        binding.searchUserRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var tempList = ArrayList<User>()
            userList.clear()
            for (data in it.documents)
            {
                if(data.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))
                {

                }
                else{
                    val user:User = data.toObject<User>()!!
                    tempList.add(user)
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        binding.searchBtn.setOnClickListener{
            val searchedText = binding.searchView.text.toString()

            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",searchedText).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                userList.clear()
                if(it.isEmpty)
                {
                    Toast.makeText(requireContext(),"User not Present",Toast.LENGTH_SHORT).show()
                }else{
                    for (data in it.documents)
                    {
                        if(data.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))
                        {

                        }
                        else{
                            val user:User = data.toObject<User>()!!
                            tempList.add(user)
                        }
                    }
                    userList.addAll(tempList)
                    adapter.notifyDataSetChanged()
                }

            }
        }
        return binding.root
    }

}