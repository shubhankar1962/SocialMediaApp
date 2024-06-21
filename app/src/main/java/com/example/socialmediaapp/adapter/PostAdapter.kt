package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.databinding.SinglePostLayoutItemBinding
import com.example.socialmediaapp.model.Post


class PostAdapter (val context:Context,val postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    inner class PostViewHolder(val binding:SinglePostLayoutItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = SinglePostLayoutItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size;
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        Glide.with(context).load(postList.get(position).postImgUrl).into(holder.binding.postImg)
    }
}