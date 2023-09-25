package com.sabiantools

import com.sabiantools.utilities.pipeline.SabianPipeline
import org.junit.Test

class SabianPipelineTest {
    @Test
    fun test_pipeline_works() {
        val original = 100
        val pipeline = SabianPipeline<Int, Int> {
            val final = it + 1
            assert(final == 101)
            final
        }.addHandler {
            val next = it + 1
            assert(next == 102)
            next
        }.addHandler {
            val next = it + 1
            assert(next == 103)
            next
        }
        val final = pipeline.execute(original)
        assert(final == 103)
    }
}