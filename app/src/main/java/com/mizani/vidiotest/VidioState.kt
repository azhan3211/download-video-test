package com.mizani.vidiotest

import com.mizani.vidiotest.data.Episode

sealed class VidioState {
    data class Success(val episode: Episode): VidioState()
    data class Failed(val episode: Episode): VidioState()
}