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
import java.util.Arrays;
import java.util.Date;


/**
 * Class representing a RawAnnotation object for a RawDataFile. This is a generic class that with the annotation data as byte array
 * and should be wrapped to particular annotation classes like ImageAnnotation, which e.g. interprets the annotation data as
 * points in a polygon.
 * In inherited classes use the equalsInherited and hashcodeInherited methods to avoid the usage of the expensive getData() method.
 */
public class RawAnnotation implements Serializable, Cloneable {

    public static final int ANNOTATION_TYPE_IMAGE = 0;
    public static final int ANNOTATION_TYPE_NMR = 1;
    public static final int ANNOTATION_TYPE_BINARY = 2;
    public static final int ANNOTATION_TYPE_SPOT = 3; // e.g. bloodsmear blue nuclei spots
    public static final int ANNOTATION_TYPE_MANUALCLASS = 4; // e.g. manual classification spots
    public static final int ANNOTATION_TYPE_TMASPOT = 5; // TMA Spots
    public static final int ANNOTATION_TYPE_DOSERESPONSE = 6; // IC50Studio parameter
    public static final int ANNOTATION_TYPE_MODEL = 7;
    public static final int ANNOTATION_TYPE_JSON = 8;
    public static final int ANNOTATION_TYPE_XML = 9;
    private static final long serialVersionUID = 1L;
    protected int rawAnnotationId = 0;
    protected int rawDataFileId = 0;
    protected int rawAnnotationType = ANNOTATION_TYPE_BINARY;
    protected String description = "";
    protected byte[] data = null;
    protected String userId = "";
    protected Date modifyDate = null;


    public RawAnnotation() {
        modifyDate = new Date(System.currentTimeMillis());
    }


    public int getRawAnnotationId() {
        return rawAnnotationId;
    }


    public void setRawAnnotationId(int rawAnnotationId) {
        this.rawAnnotationId = rawAnnotationId;
    }


    public int getRawDataFileId() {
        return rawDataFileId;
    }


    public void setRawDataFileId(int rawDataFileId) {
        this.rawDataFileId = rawDataFileId;
    }


    public int getRawAnnotationType() {
        return rawAnnotationType;
    }


    public void setRawAnnotationType(int rawAnnotationType) {
        this.rawAnnotationType = rawAnnotationType;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public byte[] getData() {
        return data;
    }


    public void setData(byte[] data) {
        this.data = data;
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


    /**
     * use getData() (not data) to get the overwritten mothod
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(getData());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + rawAnnotationId;
        result = prime * result + rawAnnotationType;
        result = prime * result + rawDataFileId;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /**
     * Hashcode without using the data array. Use the concrete (hashcode-)implementation of the data object in the inherited class.
     *
     * @return
     * @See ImageAnnotation
     */
    public int hashCodeInherited() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + rawAnnotationId;
        result = prime * result + rawAnnotationType;
        result = prime * result + rawDataFileId;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }


    /**
     * use getData() (not data) to get the overwritten mothod
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RawAnnotation))
            return false;
        RawAnnotation other = (RawAnnotation) obj;
        if (!Arrays.equals(getData(), other.getData()))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (rawAnnotationId != other.rawAnnotationId)
            return false;
        if (rawAnnotationType != other.rawAnnotationType)
            return false;
        if (rawDataFileId != other.rawDataFileId)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    /**
     * Equals without using the data array. Use the concrete (equals-)implementation of the data object in the inherited class.
     *
     * @return
     * @See ImageAnnotation
     */
    public boolean equalsInherited(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RawAnnotation))
            return false;
        RawAnnotation other = (RawAnnotation) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (rawAnnotationId != other.rawAnnotationId)
            return false;
        if (rawAnnotationType != other.rawAnnotationType)
            return false;
        if (rawDataFileId != other.rawDataFileId)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }


    public String toString() {
        return "RDF:" + rawDataFileId + " | Type:" + rawAnnotationType + " | Description:" + description + " | ModifyDate:" + modifyDate + " | User:" + userId;
    }

    public RawAnnotation clone() {
        try {
            RawAnnotation anno = (RawAnnotation) super.clone();
            anno.setDescription(new String(description));
            if (modifyDate != null)
                anno.setModifyDate((Date) modifyDate.clone());
            anno.setUserId(new String(userId));
            if (data != null)
                anno.setData(Arrays.copyOf(data, data.length));
            return anno;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
