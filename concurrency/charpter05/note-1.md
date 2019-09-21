# 线程同步与线程安全之sycchronized关键字
synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors: if an object is visible to more than one thread, all reads or writes to that object's variables are done through synchronized methods. (An important exception: final fields, which cannot be modified after the object is constructed, can be safely read through non-synchronized methods, once the object is constructed) This strategy is effective, but can present problems with liveness, as we'll see later in this lesson.

> 简而言之，若代码块或方法由synchronized修饰，则在代码块或者方法中对某一共享资源的修改，对于其他线程是可见的。

**synchronized的核心**：
1. synchronized提供了一种锁的机制来保证对共享变量的互斥访问，从而防止数据不一致问题
2. synchronized的moniter enter jvm指令保证数据是从内存中取的，而不是从cpu缓存中取；moniter exit jvm指令保证数据将被存储到内存中，而不是cpu缓存。从而解决cpu缓存问题

### 几个分析工具
##### jconsole
##### jstack
```java
import java.lang.*;
import java.util.concurrent.TimeUnit;
public class Mutex{
    private final static Object MUTEX = new Object();
    public void accessResource(){
        synchronized (MUTEX){
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        final Mutex mutex = new Mutex();
        Thread t1 = new Thread(mutex::accessResource);
        Thread t2 = new Thread(mutex::accessResource);
        Thread t3 = new Thread(mutex::accessResource);
        t1.start();
        t2.start();
        t3.start();
    }
}
//"Thread-2" #23 prio=5 os_prio=0 tid=0x00007fe36c191800 nid=0x368e waiting for monitor entry [0x00007fe332dec000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at Mutex.accessResource(Mutex.java:8)
//        - waiting to lock <0x000000067365daf8> (a java.lang.Object)
//        at Mutex$$Lambda$3/303563356.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//"Thread-1" #22 prio=5 os_prio=0 tid=0x00007fe36c18f800 nid=0x368d waiting for monitor entry [0x00007fe332eed000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//        at Mutex.accessResource(Mutex.java:8)
//        - waiting to lock <0x000000067365daf8> (a java.lang.Object)
//        at Mutex$$Lambda$2/1418481495.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//"Thread-0" #21 prio=5 os_prio=0 tid=0x00007fe36c18e000 nid=0x368c waiting on condition [0x00007fe332fee000]
//        java.lang.Thread.State: TIMED_WAITING (sleeping)
//        at java.lang.Thread.sleep(Native Method)
//        at java.lang.Thread.sleep(Thread.java:340)
//        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//        at Mutex.accessResource(Mutex.java:8)
//        - locked <0x000000067365daf8> (a java.lang.Object)
//        at Mutex$$Lambda$1/471910020.run(Unknown Source)
//        at java.lang.Thread.run(Thread.java:748)
//
//"Finalizer" #3 daemon prio=8 os_prio=0 tid=0x00007fe36c0b8800 nid=0x3679 in Object.wait() [0x00007fe3383d5000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x0000000673608ed8> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
//        - locked <0x0000000673608ed8> (a java.lang.ref.ReferenceQueue$Lock)
//        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
//        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:216)
//        
//"Reference Handler" #2 daemon prio=10 os_prio=0 tid=0x00007fe36c0b4000 nid=0x3678 in Object.wait() [0x00007fe3384d6000]
//        java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x0000000673606c00> (a java.lang.ref.Reference$Lock)
//        at java.lang.Object.wait(Object.java:502)
//        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
//        - locked <0x0000000673606c00> (a java.lang.ref.Reference$Lock)
//        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
```
##### javap
```java
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
//[root@compute1 javaproject]# javap -c Test.class
//Compiled from "Entry.java"
//class Test {
//    Test();
//    Code:
//            0: aload_0
//       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//            4: return
//
//    public static synchronized void t1();
//    Code:
//            0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//            3: ldc           #3                  // String i am method-1
//            5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            8: getstatic     #5                  // Field java/util/concurrent/TimeUnit.MINUTES:Ljava/util/concurrent/TimeUnit;
//            11: ldc2_w        #6                  // long 10l
//            14: invokevirtual #8                  // Method java/util/concurrent/TimeUnit.sleep:(J)V
//            17: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//            20: ldc           #9                  // String i am method-1,i am will over
//            22: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            25: goto          33
//            28: astore_0
//      29: aload_0
//      30: invokevirtual #11                 // Method java/lang/InterruptedException.printStackTrace:()V
//            33: return
//    Exception table:
//    from    to  target type
//           0    25    28   Class java/lang/InterruptedException
//
//    public void t2();
//    Code:
//            0: aload_0
//       1: dup
//       2: astore_1
//       3: monitorenter
//       4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//            7: ldc           #12                 // String i am method-2
//            9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            12: getstatic     #5                  // Field java/util/concurrent/TimeUnit.MINUTES:Ljava/util/concurrent/TimeUnit;
//            15: ldc2_w        #6                  // long 10l
//            18: invokevirtual #8                  // Method java/util/concurrent/TimeUnit.sleep:(J)V
//            21: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//            24: ldc           #13                 // String i am method-2,i am will over
//            26: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            29: goto          37
//            32: astore_2
//      33: aload_2
//      34: invokevirtual #11                 // Method java/lang/InterruptedException.printStackTrace:()V
//            37: aload_1
//      38: monitorexit
//      39: goto          47
//              42: astore_3
//      43: aload_1
//      44: monitorexit
//      45: aload_3
//      46: athrow
//      47: return
//    Exception table:
//    from    to  target type
//           4    29    32   Class java/lang/InterruptedException
//           4    39    42   any
//          42    45    42   any
//}
```

