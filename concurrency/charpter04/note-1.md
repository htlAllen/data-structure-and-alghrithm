# Thread常见api解析

### sleep方法
sleep方法使**当前正在运行**的（Running）的线程进入指定毫秒数的休眠，此时线程进入block状态。即将当前运行的上下文保存，指定休眠时间过后，**自动**进入Runnable状态，获得cpu时，恢复之前保存的上下文，继续执行后面的指令
> sleep方法不会放弃monitor锁的所有权
```java
class Thread{
    // Thread.sleep() 是一个 JNI方法
    public static native void sleep(long millis) throws InterruptedException;
}
```
### yield方法
yield方法使**当前正在运行**的（即Running）的线程暗示调度器自己愿意放弃cpu的使用，然而调度器可以接受这个暗示，也可以忽略这个暗示。
```java
class Thread{
    // Thread.yield() 是一个 JNI方法
    public static native void yield();
}
```

> sleep和yield的区别和联系
> 1. sleep() 和 yield()都是想释放cpu。sleep()一定会释放CPU资源，而yield()是一种暗示，具体是否释放得看调度器
> 2. sleep()线程进入block状态，而yied()进入runnable状态


### setPriority方法
setPriority方法设置线程的优先级，使得调度器以较大的概率从就绪队列中选择优先级高的线程，并将cpu资源赋给该线程。但并不代表优先级低的线程不会被给cpu资源，只是概率相对较低
```java
class Thread{
    public final void setPriority(int newPriority){
    }
}
```
> 注意，线程的默认优先级和父线程的优先级相同，见init()方法

### currentThread()
currentThread()方法用于获得当前正在运行(即处于running状态)的线程对象的引用

```java
class Thread{
    public static native Thread currentThread();
}
```
### interrupt方法
（一） interrupt方法

中断一个处于**阻塞状态**的线程（如果此时线程是runnable，running状态呢？）。当**另一线程**调用该线程的interrupt方法，对该线程的影响分为如下几种情况：

a) 对于由Thread类的sleep(),join()方法以及Object类的wait()方法等**可中断方法**所造成的阻塞。此时该线程会收到InterruptedException异常，并且interrupt status会被重置（false）
> 对于可中断方法，若当前线程的interrupt status为true，则立即收到InterruptException

b) 如果线程是因为IO操作阻塞的。此时该线程会收到CloseByInterruptException，并且会设置Interrupt status（true）

c）Selector操作的阻塞？Selector是什么？后续补上！

e）线程处于runnable，running状态。此时线程不会收到异常，但是会设置interrupt status（true）
```java
class Thread{
    public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
    private native void interrupt0();
}
```

（二） isInterrupted
isInterrupted仅仅返回当前线程的interrupt status，该方法不会修改interrupt status
```java
class Thread{
    public boolean isInterrupted() {
        return isInterrupted(false);
    }
    private native boolean isInterrupted(boolean ClearInterrupted);
}
```

（三） interrupted
isInterrupted不仅返回当前线程的interrupt status，而且会修改interrupt status
```java
class Thread{
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }
    private native boolean isInterrupted(boolean ClearInterrupted);
}
```
> interrupt status表示当前线程的中断状态，其值为true则表示该线程被中断了，为false表示该线程未被中断。若

### join方法
join方法指的是，调用其他线程的join方法会使得调用者处于阻塞状态，并且由于join方法所导致的阻塞是可被中断的。所以使得调用者从阻塞恢复有几种方法：1.目标线程结束声明周期 2.join时间结束 3.被其他线程中断





> 注意，弄清除是哪个线程调用哪个线程的API，又使得哪个线程进入阻塞状态是关键









