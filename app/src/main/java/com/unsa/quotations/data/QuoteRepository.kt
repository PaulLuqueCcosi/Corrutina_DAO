package com.unsa.quotations.data

import android.util.Log
import com.unsa.quotations.data.database.daos.QuoteDao
import com.unsa.quotations.data.database.entities.QuoteEntity
import com.unsa.quotations.data.network.QuoteService
import com.unsa.quotations.domain.model.Quote
import com.unsa.quotations.domain.model.toDomain
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.Job
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

class QuoteRepository @Inject constructor (
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response = api.getQuotes()
        return response.map { it.toDomain() }
    }
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        Log.d("corrutina","Estoy en la coroutine random-getAllQutes: ${coroutineContext[Job]}")
        val response = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }
    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }
    suspend fun clearQuotes() {
        quoteDao.deleteAll()
    }
}