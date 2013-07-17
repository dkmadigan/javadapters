package com.javadapters;

/**
 * Simple wrapper class for the adapter types
 * @param <F> The type converting from
 * @param <T> The type converting to
 */
public class ClassPair<F,T> {

   /**
    * Constructor
    * @param from From class type
    * @param to To class type
    */
   public ClassPair(Class<F> from, Class<T> to) {
      fromClass = from;
      toClass = to;
   }

   @SuppressWarnings({ "rawtypes" })
   @Override
   public boolean equals(Object obj) {
      boolean equals = false;
      if(obj instanceof ClassPair) {
         ClassPair other = (ClassPair)obj;
         equals = (other.fromClass.equals(fromClass) &&
               other.toClass.equals(toClass));
      }
      return equals;
   }

   @Override
   public int hashCode() {
      return (fromClass.hashCode() + 0xDeadBeef) ^ toClass.hashCode();
   }

   /** from class type **/
   private final Class<F> fromClass;
   /** to class type **/
   private final Class<T> toClass;
}
