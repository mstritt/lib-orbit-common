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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Represents one HCS data row as specified in the Orbit HCS Format Specification.
 * ContainerName must not contain tabulators!
 */
public class HCSRow implements Serializable, Comparable<HCSRow> {
    public static final DateFormat HCSdateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS Z");
    public static final char HCSSeparator = '\t';
    private static final long serialVersionUID = 1L;

    private String containerName = "";
    private String wellName = "";
    private int siteX = 0;
    private int siteY = 0;
    private double siteZ = 0;
    private String objectId = "";
    private int objectX = 0;
    private int objectY = 0;
    private Date dateTime = new Date();
    private double[] values = null;

    public HCSRow() {

    }

    public HCSRow(String containerName, String wellName, int siteX, int siteY, double siteZ, String objectId, int objectX, int objectY, Date dateTime,
                  double[] values) {
        this.containerName = containerName;
        this.wellName = wellName;
        this.siteX = siteX;
        this.siteY = siteY;
        this.siteZ = siteZ;
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.dateTime = dateTime;
        this.values = values;
    }

    /**
     * Parses a HCSRow.toString() string and create a HCSRow instance.
     * Returns null if s is null.
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static HCSRow parseHCSRow(String s) throws ParseException {
        if (s == null) return null;
        String[] split = s.split(String.valueOf(HCSSeparator), -1);
        if (split == null || split.length < 9)
            throw new ParseException("argument is not a valid HCSRow (null or length<9)", 0);
        Date dateTime = HCSdateFormat.parse(split[8]);
        double[] values = null;
        if (split.length > 9) {
            values = new double[split.length - 9];
            double d = Double.NaN;
            for (int i = 0; i < split.length - 9; i++) {
                try {
                    d = Double.parseDouble(split[i + 9]);
                } catch (Exception e) {
                    d = Double.NaN;
                }
                values[i] = d;
            }
        }
        return new HCSRow(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Double.parseDouble(split[4]),
                split[5], Integer.parseInt(split[6]), Integer.parseInt(split[7]), dateTime, values);
    }

    /**
     * @SuppressWarnings{
     * Returns one data line as a string with respect to HCSSeparator and HCSDateFormat.<br/>
     * Remark: No StringBuilder is used because in most cases values.length will be around 4.
     * If length is >> 4 a StringBuilder should be considered. }
     */
    @Override
    public String toString() {
        String s = containerName + HCSSeparator + wellName + HCSSeparator + siteX + HCSSeparator + siteY + HCSSeparator + siteZ + HCSSeparator + objectId + HCSSeparator + objectX + HCSSeparator + objectY + HCSSeparator + HCSdateFormat.format(dateTime);
        if (values != null) {
            for (double v : values) {
                String ds = Double.isNaN(v) ? "" : Double.toString(v);
                s += HCSSeparator + ds;
            }
        }
        return s;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((containerName == null) ? 0 : containerName.hashCode());
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
        result = prime * result + objectX;
        result = prime * result + objectY;
        result = prime * result + siteX;
        result = prime * result + siteY;
        long temp;
        temp = Double.doubleToLongBits(siteZ);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Arrays.hashCode(values);
        result = prime * result + ((wellName == null) ? 0 : wellName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof HCSRow))
            return false;
        HCSRow other = (HCSRow) obj;
        if (containerName == null) {
            if (other.containerName != null)
                return false;
        } else if (!containerName.equals(other.containerName))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        if (objectId == null) {
            if (other.objectId != null)
                return false;
        } else if (!objectId.equals(other.objectId))
            return false;
        if (objectX != other.objectX)
            return false;
        if (objectY != other.objectY)
            return false;
        if (siteX != other.siteX)
            return false;
        if (siteY != other.siteY)
            return false;
        if (Double.doubleToLongBits(siteZ) != Double.doubleToLongBits(other.siteZ))
            return false;
        if (!Arrays.equals(values, other.values))
            return false;
        if (wellName == null) {
            if (other.wellName != null)
                return false;
        } else if (!wellName.equals(other.wellName))
            return false;
        return true;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public int getSiteX() {
        return siteX;
    }

    public void setSiteX(int siteX) {
        this.siteX = siteX;
    }

    public int getSiteY() {
        return siteY;
    }

    public void setSiteY(int siteY) {
        this.siteY = siteY;
    }

    public double getSiteZ() {
        return siteZ;
    }

    public void setSiteZ(double siteZ) {
        this.siteZ = siteZ;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getObjectX() {
        return objectX;
    }

    public void setObjectX(int objectX) {
        this.objectX = objectX;
    }

    public int getObjectY() {
        return objectY;
    }

    public void setObjectY(int objectY) {
        this.objectY = objectY;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public static DateFormat getHcsdateformat() {
        return HCSdateFormat;
    }

    public int compareTo(HCSRow o) {
        if (o == null) return -1;
        if (this == o) return 0;
        int comp;
        // containerName
        if (containerName != null && o.getContainerName() != null) {
            comp = containerName.compareTo(o.getContainerName());
            if (comp != 0) return comp;
        }
        // wellName
        if (wellName != null && o.getWellName() != null) {
            comp = wellName.compareTo(o.getWellName());
            if (comp != 0) return comp;
        }
        // siteX,Y,Z
        if (siteX != o.getSiteX()) return siteX - o.getSiteX();
        if (siteY != o.getSiteY()) return siteY - o.getSiteY();
        if (siteZ != o.getSiteZ()) return (int) Math.signum(siteZ - o.getSiteZ());

        // objectId
        if (objectId != null && o.getObjectId() != null) {
            comp = objectId.compareTo(o.getObjectId());
            if (comp != 0) return comp;
        }

        // objectX,Y
        if (objectX != o.getObjectX()) return objectX - o.getObjectX();
        if (objectY != o.getObjectY()) return objectY - o.getObjectY();

        return dateTime.compareTo(o.getDateTime());
    }


}
