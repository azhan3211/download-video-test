package com.mizani.vidiotest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.vidiotest.data.Episode
import com.mizani.vidiotest.VidioState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class ActivityViewModel : ViewModel() {

    private val data = MutableLiveData<VidioState>()

    fun download(episode: Episode) {
        // this is where should we put download use case that using VidioSDK.downloadEpisode
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(5000)
                // For showing error state
                if (episode.id == 3L) {
                    throw IllegalStateException("Failed to Download")
                }
                launch(Dispatchers.Main) {
                    data.value = VidioState.Success(episode)
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    data.value = VidioState.Failed(episode)
                }
            }
        }
    }

    fun getData(): LiveData<VidioState> = data

}