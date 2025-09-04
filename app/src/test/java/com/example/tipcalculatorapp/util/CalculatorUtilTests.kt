package com.example.tipcalculatorapp.util

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorUtilsTest {

    @Test
    fun calcTotalTip_validBillAndTip_returnsCorrectTip() {
        val result = calcTotalTip(200.0, 15)
        assertEquals(30.0, result, 0.001)
    }

    @Test
    fun calcTotalTip_negativeBill_returnsZero() {
        val result = calcTotalTip(-50.0, 10)
        assertEquals(0.0, result, 0.001)
    }

    @Test
    fun calculateTotalPerPerson_withTip_multiplePeople() {
        val result = calculateTotalPerPerson(100.0, 4, 20)
        // (100 + 20) / 4 = 30
        assertEquals(30.0, result, 0.001)
    }

    @Test
    fun calculateTotalPerPerson_invalidSplit_defaultsToOne() {
        val result = calculateTotalPerPerson(100.0, 0, 10)
        // (100 + 10) / 1 = 110
        assertEquals(110.0, result, 0.001)
    }
}
