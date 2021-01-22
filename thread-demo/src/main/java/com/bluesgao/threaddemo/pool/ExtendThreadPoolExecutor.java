package com.bluesgao.threaddemo.pool;

import java.util.concurrent.*;

public class ExtendThreadPoolExecutor extends ThreadPoolExecutor {
    public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ExtendThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                // If future got interrupted exception, we want to interrupt parent thread itself.
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            handleUncaughtException(t);
        }
    }

    private static void handleUncaughtException(Throwable t) {
        if (t != null && t instanceof Error) {
            if (t instanceof OutOfMemoryError) {
                try {
                    System.err.println("Halting due to Out Of Memory Error..." + Thread.currentThread().getName());
                } catch (Throwable err) {
                    //Again we don't want to exit because of logging issues.
                }
                Runtime.getRuntime().halt(-1);
            } else {
                //Running in daemon mode, we would pass Error to calling thread.
                throw (Error) t;
            }
        } else if (t instanceof RuntimeException) {
            System.out.println(t.getMessage());
        }
    }
}
