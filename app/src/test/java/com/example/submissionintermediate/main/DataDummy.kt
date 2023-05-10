package com.example.submissionintermediate.main

import com.example.submissionintermediate.api.ListStoryItem

object DataDummy {
    fun generateDummyStory(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val story = ListStoryItem(
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                "Siapa hayo",
                "iyhhh",
                101.4032221,
                "user-UrXzb6raFqy5llEL", 0.4727186,
            )
            storyList.add(story)
        }
        return storyList
    }
}
