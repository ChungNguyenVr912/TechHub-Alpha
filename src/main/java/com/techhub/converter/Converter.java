package com.techhub.converter;

import java.util.List;

public interface Converter<S, T> {
    T convert(S source);
    List<T> convert(List<S> sourceList);
    S revert(T source);
    List<S> revert(List<T> sourceList);
}
