package com.example.mynotebook

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NoteDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
     fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java).build()
        dao = db.getNotesDao()

    }

    @After
     fun closedb() {
        db.close()
    }

    @Test
    fun writeAndReadNote() = runBlockingTest {

        val note = Note(1,"Monali Lambhade", "Metting link", "01 Jul,2022-21:12")
        dao.insert(note)
        val notes = dao.getAllNotes().getOrAwaitValue()

        Truth.assertThat(notes).contains(note)



    }
    @Test
    fun deleteNote() = runBlockingTest {

        val note = Note(1,"Monali Lambhade", "Metting link", "01 Jul,2022-21:12")
        dao.insert(note)
        dao.delete(note)
        val notes = dao.getAllNotes().getOrAwaitValue()

        Truth.assertThat(notes).doesNotContain(note)



    }
}