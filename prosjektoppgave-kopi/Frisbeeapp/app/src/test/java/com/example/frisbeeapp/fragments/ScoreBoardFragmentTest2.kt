package com.example.frisbeeapp.fragments

import org.junit.Assert.*
import org.junit.Test

class ScoreBoardFragmentTest2 {

    private val scoreboardObject: ScoreBoardFragment = ScoreBoardFragment()
    private val listRegular: MutableList<Int> = mutableListOf(1, 1, 1, 1, 1, 1, 1, 1)
    private val listLarge: MutableList<Int> = mutableListOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
    private val listSmall: MutableList<Int> = mutableListOf(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    private val listZero: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0)
    private val listWithOneZero: MutableList<Int> = mutableListOf(0,1,2,3,4,5)

    @Test
    fun addition_isCorrect() {
        assertEquals(8, scoreboardObject.addTotal(listRegular))
    }

    @Test
    fun addition_isWrong() {
        assertNotEquals(7, scoreboardObject.addTotal(listRegular))
    }

    //Test edge cases and overflow
    //Den egentlige verdien er 6442450941, men tallet blir ikke høyere enn MAX_VALUE = 2147483647
    @Test
    fun addition_largeNumbersIsWrong(){
        assertNotEquals(6442450941, scoreboardObject.addTotal(listLarge))
    }

    @Test
    fun addition_largeNumbersIsCorrect(){
        assertNotEquals(Int.MAX_VALUE, scoreboardObject.addTotal(listLarge))
    }

    //Test edge cases and overflow
    //Den egentlige verdien er -6442450944, men tallet blir ikke lavere enn MIN_VALUE = -2147483647
    @Test
    fun addition_smallNumbersIsWrong(){
        assertNotEquals(-6442450944, scoreboardObject.addTotal(listSmall))
    }
    @Test
    fun addition_smallNumbersIsCorrect(){
        assertEquals(Int.MIN_VALUE, scoreboardObject.addTotal((listSmall)))
    }

    //Test with only zero
    @Test
    fun addition_withZeroIsCorrect(){
        assertEquals(0, scoreboardObject.addTotal(listZero) )
    }

    @Test
    fun addition_withZeroIsWrong(){
        assertNotEquals(10, scoreboardObject.addTotal(listZero))
    }

    //Test with one zero
    @Test
    fun addition_withOneZeroIsCorrect(){
        assertEquals(15, scoreboardObject.addTotal(listWithOneZero) )
    }

    @Test
    fun addition_withOneZeroIsWrong(){
        assertNotEquals(0, scoreboardObject.addTotal(listWithOneZero) )
    }

    @Test
    fun calculate_isCorrect() {
        assertEquals(0, scoreboardObject.calculateScore(54, 54))
    }

    @Test
    fun calculate_isWrong() {
        assertNotEquals(5, scoreboardObject.calculateScore(54, 54))
    }

    @Test
    fun calculate_underPar() {
        assertEquals(-4, scoreboardObject.calculateScore(50, 54))
    }

    @Test
    fun calculate_overPar() {
        assertEquals(4, scoreboardObject.calculateScore(58, 54))
    }

    //Test with only zero
    @Test
    fun calculate_withZero() {
        assertEquals(0, scoreboardObject.calculateScore(0, 0))
    }

    //Test edge cases
    @Test
    fun calculate_maxValueTotal() {
        assertEquals(Int.MAX_VALUE, scoreboardObject.calculateScore(Int.MAX_VALUE, 0))
    }

    //Test edge cases
    //Appikasjonen gir ikke mulighet til å skrive inn negative tall
    //Dersom man skriver et negativt tall vil _ikke_ appliksjonen krasje
    @Test
    fun calculate_minValueTotal(){
        assertEquals(Int.MIN_VALUE, scoreboardObject.calculateScore(Int.MIN_VALUE, 0))
    }
}


