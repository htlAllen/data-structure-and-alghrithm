# Thread构造方法
##### Thread()
Allocates a new Thread object.
##### Thread(Runnable target)
Allocates a new Thread object.
##### Thread(Runnable target, String name)
Allocates a new Thread object.
##### Thread(String name)
Allocates a new Thread object.
##### Thread(ThreadGroup group, Runnable target)
Allocates a new Thread object.
##### Thread(ThreadGroup group, Runnable target, String name)
Allocates a new Thread object so that it has target as its run object, has the specified name as its name, and belongs to the thread group referred to by group.
##### Thread(ThreadGroup group, Runnable target, String name, long stackSize)
Allocates a new Thread object so that it has target as its run object, has the specified name as its name, and belongs to the thread group referred to by group, and has the specified stack size.
##### Thread(ThreadGroup group, String name)
Allocates a new Thread object.

## 总结
#### 1. 构造函数的参数无String name，则采用默认线程名字, 也可以手动修改线程的名称
```java
class Thread{
    public Thread() {
    // 线程的状态初始为0
    private volatile int threadStatus = 0;
            // 线程默认名称
            init(null, null, "Thread-" + nextThreadNum(), 0);
        }

    public final synchronized void setName(String name) {
        checkAccess();
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;
        //  线程的状态
        if (threadStatus != 0) {
            setNativeName(name);
        }
    }
    private native void setNativeName(String name);
    // 获得线程的状态
    public State getState() {
        // get current thread state
        return sun.misc.VM.toThreadState(threadStatus);
    }
}
```

#### 2.线程的父子关系，无论采用哪个构造函数，最终调用的都是init方法

```java
class Thread{
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize) {
        init(g, target, name, stackSize, null, true);
    }
    
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc,
                      boolean inheritThreadLocals) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }
        // 1 线程的名字
        this.name = name;
        // 2 线程有父线程
        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        // 3 线程是属于一个线程组的，若没有指定组，则默认是和父线程一个组
        if (g == null) {
            /* Determine if it's an applet or not */

            /* If there is a security manager, ask the security manager
               what to do. */
            if (security != null) {
                g = security.getThreadGroup();
            }

            /* If the security doesn't have a strong opinion of the matter
               use the parent thread group. */
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }

        /* checkAccess regardless of whether or not threadgroup is
           explicitly passed in. */
        g.checkAccess();

        /*
         * Do we have the required permissions?
         */
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }

        g.addUnstarted();

        this.group = g;
        // 4. 继承父线程的一些属性，如是否是守护线程，获得夫线程的优先级
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority);
        if (inheritThreadLocals && parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        /* Stash the specified stack size in case the VM cares */
        // 5. 给线程分配的栈内存大小
        this.stackSize = stackSize;
        // 6. 线程是有线程ID的
        /* Set thread ID */
        tid = nextThreadID();
    }

}
```

####3.守护线程，运行java程序的时候，其实是运行了一个JVM进程，然后JVM进程再创建了很多线程，如GC线程，main线程等。当JVM中的所有线程都是守护线程时，则JVM进程退出运行
```java
class Thread{
    // 修改线程的守护状态
    public final void setDaemon(boolean on) {
        checkAccess();
        // 只有线程处于NEW状态时才能修改，start()后再修改则会抛出IllegalThreadStateException
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }
    public final native boolean isAlive();

}
```