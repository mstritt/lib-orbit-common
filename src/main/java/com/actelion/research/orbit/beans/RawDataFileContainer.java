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

package com.actelion.research.orbit.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for rawDataFiles that are not yet persisted and don't have a rawDataFileId. \\
 * The encodedDataThn may contain encoded JPG data of a thumbnail.
 */
public class RawDataFileContainer implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private RawDataFile rawDataFile = null;
    private List<RawMeta> rawMetas = null;
    private byte[] encodedDataThn = null;

    public RawDataFileContainer() {
        this.rawMetas = new ArrayList<RawMeta>();
    }


    public RawDataFileContainer(RawDataFile rawDataFile, List<RawMeta> rawMetas, byte[] encodedDataThn) {
        this.rawDataFile = rawDataFile;
        this.rawMetas = rawMetas;
        this.encodedDataThn = encodedDataThn;
    }


    public RawDataFile getRawDataFile() {
        return rawDataFile;
    }

    public void setRawDataFile(RawDataFile rawDataFile) {
        this.rawDataFile = rawDataFile;
    }

    public List<RawMeta> getRawMetas() {
        if (this.rawMetas == null) this.rawMetas = new ArrayList<RawMeta>();
        return rawMetas;
    }

    public void setRawMetas(List<RawMeta> rawMetas) {
        this.rawMetas = rawMetas;
    }

    public void addRawMetas(List<RawMeta> rawMetasAdd) {
        if (rawMetasAdd == null) return;
        if (this.rawMetas == null) this.rawMetas = new ArrayList<RawMeta>();
        this.rawMetas.addAll(rawMetasAdd);
    }


    public byte[] getEncodedDataThn() {
        return encodedDataThn;
    }


    public void setEncodedDataThn(byte[] encodedDataThn) {
        this.encodedDataThn = encodedDataThn;
    }

    public RawDataFileContainer clone() {
        try {
            return (RawDataFileContainer) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


}
