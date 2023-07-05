package com.techhub.converter;

public interface Converter<T, S> {
    T convert(S source);
}
