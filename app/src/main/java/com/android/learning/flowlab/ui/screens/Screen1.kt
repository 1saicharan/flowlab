package com.android.learning.flowlab.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.android.learning.flowlab.ui.viewmodel.MainViewModel1
import kotlinx.coroutines.launch


@Composable
fun Screen1(modifier: Modifier = Modifier, viewModel: MainViewModel1) {
    val counterHot by viewModel.state.collectAsState()
    val counterCold by viewModel.counter.collectAsState(2)
    val scope = rememberCoroutineScope()
    val pings = remember { mutableStateListOf<String>() }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("This is Screen 1")
        Text("This is counter as hot flow $counterHot")
        Text("This is counter as cold flow $counterCold")
        Button(onClick = {
            scope.launch { viewModel.ping("This is ping") }
        }) { Text("Ping") }
        LaunchedEffect(Unit) {
            viewModel.events.collect { pings.add(it) }
        }
        pings.forEach { Text("Event $it") }
    }

}