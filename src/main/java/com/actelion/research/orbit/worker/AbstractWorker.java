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
import java.util.concurrent.Callable;

public abstract class AbstractWorker<T> implements Runnable, Callable<T> {

    public abstract T doInBackground() throws Exception;

    public ServiceSupport serviceSupport = null;

    public void done() {
    }

    @Override
    public void run() {
        try {
            doInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T call() throws Exception {
        return doInBackground();
    }

    protected final void setProgressWrapper(int p) {
        if (serviceSupport != null)
            serviceSupport.setProgress(p);
    }

    public final void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (serviceSupport != null && serviceSupport.getPropertyChangeSupport() != null)
            serviceSupport.getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    public boolean isCancelled() {
        if (serviceSupport != null) {
            return serviceSupport.isCancelled();
        } else {
            return false;
        }
    }

    public ServiceSupport getServiceSupport() {
        return serviceSupport;
    }

    public void setServiceSupport(ServiceSupport serviceSupport) {
        this.serviceSupport = serviceSupport;
    }

    public static abstract class ServiceSupport {
        public abstract void setProgress(int p);

        public abstract boolean isCancelled();

        public abstract PropertyChangeSupport getPropertyChangeSupport();
    }


}
