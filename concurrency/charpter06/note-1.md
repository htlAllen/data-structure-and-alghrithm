# ThreadGroup常见api解析
ThreadGroup对象提供了对一组thread管理的手段
### ThreadGroup构造函数
ThreadGroup函数用于创建ThreadGroup对象，创建时，需要传递父group参数，默认为当前运行的线程的group。
> 由构造函数得main group时所有新创建ThreadGroup的父group

```java
class ThreadGroup{
    public ThreadGroup(String name) {
        this(Thread.currentThread().getThreadGroup(), name);
    }

    public ThreadGroup(ThreadGroup parent, String name) {
        this(checkParentAccess(parent), parent, name);
    }

    private ThreadGroup(Void unused, ThreadGroup parent, String name) {
        this.name = name;
        // 继承父group的一些参数
        this.maxPriority = parent.maxPriority;
        this.daemon = parent.daemon;
        this.vmAllowSuspension = parent.vmAllowSuspension;
        this.parent = parent;
        parent.add(this);
    }
}

```
### ThreadGroup对象的常见方法
Method | description
------------ | -------------
public int activeCount() | Returns an estimate of the number of active threads in this thread group and its subgroups.
public int activeGroupCount() | Returns an estimate of the number of active groups in this thread group and its subgroups.
public final int getMaxPriority() | Returns the maximum priority of this thread group
public final String getName() | Returns the name of this thread group
public final ThreadGroup getParent() | Returns the parent of this thread group
public void list() | Prints information about this thread group to the standard output
public final boolean parentOf(ThreadGroup g) | Tests if this thread group is either the thread group argument or one of its ancestor thread groups
public final void setMaxPriority(int pri) | Sets the maximum priority of the group.
```java
class Test{
        public static void main(String[] args){
        // mygroup的父亲线程组为main线程所在的线程组
        ThreadGroup mygroup = new ThreadGroup("mygroup");
        // 测试看看mygroup是不是main所在的group的子group
        System.out.println("=看看mygroup是不是main所在的group的子group=");
        System.out.println(Thread.currentThread().getThreadGroup().parentOf(mygroup));
        System.out.println("===========================================");

        // 创建三个属于mygroup的线程，其父group为main
        Thread t1 = new Thread(mygroup, ()->{
            while(true){
                try{
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(mygroup, ()->{
            while(true){
                try{
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(mygroup, ()->{
            while(true){
                try{
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        System.out.println("========mygroup中估计活跃的进程数目==========");
        System.out.println(mygroup.activeCount());
        System.out.println("============================================");


        System.out.println("========mygroup中活跃的进程状态=============");
        mygroup.list();
        System.out.println("===========================================");


        System.out.println("========main group中活跃的进程状态==========");
        Thread.currentThread().getThreadGroup().list();
        System.out.println("===========================================");

        System.out.println("========确定下mygroup的父group==============");
        System.out.println(mygroup.getParent() == Thread.currentThread().getThreadGroup());
        System.out.println("===========================================");
    }
}
// 输出
//       =看看mygroup是不是main所在的group的子group=
//                true
//        ===========================================
//       ========mygroup中估计活跃的进程数目==========
//               3
//       ============================================
//      ========mygroup中活跃的进程状态=============
//        java.lang.ThreadGroup[name=mygroup,maxpri=10]
//        Thread[Thread-0,5,mygroup]
//        Thread[Thread-1,5,mygroup]
//        Thread[Thread-2,5,mygroup]
//      ===========================================
//      ========main group中活跃的进程状态==========
//        java.lang.ThreadGroup[name=main,maxpri=10]
//        Thread[main,5,main]
//        Thread[Monitor Ctrl-Break,5,main]
//        java.lang.ThreadGroup[name=mygroup,maxpri=10]
//        Thread[Thread-0,5,mygroup]
//        Thread[Thread-1,5,mygroup]
//        Thread[Thread-2,5,mygroup]
//      ===========================================
//      ========确定下mygroup的父group==============
//        true
//      ===========================================

```

