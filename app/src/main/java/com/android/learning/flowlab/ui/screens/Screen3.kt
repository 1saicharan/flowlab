package com.android.learning.flowlab.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.learning.flowlab.ui.viewmodel.MainViewModel2
import com.android.learning.flowlab.ui.viewmodel.MainViewModel3
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@Composable
fun Screen3(modifier: Modifier = Modifier, viewModel: MainViewModel3) {
    val basicFlowValue = remember { mutableStateOf<Int?>(null) }
    val channelFlowValue = remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.basicFlow.collect { value ->
                basicFlowValue.value = value
            }
        }
        scope.launch {
            viewModel.channelFlow
                .onCompletion {
                    delay(2000L)
                    channelFlowValue.value = -1
                }
                .collect { value ->
                    delay(400L)
                    channelFlowValue.value = value
                }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is Screen 3")
        Text("This is basic Flow ${basicFlowValue.value}")
        Button(onClick = { viewModel.startProducingChannel() }) {
            Text("Start producing in channel")
        }
        Text("This is flow in channel ${channelFlowValue.value}")
        Button(onClick = { viewModel.closeChannel(Throwable("Something")) }) {
            Text("Close channel with throwable")
        }
    }
}