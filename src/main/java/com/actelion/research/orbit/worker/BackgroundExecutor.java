/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2017 Idorsia Pharmaceuticals Ltd., Hegenheimermattweg 91, CH-4123 Allschwil, Switzerland.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.actelion.research.orbit.worker;

import java.beans.PropertyChangeSupport;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class BackgroundExecutor implements IWorkerExecutor<Integer> {

    protected AbstractWorker<Integer> abstractWorker;
    protected FutureTask<Integer> futureTask = null;
    protected final AtomicInteger progress = new AtomicInteger(0);
    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public BackgroundExecutor(AbstractWorker<Integer> abstractWorker) {
        this.abstractWorker = abstractWorker;
        this.abstractWorker.setServiceSupport(new AbstractWorker.ServiceSupport() {
            @Override
            public void setProgress(int p) {
                BackgroundExecutor.this.progress.set(p);
            }

            @Override
            public boolean isCancelled() {
                return BackgroundExecutor.this.isCancelled();
            }

            @Override
            public PropertyChangeSupport getPropertyChangeSupport() {
                return BackgroundExecutor.this.propertyChangeSupport;
            }
        });
    }

    protected void done() {
        abstractWorker.done();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (futureTask == null) return false;
        else return futureTask.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        if (futureTask == null) return false;
        else return futureTask.isCancelled();
    }

    public boolean isDone() {
        if (futureTask == null) return false;
        else return futureTask.isDone();
    }

    public Integer get() throws ExecutionException, InterruptedException {
        if (futureTask == null) {
            return null;
        } else return futureTask.get();
    }

    @Override
    public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (futureTask == null) {
            return null;
        } else return futureTask.get(timeout, unit);
    }

    public double getProgressPercent() {
        return progress.get();
    }

    @Override
    public AbstractWorker<Integer> getWorker() {
        return abstractWorker;
    }

    /**
     * executes worker in a new thread
     */
    public void execute() {
        futureTask = new FutureTask(abstractWorker);
        new Thread() {
            @Override
            public void run() {
                futureTask.run();
            }
        }.start();
    }

    /**
     * executes worker
     */
    @Override
    public void run() {
        futureTask = new FutureTask(abstractWorker);
        futureTask.run();
    }
}
