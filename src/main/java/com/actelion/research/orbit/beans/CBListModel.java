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

package com.actelion.research.orbit.beans;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CBListModel extends DefaultComboBoxModel {
    public static final String ANY = "<Any>";
    private static final long serialVersionUID = 1L;
    private List<String> myData = new ArrayList<String>();

    public CBListModel(List<String> myData) {
        this.myData = myData;
        this.myData.add(0, ANY);
    }


    @Override
    public int getSize() {
        return myData.size();
    }

    @Override
    public Object getElementAt(int index) {
        return myData.get(index);
    }

    public void sortAlpha() {
        Collections.sort(myData, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1.equals(ANY)) return -1;
                if (o2.equals(ANY)) return 1;
                return o1.compareTo(o2);
            }
        });
    }

    public void sortInt() {
        Collections.sort(myData, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1.equals(ANY)) return -1;
                if (o2.equals(ANY)) return 1;
                try {
                    Integer i1 = Integer.parseInt(o1);
                    Integer i2 = Integer.parseInt(o2);
                    return i1.compareTo(i2);
                } catch (Exception e) {
                    return o1.compareTo(o2);
                }
            }
        });
    }

    public void sortSite() {
        Collections.sort(myData, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1.equals(ANY)) return -1;
                if (o2.equals(ANY)) return 1;
                try {

                    Integer i1 = Integer.parseInt(o1.substring(5));
                    Integer i2 = Integer.parseInt(o2.substring(5));
                    return i1.compareTo(i2);
                } catch (Exception e) {
                    return o1.compareTo(o2);
                }
            }
        });
    }

    public static CBListModel sortCb(CBListModel model, boolean sortInt) {
        if (sortInt) {
            model.sortInt();
        } else {
            model.sortAlpha();
        }
        return model;
    }

    public static CBListModel sortCbSite(CBListModel model) {
        model.sortSite();
        return model;
    }
}
