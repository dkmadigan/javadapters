package com.javadapters.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.javadapters.AdapterFactory;
import com.javadapters.ClassPair;

/**
 * Set of default string adapters to convert between different java built in
 * types. These are added by default to the {@link AdapterFactory}.
 */
public abstract class StringAdapters {

   /**
    * @return A list of all {@link StringAdapters}
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public static Map<ClassPair<String, ?>, Adapter<String, ?>> getAllAdapters() {
      Map<ClassPair<String, ?>, Adapter<String, ?>> adapters =
            new HashMap<ClassPair<String, ?>, Adapter<String, ?>>();
      adapters.put(new ClassPair<String, Boolean>(String.class, Boolean.class),
            new StringToBooleanAdapter());
      adapters.put(new ClassPair<String, Byte>(String.class, Byte.class),
            new StringToByteAdapter());
      adapters.put(new ClassPair<String, Character>(String.class, Character.class),
            new StringToCharacterAdapter());
      adapters.put(new ClassPair<String, Date>(String.class, Date.class),
            new StringToDateAdapter());
      adapters.put(new ClassPair<String, Double>(String.class, Double.class),
            new StringToDoubleAdapter());
      adapters.put(new ClassPair<String, Enum>(String.class, Enum.class),
            new StringToEnumAdapter());
      adapters.put(new ClassPair<String, Float>(String.class, Float.class),
            new StringToFloatAdapter());
      adapters.put(new ClassPair<String, Integer>(String.class, Integer.class),
            new StringToIntegerAdapter());
      adapters.put(new ClassPair<String, Long>(String.class, Long.class),
            new StringToLongAdapter());
      adapters.put(new ClassPair<String, Short>(String.class, Short.class),
            new StringToShortAdapter());
      return adapters;
   }

   /**
    * String to Boolean adapter
    */
   public static class StringToBooleanAdapter implements Adapter<String, Boolean> {
      @Override
      public Boolean convert(String from, Class<Boolean> clazz) {
         return Boolean.valueOf(from);
      }
   }

   /**
    * String to Byte adapter
    */
   public static class StringToByteAdapter implements Adapter<String, Byte> {
      @Override
      public Byte convert(String from, Class<Byte> clazz) {
         return Byte.valueOf(from);
      }
   }

   /**
    * String to Character adapter
    */
   public static class StringToCharacterAdapter implements Adapter<String, Character> {
      @Override
      public Character convert(String from, Class<Character> clazz) {
         return ((from == null || from.isEmpty()) ? null : from.charAt(0));
      }
   }

   /**
    * String to Date adapter. Tries to convert a string to a date using some
    * default set of date formats. Logic and regexps in here adapted from DateUtil
    * by BalusC.
    * @see
    * @link http://balusc.blogspot.com/2007/09/dateutil.html
    */
   public static class StringToDateAdapter implements Adapter<String, Date> {
      @Override
      public Date convert(String from, Class<Date> clazz) {
         Date date = null;
         String dateFormat = determineDateFormat(from);
         if(dateFormat != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            simpleDateFormat.setLenient(false); // Don't automatically convert invalid date\
            try {
               date = simpleDateFormat.parse(from);
            } catch (ParseException e) {
               date = null;
            }
         }
         return date;
      }

      /**
       * Determine SimpleDateFormat pattern matching with the given date string.
       * Returns null if format is unknown.
       * @param dateString The date string to determine the SimpleDateFormat
       * @return The matching SimpleDateFormat pattern, or null if unknown
       * @see SimpleDateFormat
       */
      private String determineDateFormat(String dateString) {
         for (String regexp : sDateFormatRegexps.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
               return sDateFormatRegexps.get(regexp);
            }
         }
         return null; // Unknown format.
      }

      /** Set of regexps for date formats **/
      private static final Map<String, String> sDateFormatRegexps =
            new HashMap<String, String>();
      static {
         sDateFormatRegexps.put("^\\d{8}$", "yyyyMMdd");
         sDateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
         sDateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
         sDateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
         sDateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
         sDateFormatRegexps.put("^\\d{12}$", "yyyyMMddHHmm");
         sDateFormatRegexps.put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
         sDateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
         sDateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
         sDateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
         sDateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
         sDateFormatRegexps.put("^\\d{14}$", "yyyyMMddHHmmss");
         sDateFormatRegexps.put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
         sDateFormatRegexps.put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
         sDateFormatRegexps.put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
         sDateFormatRegexps.put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
         sDateFormatRegexps.put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
         sDateFormatRegexps.put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
      }
   }

   /**
    * String to Double adapter
    */
   public static class StringToDoubleAdapter implements Adapter<String, Double> {
      @Override
      public Double convert(String from, Class<Double> clazz) {
         return Double.valueOf(from);
      }
   }

   /**
    * Generic String to Enum adapter
    * @param <E>
    */
   public static class StringToEnumAdapter<E extends Enum<E>> implements Adapter<String, E> {
      @Override
      public E convert(String from, Class<E> clazz) {
         return Enum.valueOf(clazz, from);
      }
   }

   /**
    * String to Float adapter
    */
   public static class StringToFloatAdapter implements Adapter<String, Float> {
      @Override
      public Float convert(String from, Class<Float> clazz) {
         return Float.valueOf(from);
      }
   }

   /**
    * String to Integer adapter
    */
   public static class StringToIntegerAdapter implements Adapter<String, Integer> {
      @Override
      public Integer convert(String from, Class<Integer> clazz) {
         return Integer.valueOf(from);
      }
   }

   /**
    * String to Long adapter
    */
   public static class StringToLongAdapter implements Adapter<String, Long> {
      @Override
      public Long convert(String from, Class<Long> clazz) {
         return Long.valueOf(from);
      }
   }

   /**
    * String to Short adapter
    */
   public static class StringToShortAdapter implements Adapter<String, Short> {
      @Override
      public Short convert(String from, Class<Short> clazz) {
         return Short.valueOf(from);
      }
   }
}
