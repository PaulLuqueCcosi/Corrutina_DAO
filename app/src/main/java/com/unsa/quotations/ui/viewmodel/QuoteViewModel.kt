package com.unsa.quotations.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsa.quotations.domain.GetQuotesUseCase
import com.unsa.quotations.domain.GetRandomQuoteUseCase
import com.unsa.quotations.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.GlobalScope.coroutineContext
//import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor (
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {
    val quoteModel = MutableLiveData<Quote>()
    val isLoading = MutableLiveData<Boolean>()
    fun onCreate() {
//        Log.d("corrutina","Estoy en la coroutine: ${coroutineContext[Job]}")
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getQuotesUseCase()
            Log.d("corrutina","Estoy en la coroutine oncreate: ${coroutineContext[Job]}")
            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }
    }
    fun randomQuote() {
        viewModelScope.launch(Dispatchers.IO){
            isLoading.postValue(true)
            val quote = getRandomQuoteUseCase()
            Log.d("corrutina","Estoy en la coroutine random: ${coroutineContext[Job]}")
            if (quote != null) {
                quoteModel.postValue(quote)
            }
            isLoading.postValue(false)
        }
    }
}