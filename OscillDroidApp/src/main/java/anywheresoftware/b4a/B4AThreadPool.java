package anywheresoftware.b4a;

import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class B4AThreadPool {
    private static final int THREADS_SPARE = 5;
    private final WeakHashMap<Object, HashMap<Integer, Future<?>>> futures = new WeakHashMap<>();
    /* access modifiers changed from: private */
    public ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 50, 60, TimeUnit.SECONDS, new SynchronousQueue()) {
        /* access modifiers changed from: protected */
        public void afterExecute(Runnable r, Throwable t) {
            for (int i = 0; i < 1; i++) {
                QueuedTask qt = B4AThreadPool.this.queueOfTasks.poll();
                if (qt != null) {
                    BA.handler.post(qt);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final ConcurrentLinkedQueue<QueuedTask> queueOfTasks = new ConcurrentLinkedQueue<>();

    public B4AThreadPool() {
        this.pool.setThreadFactory(new MyThreadFactory(null));
    }

    private static class MyThreadFactory implements ThreadFactory {
        private final ThreadFactory defaultFactory;

        private MyThreadFactory() {
            this.defaultFactory = Executors.defaultThreadFactory();
        }

        /* synthetic */ MyThreadFactory(MyThreadFactory myThreadFactory) {
            this();
        }

        public Thread newThread(Runnable r) {
            Thread t = this.defaultFactory.newThread(r);
            t.setDaemon(true);
            return t;
        }
    }

    public void submit(Runnable task, Object container, int taskId) {
        if (this.pool.getActiveCount() > this.pool.getMaximumPoolSize() - 5) {
            this.queueOfTasks.add(new QueuedTask(task, container, taskId));
        } else {
            submitToPool(task, container, taskId);
        }
    }

    class QueuedTask implements Runnable {
        final Object container;
        final Runnable task;
        final int taskId;

        public QueuedTask(Runnable task2, Object container2, int taskId2) {
            this.task = task2;
            this.container = container2;
            this.taskId = taskId2;
        }

        public void run() {
            if (B4AThreadPool.this.pool.getActiveCount() > B4AThreadPool.this.pool.getMaximumPoolSize() - 5) {
                BA.handler.postDelayed(this, 50);
            } else {
                B4AThreadPool.this.submitToPool(this.task, this.container, this.taskId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void submitToPool(Runnable task, Object container, int taskId) {
        Future<?> f = this.pool.submit(task);
        HashMap<Integer, Future<?>> map = this.futures.get(container);
        if (map == null) {
            map = new HashMap<>();
            this.futures.put(container, map);
        }
        Iterator<Future<?>> it = map.values().iterator();
        while (it.hasNext()) {
            if (it.next().isDone()) {
                it.remove();
            }
        }
        map.put(Integer.valueOf(taskId), f);
    }

    public boolean isRunning(Object container, int taskId) {
        HashMap<Integer, Future<?>> map = this.futures.get(container);
        if (map == null) {
            return false;
        }
        Future<?> f = map.get(Integer.valueOf(taskId));
        if (f == null) {
            return false;
        }
        return !f.isDone();
    }

    public void markTaskAsFinished(Object container, int taskId) {
        HashMap<Integer, Future<?>> map = this.futures.get(container);
        if (map != null) {
            map.remove(Integer.valueOf(taskId));
        }
    }
}
