package com.android.learning.flowlab.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import kotlin.jvm.Throws

class MainViewModel3: ViewModel() {

    val basicFlow = flow {
        var i = 0
        while (true){
            emit(i++)
            delay(200)
        }
    }

    val riskyFlow  = flow {
        emit(1)
        emit(2)
        delay(10)
        throw IllegalStateException("boom-in-upstream")
    }
    private val _channel = Channel<Int>(capacity = Channel.RENDEZVOUS)
    val channel = _channel
    val channelFlow = channel.receiveAsFlow()

    fun startProducingChannel(scope: CoroutineScope = viewModelScope,total:Int =10 , delayTime:Long = 100L ){
        scope.launch {
            repeat(total){
                val t1 = System.currentTimeMillis()
                _channel.send(it)
                val t2 = System.currentTimeMillis()
                Log.w("Main view model3 ","producer took ${(t2-t1)} time but delay time is $delayTime")
                delay(delayTime)
            }
            _channel.close()
        }

    }
    fun closeChannel(cause: Throwable? = null){
        if(cause!=null)_channel.close(cause)else _channel.close()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        if (!_channel.isClosedForSend) _channel.close()
    }
}
