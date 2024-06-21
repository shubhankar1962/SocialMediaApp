package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.socialmediaapp.databinding.ReelsViewDesignBinding
import com.example.socialmediaapp.model.Reel

class ReelScrollAdapter(val context: Context,val reelList:ArrayList<Reel>): RecyclerView.Adapter<ReelScrollAdapter.ScrollReelViewHolder>(){

    inner class ScrollReelViewHolder(val binding:ReelsViewDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollReelViewHolder {
        val binding = ReelsViewDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ScrollReelViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ScrollReelViewHolder, position: Int) {
        Glide.with(context).load(reelList.get(position).profileImg).into(holder.binding.reelsUserProfileImg)
        holder.binding.reelCaption.text = reelList.get(position).caption
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBarId.visibility = View.GONE
            holder.binding.videoView.start()
        }

    }
}