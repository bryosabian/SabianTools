package com.sabiantools.utilities.pipeline;

interface ISabianPipeHandler<I, O> {
    O process(I input);
}