### interrupt方法
interrupt方法会中断当前线程组中的所有线程还有**子线程组**中的所有线程
```java
class ThreadGroup{
    public final void interrupt() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            // 依此调用每个线程的interrupt方法
            for (int i = 0 ; i < nthreads ; i++) {
                threads[i].interrupt();
            }
            ngroupsSnapshot = ngroups;
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
        }
        for (int i = 0 ; i < ngroupsSnapshot ; i++) {
            groupsSnapshot[i].interrupt();
        }
    }
}
public class Entry{
    public static void main(String[] args){

        ThreadGroup mygroup = new ThreadGroup("mygroup");

        // 创建三个属于mygroup的线程，其父group为main
        Thread t1 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mygroup.list();
        mygroup.interrupt();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mygroup.list();
    }
}
//        java.lang.ThreadGroup[name=mygroup,maxpri=10]
//        Thread[Thread-0,5,mygroup]
//        Thread[Thread-1,5,mygroup]
//        Thread[Thread-2,5,mygroup]
//        java.lang.InterruptedException: sleep interrupted
//        at java.lang.Thread.sleep(Native Method)
//        at java.lang.Thread.sleep(Thread.java:340)
//        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//        at Entry.lambda$main$0(Entry.java:13)
//        at java.lang.Thread.run(Thread.java:748)
//        java.lang.InterruptedException: sleep interrupted
//        at java.lang.Thread.sleep(Native Method)
//        at java.lang.Thread.sleep(Thread.java:340)
//        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//        at Entry.lambda$main$1(Entry.java:20)
//        at java.lang.Thread.run(Thread.java:748)
//        java.lang.InterruptedException: sleep interrupted
//        at java.lang.Thread.sleep(Native Method)
//        at java.lang.Thread.sleep(Thread.java:340)
//        at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//        at Entry.lambda$main$2(Entry.java:27)
//        at java.lang.Thread.run(Thread.java:748)
//        TERMINATED
//        java.lang.ThreadGroup[name=mygroup,maxpri=10]
```

