package com.javadapters;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.javadapters.adapters.Adapter;
import com.javadapters.adapters.SameTypeAdapter;
import com.javadapters.adapters.StringAdapters;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AdapterFactory {

   /**
    * Add an adapter type to the current supported set of adapters.
    * If an adapter for the specified types already exists then will overwrite
    * it.
    * @param fromType The type to convert from
    * @param toType The type to convert to
    * @param adapter The adapter to add
    */
   public static <F,T> void addAdapter(Class<F> fromType, Class<T> toType,
         Adapter<F,T> adapter) {
      sAdapters.put(new ClassPair<F,T>(fromType, toType), adapter);
   }

   /**
    * Gets the adapter to convert from fromType to toType
    * @param fromType The type to convert from
    * @param toType The type to convert to
    * @return The adapter for the given types
    * @throws AdapterNotFoundException
    */
   public static <F,T> Adapter<F,T> getAdapter(Class<F> fromType,
         Class<T> toType) throws AdapterNotFoundException {
      if(fromType == null || toType == null) {
         throw getException(fromType, toType);
      }

      //If the types passed in are primitive, convert to wrapped types to find
      //the appropriate adapter
      fromType = getWrapper(fromType);
      toType = getWrapper(toType);

      if(fromType.equals(toType)) {
         return sSameTypeAdapter;
      }

      Adapter<F,T> adapter = (Adapter<F,T>)sAdapters.get(
            new ClassPair<F,T>(fromType, toType));
      if(adapter == null) {
         //No adapter found...check some special cases

         //If either type is an enum then check for the generic enum case
         Class f = fromType.isEnum() ? Enum.class : fromType;
         Class t = toType.isEnum() ? Enum.class : toType;

         adapter = (Adapter<F,T>)sAdapters.get(new ClassPair<F,T>(f, t));
         if(adapter == null) {
            throw getException(fromType, toType);
         }
      }

      return adapter;
   }

   /**
    * Builds and returns the exception if the adapter was not found
    * @param fromType
    * @param toType
    * @return An {@link AdapterNotFoundException}
    */
   private static <F,T> AdapterNotFoundException getException(Class<F> fromType,
         Class<T> toType) {
      StringBuilder sb = new StringBuilder("An adapter for the types <");
      sb.append(fromType).append(", ").append(toType);
      sb.append("> was not found!");
      return new AdapterNotFoundException(sb.toString());
   }

   /**
    * Helper method to get the wrapper type for the given type if it is a
    * primitive type. If not a primitive type then just returns the type.
    * @param type The type to get the wrapper for
    * @return The wrapper class if primitive, otherwise just the passed in type
    */
   public static <T> Class<T> getWrapper(Class<T> type) {
      Class<T> wrapped = (Class<T>) sPrimitiveToWrapperMap.get(type);
      return (wrapped == null) ? type : wrapped;
   }

   /** Simple adapter if the types are the same **/
   private static final Adapter sSameTypeAdapter = new SameTypeAdapter();

   /** Mapping from primitive type to wrapper type **/
   private static final Map<Class<?>, Class<?>> sPrimitiveToWrapperMap =
         new HashMap<Class<?>, Class<?>>();

   /** Cache of the current adapters **/
   private static final Map<ClassPair<?, ?>, Adapter<?,?>> sAdapters =
         new ConcurrentHashMap<>();

   static {
      //Add the default adapters
      sAdapters.putAll(StringAdapters.getAllAdapters());

      sPrimitiveToWrapperMap.put(boolean.class, Boolean.class);
      sPrimitiveToWrapperMap.put(byte.class, Byte.class);
      sPrimitiveToWrapperMap.put(char.class, Character.class);
      sPrimitiveToWrapperMap.put(double.class, Double.class);
      sPrimitiveToWrapperMap.put(float.class, Float.class);
      sPrimitiveToWrapperMap.put(int.class, Integer.class);
      sPrimitiveToWrapperMap.put(long.class, Long.class);
      sPrimitiveToWrapperMap.put(short.class, Short.class);
      sPrimitiveToWrapperMap.put(void.class, Void.class);
   }

   public static void main(String[] args) {
      try {
         System.out.println(AdapterFactory.getAdapter(String.class, Boolean.class));
         System.out.println(AdapterFactory.getAdapter(String.class, boolean.class));
         System.out.println(AdapterFactory.getAdapter(String.class, Boolean.class).convert(
               "true", Boolean.class));
         System.out.println(AdapterFactory.getAdapter(String.class, Boolean.class).convert(
               "false", Boolean.class));
         Adapter<String, ElementType> etAdapter =
               AdapterFactory.getAdapter(String.class, ElementType.class);
         System.out.println(etAdapter.convert("TYPE", ElementType.class));
      } catch (AdapterNotFoundException e) {
         System.out.println(e.getMessage());
      }
   }
}
