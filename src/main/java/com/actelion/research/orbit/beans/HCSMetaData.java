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

/**
 * HCS/HTS meta data like platename and wellname.
 */
public class HCSMetaData implements Serializable {
    private int btachNr;
    private String plateName;
    private String wellName;
    private int siteX;
    private int siteY;
    private double siteZ;
    private long datetime;
    private String channel;

    public HCSMetaData(int btachNr, String plateName, String wellName, int siteX, int siteY, double siteZ, long datetime, String channel) {
        this.btachNr = btachNr;
        this.plateName = plateName;
        this.wellName = wellName;
        this.siteX = siteX;
        this.siteY = siteY;
        this.siteZ = siteZ;
        this.datetime = datetime;
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "HCSMetaData{" +
                "btachNr=" + btachNr +
                ", plateName='" + plateName + '\'' +
                ", wellName='" + wellName + '\'' +
                ", siteX=" + siteX +
                ", siteY=" + siteY +
                ", siteZ=" + siteZ +
                ", datetime=" + datetime +
                ", channel='" + channel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HCSMetaData that = (HCSMetaData) o;

        if (btachNr != that.btachNr) return false;
        if (siteX != that.siteX) return false;
        if (siteY != that.siteY) return false;
        if (Double.compare(that.siteZ, siteZ) != 0) return false;
        if (datetime != that.datetime) return false;
        if (plateName != null ? !plateName.equals(that.plateName) : that.plateName != null) return false;
        if (wellName != null ? !wellName.equals(that.wellName) : that.wellName != null) return false;
        return channel != null ? channel.equals(that.channel) : that.channel == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = btachNr;
        result = 31 * result + (plateName != null ? plateName.hashCode() : 0);
        result = 31 * result + (wellName != null ? wellName.hashCode() : 0);
        result = 31 * result + siteX;
        result = 31 * result + siteY;
        temp = Double.doubleToLongBits(siteZ);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (datetime ^ (datetime >>> 32));
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    public int getBtachNr() {
        return btachNr;
    }

    public void setBtachNr(int btachNr) {
        this.btachNr = btachNr;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
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

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
