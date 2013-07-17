package com.javadapters.adapters;

/**
 * Adapter interface used to convert values of one type to another type.
 *
 * @param <F> From type
 * @param <T> To type
 */
public interface Adapter<F,T> {

   /**
    * Converts from type F to T
    * @param from The value to convert from
    * @param clazz The class type of T
    * @return T value
    */
   public T convert(F from, Class<T> clazz);
}
