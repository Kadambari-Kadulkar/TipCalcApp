package com.example.tipcalculatorapp.util

fun calcTotalTip(totalBill: Double, tipPercent: Int): Double {
    return  if(totalBill > 0 && totalBill.toString().isNotEmpty()) (totalBill * tipPercent) / 100 else 0.0

}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercent: Int
):Double{
    if (totalBill <= 0) return 0.0  // no bill, nothing to split

    val safeSplitBy = if (splitBy <= 0) 1 else splitBy  // fallback to 1

    val bill: Double = calcTotalTip(totalBill, tipPercent) + totalBill
    return bill / safeSplitBy
}