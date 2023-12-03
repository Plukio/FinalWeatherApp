package com.example.finalweatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.example.finalweatherapp.model.data.User
import com.example.finalweatherapp.model.database.UserDao
import com.example.finalweatherapp.model.database.UserDataActivityDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDataTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: UserDataActivityDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDataActivityDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNewUserTest() = runBlockingTest {
        val user = User("1234567", "test")
        dao.addUser(user)

        val allUser = dao.allUsers().getOrAwaitValue()
        assertThat(allUser).contains(user)

    }

    @Test
    fun findUserTest() = runBlockingTest {
        val user = User("1234567", "test")
        val user2 = User("s1234567", "test2")
        dao.addUser(user)
        dao.addUser(user2)

        val userFound = dao.findUser("1234567").getOrAwaitValue()
        assertThat(userFound).contains(user)
    }



    @Test
    fun allUserTest() = runBlockingTest {
        val user1 = User("a1234567", "test1")
        val user2 = User("s1234567", "test2")
        val user3 = User("d1234567", "test3")
        dao.addUser(user1)
        dao.addUser(user2)
        dao.addUser(user3)

        val totalUser = dao.allUsers().getOrAwaitValue().size

        assertThat(totalUser).isEqualTo(3)
    }
}