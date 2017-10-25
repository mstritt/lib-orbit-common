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

package com.actelion.research.orbit.utils;

import com.actelion.research.orbit.beans.RawMeta;

/**
 * Compares RawMeta objects based on rawMeta.getName().
 */
public class RawMetaComparator implements java.util.Comparator<RawMeta> {

    public int compare(RawMeta o1, RawMeta o2) {
        if (o1 == null || o2 == null) throw new IllegalArgumentException("Comparator: Cannot handle null arguments.");
        if (o1.getName().equals("Orbit ID")) return -1; // put Orbit ID always on top
        if (o1.getName().equals("Modify Date")) return 100;
        if (o1.getName().equals("Reference Date")) return 101;
        return o1.getName().compareTo(o2.getName());
    }
}
