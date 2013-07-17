package com.javadapters.adapters;

/**
 * Simple adapter for use when the types are the same.
 * @param <T>
 */
public class SameTypeAdapter<T> implements Adapter<T, T> {

   /*
    * (non-Javadoc)
    * @see com.javadapters.adapters.Adapter#convert(java.lang.Object, java.lang.Class)
    */
   @Override
   public T convert(T from, Class<T> clazz) {
      return from;
   };
}
