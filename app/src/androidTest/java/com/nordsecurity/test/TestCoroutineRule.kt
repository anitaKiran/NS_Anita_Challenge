package com.nordsecurity.test

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Created by Anita Kiran on 5/25/2022.
 */


@ExperimentalCoroutinesApi
class TestCoroutineRule (

    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
    ) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

        override fun starting(description: Description?) {
            super.starting(description)
            Dispatchers.setMain(dispatcher)
        }

        override fun finished(description: Description?) {
            super.finished(description)
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }

}