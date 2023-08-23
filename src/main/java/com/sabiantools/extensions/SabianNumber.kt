package com.sabiantools.extensions

fun Int.withSuffix(): String {

    val j = this % 10

    val k = this % 100

    if (j == 1 && k != 11) {
        return "%dst".format(this)
    }
    if (j == 2 && k != 12) {
        return "%dnd".format(this)
    }
    if (j == 3 && k != 13) {
        return "%drd".format(this)
    }
    return "%dth".format(this)

}