### This Monitor和Class Monitor
##### this monitor
当一个对象有两个synchronized方法时，这两个方法锁获得的monitor lock是同一个monitor lock，即this monitor
```java
public class Entry{
    public static void main(String[] args){
        Test x = new Test();
        Thread t1 = new Thread(x::t1);
        Thread t2 = new Thread(x::t2);
        t1.start();
        t2.start();
// 两者并未交替输出，而是串行输出
// i am method-1
// i am method-1,i am will over
// i am method-2
// i am method-2,i am will over
    }
}
class Test{
    public void t1(){
        synchronized (this){
            try{
                System.out.println("i am method-1");
                TimeUnit.SECONDS.sleep(100);
                System.out.println("i am method-1,i am will over");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
    public synchronized void t2(){
        try{
            System.out.println("i am method-2");
            TimeUnit.SECONDS.sleep(100);
            System.out.println("i am method-2,i am will over");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
```
##### class monitor
当一个对象有两个synchronized的静态方法时，这两个方法锁获得的monitor lock是同一个monitor lock，即class monitor
```java
public class Entry{
    public static void main(String[] args){
        Test x = new Test();
        Thread t1 = new Thread(Test::t1);
        Thread t2 = new Thread(Test::t2);
        t1.start();
        t2.start();
// 两者并未交替输出，而是串行输出
// i am method-1
// i am method-1,i am will over
// i am method-2
// i am method-2,i am will over
    }
}
class Test{
    public static synchronized void t1(){
        try{
            System.out.println("i am method-1");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("i am method-1,i am will over");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void t2(){
        synchronized (Test.class){
            try{
                System.out.println("i am method-2");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("i am method-2,i am will over");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
```
```java
public class Entry{
    public static void main(String[] args){
        Test x = new Test();
        Thread t1 = new Thread(Test::t1);
        Thread t2 = new Thread(x::t2);
        t1.start();
        t2.start();
// 两个方法交替输出，因为一个是this monitor的lock，一个是class monitor的lock
// i am method-1
// i am method-2
// i am method-1,i am will over
// i am method-2,i am will over
    }
}
class Test{
    public static synchronized void t1(){
        try{
            System.out.println("i am method-1");
            TimeUnit.SECONDS.sleep(10);
            System.out.println("i am method-1,i am will over");
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public synchronized void t2(){
            try{
                System.out.println("i am method-2");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("i am method-2,i am will over");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
    }
}
```
### 死锁

