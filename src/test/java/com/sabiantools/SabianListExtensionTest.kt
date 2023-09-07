package com.sabiantools

import com.sabiantools.extensions.filterNotNull
import org.junit.Test

class SabianListExtensionTest {

    @Test
    fun test_filter_not_null_is_accurate() {
        val list = listOf("Test", "Another Test", null, "Another test")
        val valid = list.filterNotNull { it }
        assert(valid.size == 3)
    }
}