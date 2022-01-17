package com.sabiantools.utilities.pipeline;

public class SabianPipeline<I, O> {

    private final ISabianPipeHandler<I, O> currentHandler;

    SabianPipeline(ISabianPipeHandler<I, O> currentHandler) {
        this.currentHandler = currentHandler;
    }

    <K> SabianPipeline<I, K> addHandler(ISabianPipeHandler<O, K> newHandler) {
        return new SabianPipeline<>(input -> newHandler.process(currentHandler.process(input)));
    }

    O execute(I input) {
        return currentHandler.process(input);
    }
}