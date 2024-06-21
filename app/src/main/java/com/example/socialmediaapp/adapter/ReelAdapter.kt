package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.socialmediaapp.databinding.SinglePostLayoutItemBinding
import com.example.socialmediaapp.model.Reel

class ReelAdapter(val context: Context,val vidList:ArrayList<Reel>):RecyclerView.Adapter<ReelAdapter.ReelViewHolder>() {

    inner class ReelViewHolder(val binding:SinglePostLayoutItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelViewHolder {
        val binding = SinglePostLayoutItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ReelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return vidList.size
    }

    override fun onBindViewHolder(holder: ReelViewHolder, position: Int) {
        Glide.with(context).load(vidList.get(position).reelUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.postImg)
    }
}