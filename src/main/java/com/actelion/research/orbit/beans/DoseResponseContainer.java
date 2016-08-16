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

import com.actelion.research.orbit.utils.RawUtilsCommon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoseResponseContainer implements Serializable, Comparable<DoseResponseContainer> {
    private static final long serialVersionUID = 1L;
    public static final String DoseResponseHeader = "ContainerName\tWellName\tWellType\tCompoundName\tTubeId\tCompoundId\tCompoundType\tSubstanceNo\tReplicateNo\tConcentration\tConcentrationFactor\tMeasurementName\tMeasurementValue";
    public static final int NumHeaderColumns = 13;
    private String containerName = "";
    private Date referenceDate = new Date();
    private String plateFormat = RawUtilsCommon.PlateFormat_384;
    private List<DoseResponseRow> data = new ArrayList<DoseResponseRow>();

    public DoseResponseContainer() {

    }

    public DoseResponseContainer(String containerName, Date referenceDate, List<DoseResponseRow> data, String plateFormat) {
        this.containerName = containerName;
        this.referenceDate = referenceDate;
        this.plateFormat = plateFormat;
        if (data != null)
            this.data = data;
    }


    @Override
    public String toString() {
        return "DoseResponseContainer [containerName=" + containerName + ", referenceDate=" + referenceDate + ", plateFormat=" + plateFormat + ", dataNumRows=" + (data == null ? null : data.size()) + "]";
    }


    /**
     * returns the header + datarows as a string
     *
     * @return
     */
    public String getDataAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DoseResponseHeader);
        sb.append("\n");
        if (data != null && data.size() > 0) {
            for (DoseResponseRow row : data) {
                sb.append(row.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public byte[] toByteArray() {
        if (data == null) return null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(outStream);
        try {
            osw.write(getDataAsString());
            osw.flush();
        } catch (IOException e) {
            System.out.println("Error writing to outputstream: " + e.getMessage());
        }
        return outStream.toByteArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoseResponseContainer that = (DoseResponseContainer) o;

        if (containerName != null ? !containerName.equals(that.containerName) : that.containerName != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (plateFormat != null ? !plateFormat.equals(that.plateFormat) : that.plateFormat != null) return false;
        return referenceDate != null ? referenceDate.equals(that.referenceDate) : that.referenceDate == null;

    }

    @Override
    public int hashCode() {
        int result = containerName != null ? containerName.hashCode() : 0;
        result = 31 * result + (referenceDate != null ? referenceDate.hashCode() : 0);
        result = 31 * result + (plateFormat != null ? plateFormat.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(DoseResponseContainer that) {
        if (this.containerName.compareTo(that.containerName) < 0) {
            return -1;
        } else if (this.containerName.compareTo(that.containerName) > 0) {
            return 1;
        }

        if (this.referenceDate.compareTo(that.referenceDate) < 0) {
            return -1;
        } else if (this.referenceDate.compareTo(that.referenceDate) > 0) {
            return 1;
        }

        if (this.plateFormat.compareTo(that.plateFormat) < 0) {
            return -1;
        } else if (this.plateFormat.compareTo(that.plateFormat) > 0) {
            return 1;
        }

        return 0;
    }


    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public List<DoseResponseRow> getData() {
        return data;
    }

    public void setData(List<DoseResponseRow> data) {
        this.data = data;
    }

    public String getPlateFormat() {
        return plateFormat;
    }

    public void setPlateFormat(String plateFormat) {
        this.plateFormat = plateFormat;
    }
}
