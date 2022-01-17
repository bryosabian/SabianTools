package com.sabiantools.extensions

import com.sabiantools.utilities.money.Money
import java.math.BigDecimal

operator fun Money.plus(decimal: BigDecimal) : Money {
    return this.plus(Money(decimal))
}
operator fun Money.minus(decimal: BigDecimal) : Money{
    return this.minus(Money(decimal))
}