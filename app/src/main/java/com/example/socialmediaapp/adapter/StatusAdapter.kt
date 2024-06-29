package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R

import com.example.socialmediaapp.databinding.StatusRvItemBinding
import com.example.socialmediaapp.model.User

class StatusAdapter(val context:Context,val statusList:ArrayList<User>):RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {


    inner class StatusViewHolder(val binding: StatusRvItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val binding = StatusRvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return StatusViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return statusList.size
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
       // if(statusList.get(position).image.isNullOrEmpty()){
            Glide.with(context).load(statusList.get(position).image).placeholder(R.drawable.profile_icon).into(holder.binding.statusRvItemImg)
//        }
//        else {
//            Glide.with(context).load(statusList.get(position).image)
//                .into(holder.binding.statusRvItemImg)
//        }
    }
}