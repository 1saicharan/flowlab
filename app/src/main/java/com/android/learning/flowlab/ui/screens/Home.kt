package com.android.learning.flowlab.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.learning.flowlab.ui.NavigationRoute

@Composable
fun Home(modifier: Modifier = Modifier,nav: NavHostController){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("This is Home Screen")
        Button(onClick =  {nav.navigate(NavigationRoute.SCREEN1.value)}) {
            Text("goto Screen 1")
        }
        Button(onClick =  {nav.navigate(NavigationRoute.SCREEN2.value)}) {
            Text("goto Screen 2")
        }
        Button(onClick =  {nav.navigate(NavigationRoute.SCREEN3.value)}) {
            Text("goto Screen 3")
        }
    }
    BackHandler {
        nav.navigate(NavigationRoute.HOME.value)
    }

}