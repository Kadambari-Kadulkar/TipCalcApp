package com.example.tipcalculatorapp.util

fun calcTotalTip(totalBill: Double, tipPercent: Int): Double {
    return  if(totalBill > 0 && totalBill.toString().isNotEmpty()) (totalBill * tipPercent) / 100 else 0.0

}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercent: Int
):Double{
    val bill: Double = calcTotalTip(totalBill, tipPercent) + totalBill
    return (bill / splitBy)
}