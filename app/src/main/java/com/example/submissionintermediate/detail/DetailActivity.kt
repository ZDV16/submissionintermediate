package com.example.submissionintermediate.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionintermediate.api.ListStoryItem
import com.example.submissionintermediate.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem

        binding.apply {
            tvName.text = detail.name
            tvDesc.text = detail.description
            Picasso.get()
                .load(detail.photoUrl)
                .into(ivStory)
        }
        supportActionBar?.hide()
    }
}