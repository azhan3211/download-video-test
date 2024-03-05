package com.mizani.vidiotest.data

data class Episode(
    val id: Long,
    val title: String,
    val duration: String,
    val thumbnailUrl: String,
    val description: String,
    val isFree: Boolean,
    val downloadStatus: DownloadStatus
)