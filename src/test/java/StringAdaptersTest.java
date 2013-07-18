import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.junit.Test;

import com.javadapters.AdapterFactory;
import com.javadapters.AdapterNotFoundException;
import com.javadapters.adapters.Adapter;
import com.javadapters.adapters.StringAdapters.StringToBooleanAdapter;
import com.javadapters.adapters.StringAdapters.StringToByteAdapter;
import com.javadapters.adapters.StringAdapters.StringToCharacterAdapter;


public class StringAdaptersTest {

   @Test
   public void stringToBooleanTest() {
      try {
         Adapter<String, Boolean> adapter1 =
               AdapterFactory.getAdapter(String.class, Boolean.class);
         assertTrue(adapter1 instanceof StringToBooleanAdapter);

         Adapter<String, Boolean> adapter2 =
               AdapterFactory.getAdapter(String.class, boolean.class);
         assertTrue(adapter2 instanceof StringToBooleanAdapter);

         assertTrue(adapter1.convert("true", Boolean.class));
         assertFalse(adapter1.convert("false", Boolean.class));

         assertTrue(adapter2.convert("true", Boolean.class));
         assertFalse(adapter2.convert("false", Boolean.class));

      } catch (AdapterNotFoundException e) {
         fail(e.getMessage());
      }
   }

   @Test
   public void stringToByteTest() {
      try {
         Adapter<String, Byte> adapter1 =
               AdapterFactory.getAdapter(String.class, Byte.class);
         assertTrue(adapter1 instanceof StringToByteAdapter);

         Adapter<String, Byte> adapter2 =
               AdapterFactory.getAdapter(String.class, byte.class);
         assertTrue(adapter2 instanceof StringToByteAdapter);

         assertTrue(adapter1.convert("5", Byte.class) == 5);
         assertTrue(adapter2.convert("5", Byte.class) == 5);

      } catch(AdapterNotFoundException e) {
         fail(e.getMessage());
      }
   }

   @Test
   public void stringToCharTest() {
      try {
         Adapter<String, Character> adapter1 =
               AdapterFactory.getAdapter(String.class, Character.class);
         assertTrue(adapter1 instanceof StringToCharacterAdapter);

         Adapter<String, Character> adapter2 =
               AdapterFactory.getAdapter(String.class, char.class);
         assertTrue(adapter2 instanceof StringToCharacterAdapter);

         assertTrue(adapter1.convert("Test", Character.class) == 'T');
         assertTrue(adapter1.convert("", Character.class) == null);
         assertTrue(adapter1.convert(null, Character.class) == null);
         assertTrue(adapter2.convert("Test", Character.class) == 'T');
         assertTrue(adapter2.convert("", Character.class) == null);
         assertTrue(adapter2.convert(null, Character.class) == null);
      } catch (AdapterNotFoundException e) {
         fail(e.getMessage());
      }
   }

   @Test
   public void stringToDateTest() {
      try {
         Adapter<String, Date> adapter =
               AdapterFactory.getAdapter(String.class, Date.class);
         Date d1 = adapter.convert("07/17/2013 12:00:00", Date.class);
         assertNotNull(d1);
         Date d2 = adapter.convert("07 JUL 2013 03:30:00", Date.class);
         assertNotNull(d2);
      } catch (AdapterNotFoundException e) {
         fail(e.getMessage());
      }
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Test
   public void stringToNumberTest() {
      try {
         Class<?>[] array1 = new Class<?>[] {
               Double.class, Float.class, Integer.class, Long.class};
         for(int i = 0; i < array1.length; ++i) {
            Adapter adapter =
                  AdapterFactory.getAdapter(String.class, array1[i]);
            Number n = (Number)adapter.convert("100", array1[i]);
            Method compareMethod = array1[i].getMethod("compareTo", array1[i]);
            Method valueOfMethod = array1[i].getMethod("valueOf", String.class);
            Number testValue = (Number)valueOfMethod.invoke(n, "100");
            int result = (int)compareMethod.invoke(n, testValue);
            assertTrue(result == 0);
         }

         Class<?>[] array2 = new Class<?>[] {
               double.class, float.class, int.class, long.class};
         for(int i = 0; i < array2.length; ++i) {
            Adapter adapter =
                  AdapterFactory.getAdapter(String.class, array2[i]);
            Number n = (Number)adapter.convert("100", array1[i]);
            Method compareMethod = array1[i].getMethod("compareTo", array1[i]);
            Method valueOfMethod = array1[i].getMethod("valueOf", String.class);
            Number testValue = (Number)valueOfMethod.invoke(n, "100");
            int result = (int)compareMethod.invoke(n, testValue);
            assertTrue(result == 0);
         }
      } catch (AdapterNotFoundException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (NoSuchMethodException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (SecurityException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (InvocationTargetException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (IllegalAccessException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }



}
