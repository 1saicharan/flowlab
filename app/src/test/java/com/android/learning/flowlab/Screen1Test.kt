package com.android.learning.flowlab

import app.cash.turbine.test
import com.android.learning.flowlab.ui.viewmodel.MainViewModel1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

class Screen1Test {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun stateInTest() = runTest  {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val vm = MainViewModel1(dispatcher = testDispatcher)
        Dispatchers.setMain(testDispatcher)
        vm.state.test {
            assert(0==awaitItem())
            testScheduler.advanceTimeBy(310)
            runCurrent()
            assert(1==awaitItem())
        }
        Dispatchers.resetMain()
    }
}