javadapters
===========

Adapter framework for converting between different Java types

Usage
=====
javadapters can be useful for converting between two different types, especially if the type being converted
to is not explicitly known at runtime. A good example of this is parsing some set of arbitrary data from an
external source and marshaling it into some known type using reflection.
<code>
<pre>
...
private Map<String, Method> methodMap = new HashMap<String, Method>();
static {
  methodMap.put("foo", SomeType.class.getMethod("setFoo", Integer.class));
  methodMap.put("bar", SomeType.class.getMethod("setBar", Boolean.class));
  methodMap.put("baz", SomeType.class.getMethod("setBaz", MyCoolType.class));
}

public SomeType parseData(Map<String, String> dataMap) {
  //Assume dataMap is a mapping from "foo"/"bar"/"baz" to 
  //the string representation of the data to set for that field
  SomeType st = new SomeType();
  for(Entry<String, String> entry : dataMap.entrySet()) {
    Method m = methodMap.get(entry.getKey());
    Class parmType = m.getParameterTypes()[0];  
    Adapter adapter = AdapterFactory.getAdapter(String.class, parmType);
    
    //Invokes the correct setter method and convert the string parm
    //into the appropriate type for that method
    m.invoke(st, adapter.convert(entry.getValue(), parmType));
  }
}
</pre>
</code>
In addition you can add your own adapter types using:
<code>
<pre>
AdapterFactory.addAdapter(String.class, SpecialClass.class, 
  new SpecialClassAdapter());
</pre>
</code>
