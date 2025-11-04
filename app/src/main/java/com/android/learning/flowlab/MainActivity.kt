package com.android.learning.flowlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.learning.flowlab.ui.NavigationRoute
import com.android.learning.flowlab.ui.screens.Home
import com.android.learning.flowlab.ui.screens.Screen1
import com.android.learning.flowlab.ui.screens.Screen2
import com.android.learning.flowlab.ui.screens.Screen3
import com.android.learning.flowlab.ui.theme.FlowlabTheme
import com.android.learning.flowlab.ui.viewmodel.MainViewModel1
import com.android.learning.flowlab.ui.viewmodel.MainViewModel2
import com.android.learning.flowlab.ui.viewmodel.MainViewModel3

class MainActivity : ComponentActivity() {

    val viewModel1: MainViewModel1 by viewModels()
    val viewModel2: MainViewModel2 by viewModels()

    val viewModel3: MainViewModel3 by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowlabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        FlowLabApp(modifier = Modifier.padding(innerPadding),viewModel1,viewModel2,viewModel3)
                }
            }
        }
    }
}

@Composable
fun FlowLabApp(modifier: Modifier,viewModel1: MainViewModel1,viewModel2: MainViewModel2,viewModel3: MainViewModel3){
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = NavigationRoute.HOME.value) {
        composable(NavigationRoute.HOME.value){ Home(modifier,nav) }
        composable(NavigationRoute.SCREEN1.value){ Screen1(modifier,viewModel1) }
        composable(NavigationRoute.SCREEN2.value) { Screen2(modifier,viewModel2) }
        composable(NavigationRoute.SCREEN3.value) { Screen3(modifier,viewModel3) }
    }

}