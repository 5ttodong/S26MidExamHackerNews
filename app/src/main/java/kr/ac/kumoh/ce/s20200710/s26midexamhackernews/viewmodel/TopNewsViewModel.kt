package kr.ac.kumoh.ce.s20200710.s26midexamhackernews.viewmodel

import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.repository.NewsRepository

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.model.NewsItem

class TopNewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _news = MutableStateFlow<List<NewsItem>>(emptyList())
    val news = _news.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _news.value = repository.getTopStories(30)
                Log.d("TopNews", _news.value.toString())
            } catch (e: Exception) {
                Log.e("TopNews", "뉴스 로딩 실패", e)
            }
        }
    }
}