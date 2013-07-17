package com.javadapters;

public class AdapterNotFoundException extends Exception {

   private static final long serialVersionUID = 7694079385547556557L;

   public AdapterNotFoundException() {
   }

   public AdapterNotFoundException(String message) {
      super(message);
   }

   public AdapterNotFoundException(Throwable cause) {
      super(cause);
   }

   public AdapterNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public AdapterNotFoundException(String message, Throwable cause,
         boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
