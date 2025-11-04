package com.android.learning.flowlab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.learning.flowlab.ui.enums.NetworkStatus
import com.android.learning.flowlab.ui.states.CombineState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MainViewModel2: ViewModel() {

    private val query = MutableStateFlow(arrayListOf("fun","fun2","fun3").random())


    val queryFlow = query.debounce(300)

        .flatMapLatest { it -> searchQuery(it) }

    val statusFlow = flow {
        while (currentCoroutineContext().isActive){
            emit(NetworkStatus.ONLINE)
            delay(2000)
            emit(NetworkStatus.OFFLINE)
            delay(2000)
        }
    }.distinctUntilChanged()

    val ui = combine(queryFlow,statusFlow){
        results,status ->
        CombineState(results,status)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CombineState(emptyList(), NetworkStatus.ONLINE))
    private fun searchQuery(q:String): Flow<List<String>> = flow {
        if (q.isBlank()) {
            emit(emptyList())
            return@flow
        }
        delay(500)
        emit(listOf("res for $q"))

    }

    val stress = flow {
        for(i in 1..100){
            emit(i)
            delay(100)
        }
    }

    suspend fun heavyCpu(i:Int) {
        delay(500)
    }
}