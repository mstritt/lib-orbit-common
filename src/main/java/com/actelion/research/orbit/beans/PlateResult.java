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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Contains meta data and a String[] list of data values.
 * Additionally, it is possible to set a reference date.
 */
public class PlateResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<RawMeta> metas = null;
    private List<String[]> data = null;
    private Date referenceDate = null;
    public final static char SEPERATOR = '\t';

    public PlateResult() {

    }

    public PlateResult(List<RawMeta> metas, List<String[]> data) {
        this.metas = metas;
        this.data = data;
    }

    public List<RawMeta> getMetas() {
        return metas;
    }

    public void setMetas(List<RawMeta> metas) {
        this.metas = metas;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String dataToString() {
        StringBuilder sb = new StringBuilder();
        for (String[] line : data) {
            for (int i = 0; i < line.length; i++) {
                if (i < line.length - 1)
                    sb.append(line[i] + SEPERATOR);
                else sb.append(line[i] + "\n");
            }
        }
        return sb.toString();
    }

    public byte[] toByteArray() {
        if (data == null) return null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(outStream);
        try {
            osw.write(dataToString());
            osw.flush();
        } catch (IOException e) {
            System.out.println("Error writing to outputstream: " + e.getMessage());
        }
        return outStream.toByteArray();
    }


}
