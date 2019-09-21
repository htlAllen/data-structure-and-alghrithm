import org.omg.IOP.ENCODING_CDR_ENCAPS;


import java.lang.Thread;
import java.util.concurrent.TimeUnit;

public class Entry{
    public static void main(String[] args){
        Test x = new Test();
        Thread t1 = new Thread(Test::t1);
        Thread t2 = new Thread(x::t2);
        t1.start();
        t2.start();
    }
}
class Test{
    public static synchronized void t1(){
        try{
            System.out.println("i am method-1");
            TimeUnit.MINUTES.sleep(10);
            System.out.println("i am method-1,i am will over");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void t2(){
        synchronized (this){
            try{
                System.out.println("i am method-2");
                TimeUnit.MINUTES.sleep(10);
                System.out.println("i am method-2,i am will over");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}