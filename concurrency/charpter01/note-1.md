# 快速认识线程
## 1.线程的生命周期
见图
## 2.Thread类的start方法
在调用start方法的时候，首先会处理线程相关的逻辑事务，比如将线程从new状态转变为runnable状态，将线程加入到就绪队列中
然后在调用JNI的start0()，该方法先检查是否有runnable对象，如果有则调用该对象的run方法，如果没有则会调用自己所重写的run方法
## 3以下三种创建线程的联系和区别
```java
package yangyi.concurrency.charpter01;

public class TryConcurency{
    public static void main(String[] args){
        TicketWindow1 t11 = new TicketWindow1("类型1窗口1");
        TicketWindow1 t12 = new TicketWindow1("类型1窗口2");
        t11.start();
        t12.start();


        TicketWindow2 t21 = new TicketWindow2("类型2窗口1");
        TicketWindow2 t22 = new TicketWindow2("类型2窗口2");
        t21.start();
        t22.start();

        TicketWindow3 task = new TicketWindow3();
        Thread t31 = new Thread(task,"类型3窗口1");
        Thread t32 = new Thread(task,"类型3窗口2");
        t31.start();
        t32.start();
    }
}
class TicketWindow1 extends Thread{
    String name;
    public TicketWindow1(String name){
        this.name = name;
    }
    private int index = 1;
    private final int MAX = 50;
    @Override
    public void run(){
        while(index <= MAX){
            System.out.println(name + "卖：" + (index++));
        }
    }
}

class TicketWindow2 extends Thread{
    private String name;
    private static int index = 1;
    private final static int MAX = 50;
    public TicketWindow2(String name){
        this.name = name;
    }
    @Override
    public void run(){
        while(index <= MAX){
            System.out.println(name + "卖：" + (index++));
        }
    }
}
class TicketWindow3 implements Runnable{
    private int index = 1;
    private final static int MAX = 50;
    @Override
    public void run(){
        while(index <= MAX){
            System.out.println(Thread.currentThread() + "卖：" + (index++));
        }
    }
}
```
