package com.example.socialmediaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.viewpager2.widget.ViewPager2
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.ReelAdapter
import com.example.socialmediaapp.adapter.ReelScrollAdapter
import com.example.socialmediaapp.databinding.ActivityReelsBinding
import com.example.socialmediaapp.databinding.FragmentReelBinding
import com.example.socialmediaapp.model.Reel
import com.example.socialmediaapp.utility.REEL
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class ReelFragment : Fragment() {

    lateinit var binding:FragmentReelBinding
    lateinit var reelList:ArrayList<Reel>
    lateinit var reelAdapter: ReelScrollAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelBinding.inflate(layoutInflater)

        reelList = arrayListOf<Reel>()
        reelAdapter = ReelScrollAdapter(requireContext(),reelList)

        binding.reelViewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.reelViewPager.adapter = reelAdapter

        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = arrayListOf<Reel>()
            reelList.clear()
            for(data in it.documents)
            {
                val reel:Reel = data.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            reelAdapter.notifyDataSetChanged()
        }

        return binding.root
    }


}