package com.example.frisbeeapp.fragments

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith (JUnit4::class)
class ScoreBoardFragmentTest{

    private val scoreboardObject = ScoreBoardFragment()
    private val listInt: MutableList<Int> = mutableListOf(1,1,1,1,1,1,1,1)

    @Test
    fun addition_isCorrect() {
        //val actual = scoreboardObject.addNumbers(listInt)
        assertEquals(8, scoreboardObject.showNumbers(listInt))
    }

}