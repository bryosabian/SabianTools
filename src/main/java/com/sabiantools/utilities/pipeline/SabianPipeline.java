package com.sabiantools.utilities.pipeline;

/**
 * @param <I>
 * @param <O>
 * @see <a href="https://java-design-patterns.com/patterns/pipeline/#explanation">See design pattern here</a>
 */
public class SabianPipeline<I, O> {

    private final ISabianPipeHandler<I, O> currentHandler;

    public SabianPipeline(ISabianPipeHandler<I, O> currentHandler) {
        this.currentHandler = currentHandler;
    }

    public <K> SabianPipeline<I, K> addHandler(ISabianPipeHandler<O, K> newHandler) {
        return new SabianPipeline<>(input -> newHandler.process(currentHandler.process(input)));
    }

    public O execute(I input) {
        return currentHandler.process(input);
    }
}