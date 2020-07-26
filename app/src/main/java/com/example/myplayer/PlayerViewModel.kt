package com.example.myplayer

import android.graphics.Point
import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 * @author iwen大大怪
 * Create to 2020/7/26 23:07
 *
 */
class PlayerViewModel:ViewModel() {
    val mediaPlayer = MediaPlayer()
    private val _progressbarVisibility = MutableLiveData(View.VISIBLE)
    val progressbarVisibility:LiveData<Int> = _progressbarVisibility
    private val _videoResolution = MutableLiveData(Pair(0,0))
    val videoResolution :LiveData<Pair<Int,Int>> = _videoResolution
    init {
        loadVideo()
    }

    // 加载视频
    fun loadVideo(){
        mediaPlayer.apply {
            _progressbarVisibility.value = View.VISIBLE
            setDataSource("http://192.168.100.5/shipin/001.mp4")
            setOnPreparedListener {
                _progressbarVisibility.value = View.INVISIBLE
                isLooping = true
                it.start()
            }
            setOnVideoSizeChangedListener { _, width, height ->
                _videoResolution.value = Pair(width,height)
            }
            prepareAsync()
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}