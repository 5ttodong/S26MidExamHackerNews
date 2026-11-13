package kr.ac.kumoh.ce.s20200710.s26midexamhackernews.viewmodel

import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.repository.NewsRepository

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.ac.kumoh.ce.s20200710.s26midexamhackernews.model.NewsItem

class CommentViewModel(
    private val text: String,
    private val kids: List<Long>,
    private val repository: NewsRepository
) : ViewModel() {
    private val _title = MutableStateFlow<String>(text)
    val title = _title.asStateFlow()

    private val _comments = MutableStateFlow<List<NewsItem>>(emptyList())
    val comments = _comments.asStateFlow()

    init {
        Log.d("Comments", text)
        Log.d("Comments", kids.toString())
        viewModelScope.launch {
            try {
                val comments = repository.getComments(kids)

                _comments.value = comments ?: emptyList()
                Log.d("Comments", _comments.value.toString())
            } catch (e: Exception) {
                Log.e("Comments", "댓글 로딩 실패", e)
            }
        }
    }
}