package com.sabiantools.utilities.pipeline;

import androidx.annotation.NonNull;

public interface ISabianPipeHandler<I, O> {
    @NonNull
    O process(@NonNull I input);
}