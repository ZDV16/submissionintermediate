package com.example.submissionintermediate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.databinding.ItemStoriesBinding
import com.squareup.picasso.Picasso

class StoriesAdapter : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private val listStory = ArrayList<ListStoryItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(stories: ArrayList<ListStoryItem>) {
        listStory.clear()
        listStory.addAll(stories)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(story)
            }
            binding.apply {
                tvItemName.text = story.name
                tvItemDescription.text = story.description
                Picasso.get()
                    .load(story.photoUrl)
                    .resize(1280, 720)
                    .into(imgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }
}