### destroy方法
destroy方法会destroy当前线程组（注意:只能destroy一次,并且线程组中没有活跃线程才可以正常执行,否则抛出异常），其会将当前线程组从父线程组的子线程组删除，设置一些标志位等。如下代码所示
```java
class ThreadGroup{
    public final void destroy() {
        int ngroupsSnapshot;
        ThreadGroup[] groupsSnapshot;
        synchronized (this) {
            checkAccess();
            // 如果已经被destroy过了或者线程组中还有活跃线程则抛出异常
            if (destroyed || (nthreads > 0)) {
                throw new IllegalThreadStateException();
            }
            ngroupsSnapshot = ngroups;
            
            if (groups != null) {
                groupsSnapshot = Arrays.copyOf(groups, ngroupsSnapshot);
            } else {
                groupsSnapshot = null;
            }
            // 设置一些参数，如设置destroyed标志位，设置活跃线程数位0等
            if (parent != null) {
                destroyed = true;
                ngroups = 0;
                groups = null;
                nthreads = 0;
                threads = null;
            }
        }
        // 销毁线程
        for (int i = 0 ; i < ngroupsSnapshot ; i += 1) {
            groupsSnapshot[i].destroy();
        }
        // 如果有父线程中，则从父线程组中移除自己
        if (parent != null) {
            parent.remove(this);
        }
    }
}
public class Entry{
    public static void main(String[] args){

        ThreadGroup mygroup = new ThreadGroup("mygroup");

        // 创建三个属于mygroup的线程，其父group为main
        Thread t1 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        Thread.currentThread().getThreadGroup().list();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mygroup.interrupt();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mygroup.destroy();
        Thread.currentThread().getThreadGroup().list();
    }
}
//    java.lang.ThreadGroup[name=main,maxpri=10]
//    Thread[main,5,main]
//    Thread[Monitor Ctrl-Break,5,main]
//    java.lang.ThreadGroup[name=mygroup,maxpri=10]
//      Thread[Thread-0,5,mygroup]
//      Thread[Thread-1,5,mygroup]
//      Thread[Thread-2,5,mygroup]
//    java.lang.InterruptedException: sleep interrupted
//      at java.lang.Thread.sleep(Native Method)
//      at java.lang.Thread.sleep(Thread.java:340)
//      at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//      at Entry.lambda$main$1(Entry.java:20)
//      at java.lang.Thread.run(Thread.java:748)
//    java.lang.InterruptedException: sleep interrupted
//      at java.lang.Thread.sleep(Native Method)
//      at java.lang.Thread.sleep(Thread.java:340)
//      at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//      at Entry.lambda$main$0(Entry.java:13)
//      at java.lang.Thread.run(Thread.java:748)
//    java.lang.InterruptedException: sleep interrupted
//      at java.lang.Thread.sleep(Native Method)
//      at java.lang.Thread.sleep(Thread.java:340)
//      at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//      at Entry.lambda$main$2(Entry.java:27)
//      at java.lang.Thread.run(Thread.java:748)
//    java.lang.ThreadGroup[name=main,maxpri=10]
//      Thread[main,5,main]
//      Thread[Monitor Ctrl-Break,5,main]
```
### setDaemon方法
setDaemon会设置ThreadGroup对象的daemon字段，但并不会改变其中线程的daemon的属性。当ThreadGroup对象的daemon字段为true时，并且其中没有活跃线程等条件时则会自动调用destroy方法
```java
class ThreadGroup{
    public final void setDaemon(boolean daemon) {
        checkAccess();
        // 设置daemon标志位
        this.daemon = daemon;
    }

    /**
     * Notifies the group that the thread {@code t} has terminated.
     *
     * <p> Destroy the group if all of the following conditions are
     * true: this is a daemon thread group; there are no more alive
     * or unstarted threads in the group; there are no subgroups in
     * this thread group.
     *
     * @param  t
     *         the Thread that has terminated
     */
    void threadTerminated(Thread t) {
        synchronized (this) {
            remove(t);

            if (nthreads == 0) {
                notifyAll();
            }
            // 如果daemon标志位为真，并且活跃线程为0，等条件都满足，则destroy()方法
            if (daemon && (nthreads == 0) &&
                (nUnstartedThreads == 0) && (ngroups == 0))
            {
                destroy();
            }
        }
    }
}

public class Entry{
    public static void main(String[] args){
        ThreadGroup mygroup = new ThreadGroup("mygroup");
        // 设置守护线程组
        mygroup.setDaemon(true);
        // 创建三个属于mygroup的线程，其父group为main
        Thread t1 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "my-thread-1");
        Thread t2 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        },"my-thread-2");
        Thread t3 = new Thread(mygroup, ()->{
            try{
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        },"my-thread-3");
        t1.start();
        t2.start();
        t3.start();
        Thread.currentThread().getThreadGroup().list();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mygroup.interrupt();
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
//        当前线程组为守护线程组，自动执行destroy方法
//        mygroup.destroy();
        Thread.currentThread().getThreadGroup().list();
    }
}

//    java.lang.ThreadGroup[name=main,maxpri=10]
//      Thread[main,5,main]
//      Thread[Monitor Ctrl-Break,5,main]
//      java.lang.ThreadGroup[name=mygroup,maxpri=10]
//          Thread[my-thread-1,5,mygroup]
//          Thread[my-thread-2,5,mygroup]
//          Thread[my-thread-3,5,mygroup]
//    java.lang.InterruptedException: sleep interrupted
//       at java.lang.Thread.sleep(Native Method)
//       at java.lang.Thread.sleep(Thread.java:340)
//       at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//       at Entry.lambda$main$1(Entry.java:21)
//       at java.lang.Thread.run(Thread.java:748)
//    java.lang.InterruptedException: sleep interrupted
//       at java.lang.Thread.sleep(Native Method)
//       at java.lang.Thread.sleep(Thread.java:340)
//       at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//       at Entry.lambda$main$0(Entry.java:14)
//       at java.lang.Thread.run(Thread.java:748)
//    java.lang.InterruptedException: sleep interrupted
//       at java.lang.Thread.sleep(Native Method)
//       at java.lang.Thread.sleep(Thread.java:340)
//       at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//       at Entry.lambda$main$2(Entry.java:28)
//       at java.lang.Thread.run(Thread.java:748)
//    java.lang.ThreadGroup[name=main,maxpri=10]
//    Thread[main,5,main]
//      Thread[Monitor Ctrl-Break,5,main]
//      java.lang.ThreadGroup[name=mygroup,maxpri=10]
```









