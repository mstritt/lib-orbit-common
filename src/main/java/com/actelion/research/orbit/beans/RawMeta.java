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

import com.actelion.research.orbit.utils.RawDbTypes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Class representing a RawMeta object. This is used to specify a set of property/value pairs for RawData or RawDataFile.
 */
public class RawMeta implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private int rawMetaId = 0;
    private int rawDataId = 0;
    private int rawDataFileId = 0;
    private int rawTypeId = 0;
    private String name = "";
    private String value = "";
    private String userId = "";
    private Date modifyDate = null;

    public RawMeta() {
        modifyDate = new Date(System.currentTimeMillis());
    }

    public int getRawMetaId() {
        return rawMetaId;
    }

    public void setRawMetaId(int rawMetaId) {
        this.rawMetaId = rawMetaId;
    }

    public int getRawDataId() {
        return rawDataId;
    }

    public void setRawDataId(int rawDataId) {
        this.rawDataId = rawDataId;
    }

    public int getRawTypeId() {
        return rawTypeId;
    }

    public void setRawTypeId(int rawTypeId) {
        this.rawTypeId = rawTypeId;
    }

    public String getName() {
        return name;
    }

    /**
     * replaces _ by ' '
     *
     * @return
     */
    public String getNameClean() {
        return name.replaceAll("_", " ");
    }

    /**
     * replaces some words for better hierarchical handling (e.g. Microscope Stand -> Microscope.Stand) based on getNameClean.
     *
     * @return
     */
    public String getNameCleanSpecial() {
        String s = name.replaceAll("_", " ");
        s = s.replaceAll("Microscope ", "Microscope.");
        s = s.replaceAll("Image.", ""); // -> in general group
        return s;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getValueFormatted() {
        String s = this.value;

        if (this.getRawTypeId() == RawDbTypes.RAW_TYPE_INTEGER) {
            return s;
        }

        // just try if it can be typecasted to double (because s.t. it's a double saved as a string...
        try {
            s = String.format("%1$.2f", Double.parseDouble(this.value));
            if (s.endsWith(".00") || s.endsWith(",00")) s = s.substring(0, Math.max(0, s.length() - 3));
        } catch (Exception ex) {
            // ok, so just take the string. Remark: dates should already be formatted via RawMetaFactory(s).
            s = this.value;
        }
        return s;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getRawDataFileId() {
        return rawDataFileId;
    }

    public void setRawDataFileId(int rawDataFileId) {
        this.rawDataFileId = rawDataFileId;
    }

    public String toString() {
        return "Name:" + name + " | Value:" + value + " | Type:" + rawTypeId + " | ModifyDate:" + modifyDate + " | RDFId:" + rawDataFileId + " | RawDataId:" + rawDataId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + rawDataFileId;
        result = prime * result + rawDataId;
        result = prime * result + rawMetaId;
        result = prime * result + rawTypeId;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RawMeta))
            return false;
        RawMeta other = (RawMeta) obj;
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (rawDataFileId != other.rawDataFileId)
            return false;
        if (rawDataId != other.rawDataId)
            return false;
        if (rawMetaId != other.rawMetaId)
            return false;
        if (rawTypeId != other.rawTypeId)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    public RawMeta clone() {
        try {
            return (RawMeta) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Get a {@link RawMeta} element by its name
     * 
     * @param rawMetas {@link Collection} of {@link RawMeta} elements
     * @param name Name to look for
     * @return Any matching {@link RawMeta}
     */
    public static Optional<RawMeta> getByTagName (final Collection<RawMeta> rawMetas, final String name)
    {
        return rawMetas.stream() //
                       .filter(rawMeta -> Objects.equals(rawMeta.getName(), name)) //
                       .findAny();
    }

    /**
     * Get a {@link RawMeta} element with a matching name
     * 
     * @param rawMetas {@link Collection} of {@link RawMeta} elements
     * @param pattern Name {@link Pattern} to look for
     * @return Any matching {@link RawMeta}
     */
    public static Optional<RawMeta> getAnyByTagName (final Collection<RawMeta> rawMetas, final Pattern pattern)
    {
        return getAllByTagName(rawMetas, pattern).findAny();
    }
    
    /**
     * Get all {@link RawMeta} elements with matching names
     * 
     * @param rawMetas {@link Collection} of {@link RawMeta} elements
     * @param pattern Name {@link Pattern} to look for
     * @return Any matching {@link RawMeta}
     */
    public static Stream<RawMeta> getAllByTagName (final Collection<RawMeta> rawMetas, final Pattern pattern)
    {
        return rawMetas.stream() //
                       .filter(rawMeta -> rawMeta.getName() != null) //
                       .filter(rawMeta -> pattern.matcher(rawMeta.getName()).matches());
    }

}
