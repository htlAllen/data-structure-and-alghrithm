import java.util.HashSet;
import java.lang.Thread;
import java.lang.ThreadGroup;
import java.util.Set;
import java.util.concurrent.TimeUnit;
public class Entry{
    public static void main(String[] args){

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getState());
            System.out.println(t.getName() + "occur");
            e.printStackTrace();
        });

        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(1/0);
        }).start();

        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
