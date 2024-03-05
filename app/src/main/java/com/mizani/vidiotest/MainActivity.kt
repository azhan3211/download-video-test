package com.mizani.vidiotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mizani.vidiotest.adapter.VidioAdapter
import com.mizani.vidiotest.data.DownloadStatus
import com.mizani.vidiotest.data.Episode
import com.mizani.vidiotest.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: ActivityViewModel = ActivityViewModel()
    private val episodes = arrayListOf(
        Episode(1, "First", "First Description", "", "First Description", true, DownloadStatus.NOT_DOWNLOADED),
        Episode(2, "Second", "Second Description", "", "Second Description", true, DownloadStatus.NOT_DOWNLOADED),
        Episode(3, "Third", "Third Description", "", "Third Description", true, DownloadStatus.NOT_DOWNLOADED),
        Episode(4, "Forth", "Forth Description", "", "Forth Description", true, DownloadStatus.NOT_DOWNLOADED),
        Episode(5, "Fifth", "Fifth Description", "", "Fifth Description", true, DownloadStatus.NOT_DOWNLOADED),
        Episode(6, "Sixth", "Sixth Description", "", "Sixth Description", true, DownloadStatus.NOT_DOWNLOADED),
    )
    private val adapter by lazy {
        VidioAdapter(episodes, ::downloadVidio)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupVidios()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.getData().observe(this) {
            when (it) {
                is VidioState.Failed -> {
                    Toast.makeText(this, "Failed to download", Toast.LENGTH_SHORT).show()
                    updateVidioList(it.episode.id, DownloadStatus.NOT_DOWNLOADED)
                }
                is VidioState.Success -> {
                    updateVidioList(it.episode.id, DownloadStatus.DOWNLOAD_FINISH)
                }
            }
        }
    }

    private fun setupVidios() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun updateVidioList(id: Long, status: DownloadStatus) {
        val episode = episodes.find { it.id == id }
        val index = episodes.indexOf(episode)
        episodes[index] = episodes[index].copy(downloadStatus = status)
        adapter.notifyItemChanged(index)
    }

    private fun downloadVidio(episode: Episode) {
        updateVidioList(episode.id, DownloadStatus.DOWNLOADING)
        viewModel.download(episode)
    }
}