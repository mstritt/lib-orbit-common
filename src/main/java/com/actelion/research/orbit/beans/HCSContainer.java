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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HCSContainer implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String HCSHeaderBase = "ContainerName\tWellName\tSiteX\tSiteY\tSiteZ\tObjectId\tObjectX\tObjectY\tTime";
    public static final List<String> HCSHeaderBaseList = new ArrayList<String>() {{
        add("ContainerName");
        add("WellName");
        add("SiteX");
        add("SiteY");
        add("SiteZ");
        add("ObjectId");
        add("ObjectX");
        add("ObjectY");
        add("Time");
    }};
    public static final int NumHeaderColumns = 9;
    private String containerName = "";
    private Date referenceDate = new Date();
    private List<HCSRow> data = new ArrayList<HCSRow>();
    private List<String> header = new ArrayList<String>();
    private String plateFormat = "";
    private int iPlateBatch = 0;
    private String sConcentration = "";
    private String sPipelineName = "";

    public HCSContainer() {

    }

    public HCSContainer(String containerName, Date referenceDate, List<HCSRow> data, List<String> header, String plateFormat, int iPlateBatch, String sConcentration, String sPipelineName) {
        this.containerName = containerName;
        this.referenceDate = referenceDate;
        if (data != null)
            this.data = data;
        this.header = header;
        this.plateFormat = plateFormat;
        this.iPlateBatch = iPlateBatch;
        this.sConcentration = sConcentration;
        this.sPipelineName = sPipelineName;
    }


    public HCSContainer(String containerName, Date referenceDate, List<HCSRow> data, List<String> header, String plateFormat, int iPlateBatch) {
        this.containerName = containerName;
        this.referenceDate = referenceDate;
        if (data != null)
            this.data = data;
        this.header = header;
        this.plateFormat = plateFormat;
        this.iPlateBatch = iPlateBatch;
    }

    public HCSContainer(String containerName, Date referenceDate, List<HCSRow> data, List<String> header, String plateFormat) {
        this.containerName = containerName;
        this.referenceDate = referenceDate;
        if (data != null)
            this.data = data;
        this.header = header;
        this.plateFormat = plateFormat;
    }

    public HCSContainer(String containerName, Date referenceDate, List<HCSRow> data, List<String> header) {
        this.containerName = containerName;
        this.referenceDate = referenceDate;
        if (data != null)
            this.data = data;
        this.header = header;
    }


    @Override
    public String toString() {
        return "HCSContainer [containerName=" + containerName + ", referenceDate=" + referenceDate + ", dataNumRows=" + (data == null ? null : data.size()) + ", plateFormat=" + plateFormat + ", plateBatch=" + iPlateBatch + "s]";
    }

    public List<String> getValueNames() {
        return header.subList(HCSContainer.HCSHeaderBase.split(String.valueOf(HCSRow.HCSSeparator)).length, header.size());
    }

    /**
     * returns the header + datarows as a string
     *
     * @return
     */
    public String getDataAsString() {
        StringBuilder sb = new StringBuilder();
        if (header != null && header.size() > 0) {
            for (int i = 0; i < header.size(); i++) {
                String colName = header.get(i);
                sb.append(colName);
                if (i < header.size() - 1) sb.append(HCSRow.HCSSeparator);
            }
            sb.append("\n");
        }
        if (data != null && data.size() > 0) {
            for (HCSRow row : data) {
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

        HCSContainer that = (HCSContainer) o;

        if (iPlateBatch != that.iPlateBatch) return false;
        if (containerName != null ? !containerName.equals(that.containerName) : that.containerName != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (header != null ? !header.equals(that.header) : that.header != null) return false;
        if (plateFormat != null ? !plateFormat.equals(that.plateFormat) : that.plateFormat != null) return false;
        return referenceDate != null ? referenceDate.equals(that.referenceDate) : that.referenceDate == null;

    }

    @Override
    public int hashCode() {
        int result = containerName != null ? containerName.hashCode() : 0;
        result = 31 * result + (referenceDate != null ? referenceDate.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (header != null ? header.hashCode() : 0);
        result = 31 * result + (plateFormat != null ? plateFormat.hashCode() : 0);
        result = 31 * result + iPlateBatch;
        return result;
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

    public List<HCSRow> getData() {
        return data;
    }

    public void setData(List<HCSRow> data) {
        this.data = data;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public String getPlateFormat() {
        return plateFormat;
    }

    public void setPlateFormat(String plateFormat) {
        this.plateFormat = plateFormat;
    }

    public int getiPlateBatch() {
        return iPlateBatch;
    }

    public void setiPlateBatch(int iPlateBatch) {
        this.iPlateBatch = iPlateBatch;
    }

    public String getsConcentration() {
        return sConcentration;
    }

    public void setsConcentration(String sConcentration) {
        this.sConcentration = sConcentration;
    }

    public String getsPipelineName() {
        return sPipelineName;
    }

    public void setsPipelineName(String sPipelineName) {
        this.sPipelineName = sPipelineName;
    }
}
