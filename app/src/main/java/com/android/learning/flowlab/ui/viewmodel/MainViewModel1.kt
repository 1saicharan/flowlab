package com.android.learning.flowlab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainViewModel1(dispatcher: CoroutineDispatcher = Dispatchers.Main): ViewModel() {

    private val scope = CoroutineScope(dispatcher)

    val counter = flow {
        var count = 0
        while(true){
            emit(count++)
            delay(300)
        }
    }
    val state : StateFlow<Int> = counter.stateIn(scope, SharingStarted.WhileSubscribed(5000),0)

    val events = MutableSharedFlow<String>(0)

    suspend fun ping(label :String = "ping"){
        events.emit(label)
    }
}