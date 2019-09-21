# Hook线程以及捕获线程的异常
### Hook**线程**
当jvm进程退出的时候，并且hook线程收到退出信号（有时候强制退出，hook则收不到退出信号）的时候，hook线程被执行，其通常用来做一些收尾的工作（如关闭文件句柄，socket连接等）
> jvm进程在以下两种情况下会退出
> 1. jvm进程中没有非守护线程
> 2. 收到终止信号
```java
public class Entry{
    public static void main(String[] args){
        // jvm 要进程退出的时候， hook线程启动并且执行 
        
        // 获取当前的Runtime对象
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(()->{
            System.out.println("process will shutdown");
            try{
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("process shutdown");
        }));
        
        
        try{
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        // main线程结束， jvm进程即将结束，hook线程被执行
        System.out.println("main will end");
    }
}
```
> 思考：如何用hook线程来防止程序被重复启动


### 关于线程的异常
##### 1. check异常
线程的运行单元中**不允许**抛出check异常,因为线程运行在独立的上下文中，其派生线程无法捕捉其抛出的check异常。
##### 2. uncheck异常（运行时异常）
线程的运行单元中**允许**抛出uncheck异常,java提供了一种回调的机制来获得线程抛出的uncheck异常。即线程在运行时出现异常，会调用UncatchExceptionHandler接口
```java
public class Entry{
    public static void main(String[] args){
        Thread t1 = new Thread(){
            @Override
            public void run(){    // 不报错
                throw new RuntimeException("aaa");
            }
        };
        Thread t2 =new Thread(){
            @Override
            public void run() throws  Exception{  // 报错
                throw new Exception("aaa");
            }
        }
    }
}
```
##### 回调接口

- **setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)**

Set the handler invoked when this thread abruptly terminates due to an uncaught exception.

- **setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh)**

Set the default handler invoked when a thread abruptly terminates due to an uncaught exception, and no other handler has been defined for that thread.

- **getDefaultUncaughtExceptionHandler()**

Returns the default handler invoked when a thread abruptly terminates due to an uncaught exception.

- **getUncaughtExceptionHandler()**
Returns the default handler invoked when a thread abruptly terminates due to an uncaught exception. If the returned value is null, there is no default.
- **private void dispatchUncaughtException(Throwable e)** 

Dispatch an uncaught exception to the handler. This method is intended to be called only by the JVM.
```java
class Thread{
    @FunctionalInterface
    // Thread类下面有一个UncaughtExceptionHandler接口，里面包含了需要重写的uncaughtException函数
    public interface UncaughtExceptionHandler {
        void uncaughtException(Thread t, Throwable e);
    }
    // null unless explicitly set
    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;

    // null unless explicitly set
    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(
                new RuntimePermission("setDefaultUncaughtExceptionHandler")
                    );
        }

         defaultUncaughtExceptionHandler = eh;
     }

    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        checkAccess();
        uncaughtExceptionHandler = eh;
    }

    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return uncaughtExceptionHandler != null ?
            uncaughtExceptionHandler : group;
    }

    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler(){
        return defaultUncaughtExceptionHandler;
    }

    private void dispatchUncaughtException(Throwable e) {
        getUncaughtExceptionHandler().uncaughtException(this, e);
    }

}
public class Entry{
    public static void main(String[] args){
        // 该回调函数能够捕捉到异常信息。若在相关group中和当前Thread对象中没有设置回调函数，则将异常的信息重定位到System.err中
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
```