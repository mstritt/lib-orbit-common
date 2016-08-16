/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2016 Actelion Pharmaceuticals Ltd., Gewerbestrasse 16, CH-4123 Allschwil, Switzerland.
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

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class SwingWorkerExecutor extends SwingWorker<Integer, Void> implements IWorkerExecutor<Integer> {

    private AbstractWorker<Integer> abstractWorker;

    public SwingWorkerExecutor(AbstractWorker<Integer> abstractWorker) {
        super();
        this.abstractWorker = abstractWorker;
        this.abstractWorker.setServiceSupport(new AbstractWorker.ServiceSupport() {
            @Override
            public void setProgress(int p) {
                SwingWorkerExecutor.this.setProgress(p);
            }

            @Override
            public boolean isCancelled() {
                return SwingWorkerExecutor.this.isCancelled();
            }

            @Override
            public PropertyChangeSupport getPropertyChangeSupport() {
                return SwingWorkerExecutor.this.getPropertyChangeSupport();
            }
        });
    }


    @Override
    protected Integer doInBackground() throws Exception {
        return abstractWorker.doInBackground();
    }

    @Override
    protected void done() {
        abstractWorker.done();
    }


    @Override
    public double getProgressPercent() {
        return getProgress();
    }

    @Override
    public AbstractWorker<Integer> getWorker() {
        return abstractWorker;
    }
}
