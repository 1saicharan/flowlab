package com.android.learning.flowlab.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.learning.flowlab.ui.viewmodel.MainViewModel1
import com.android.learning.flowlab.ui.viewmodel.MainViewModel2
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch

@Composable
fun Screen2(modifier: Modifier = Modifier, viewModel: MainViewModel2) {
    val combineState by viewModel.ui.collectAsState()
    val bufCount = remember { mutableStateOf(0) }
    val confCount = remember { mutableStateOf(0) }
    val latestCount = remember { mutableStateOf(0) }


    val bufLast = remember { mutableStateOf<Int?>(null) }
    val confLast = remember { mutableStateOf<Int?>(null) }
    val latestLast = remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(Unit) {

        launch {
            viewModel.stress
                .buffer()
                .collect { value ->
                    viewModel.heavyCpu(value)
                    bufLast.value = value
                    bufCount.value += 1
                }
        }


        launch {
            viewModel.stress
                .conflate()
                .collect { value ->
                    viewModel.heavyCpu(value)
                    confLast.value = value
                    confCount.value += 1
                }
        }


        launch {
            viewModel.stress.collectLatest { value ->
                viewModel.heavyCpu(value)
                latestLast.value = value
                latestCount.value += 1
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("This is Screen 2")
        Text("This is same counter as hot flow $combineState")
        Column {
            Text("buffer()")
            Text("processed: ${bufCount.value}")
            Text("last: ${bufLast.value ?: "-"}")
        }
        Column {
            Text("conflate()")
            Text("processed: ${confCount.value}")
            Text("last: ${confLast.value ?: "-"}")
        }
        Column {
            Text("collectLatest")
            Text("processed: ${latestCount.value}")
            Text("last: ${latestLast.value ?: "-"}")
        }
    }
}