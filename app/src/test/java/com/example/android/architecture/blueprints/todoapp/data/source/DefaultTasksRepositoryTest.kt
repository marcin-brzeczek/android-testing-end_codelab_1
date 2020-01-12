package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteSource: FakeDataSource
    private lateinit var tasksLocalSource: FakeDataSource


    // Class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun setUp() {
        tasksRemoteSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalSource = FakeDataSource(localTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(tasksRemoteSource, tasksLocalSource, Dispatchers.Unconfined)
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runBlockingTest {

        //Given

        //When
        val tasks = tasksRepository.getTasks(true) as Result.Success

        //Then
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}