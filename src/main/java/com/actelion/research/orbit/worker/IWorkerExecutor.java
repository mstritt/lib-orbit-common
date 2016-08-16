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

import java.util.concurrent.Future;

public interface IWorkerExecutor<T> extends Runnable, Future<T> {
    void execute(); // execute worker in background (new thread)

    double getProgressPercent(); // returns the progress in percent (0..100). The name getProgressPercent is used as return type so that the getProgress->double from JavaFX Task and SwingWorker.getProgress->int does not conflict.

    AbstractWorker<T> getWorker();
}
