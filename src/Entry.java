import org.omg.IOP.ENCODING_CDR_ENCAPS;


import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Period;
import java.util.concurrent.TimeUnit;

public class Entry{
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class c = person.class;
        Object obj = null;
        obj = c.newInstance();
        Method[] methods = c.getDeclaredMethods();
        for(Method method: methods){
            Class[] types = method.getParameterTypes();
            if(types[0] == String.class){
                method.invoke(obj, "allen");
            }
            if(types[0] == int.class){
                method.invoke(obj, 11);
            }
        }
        System.out.println("aaaa");
    }
}
class person{
    String name;
    int age;
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
}