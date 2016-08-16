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

package com.actelion.research.orbit.utils;

/**
 * Use it as comparator for sorting a ListWithName object. It puts the 'General' metas on top and sorts
 * the rest in alphabitical order.
 */
@SuppressWarnings("unchecked")
public class ListWithNameComparator implements java.util.Comparator<ListWithName> {

    public int compare(ListWithName o1, ListWithName o2) {
        if (o1 == null || o2 == null) throw new IllegalArgumentException("Comparator: Cannot handle null arguments.");
        if (o1.getName().equals(RawUtilsCommon.STR_GENERAL) && (!o2.getName().equals(RawUtilsCommon.STR_GENERAL)))
            return -1;
        else if (o2.getName().equals(RawUtilsCommon.STR_GENERAL) && (!o1.getName().equals(RawUtilsCommon.STR_GENERAL)))
            return 1;
        else
            return o1.getName().compareTo(o2.getName());
    }

}
