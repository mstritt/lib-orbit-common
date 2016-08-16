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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Simple Polygon annotation exchange bean. Can be used for desktop applications *and* android.
 */
public class SimplePolygonAnnotation implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int ANNOTATION_TYPE_IMAGE = 0;
    public static final int ANNOTATION_TYPE_NMR = 1;
    public static final int ANNOTATION_TYPE_BINARY = 2;
    public static final int ANNOTATION_TYPE_SPOT = 3; // e.g. bloodsmear blue nuclei spots
    public static final int ANNOTATION_TYPE_MANUALCLASS = 4; // e.g. manual classification spots
    public static final int ANNOTATION_TYPE_TMASPOT = 5; // TMA Spots

    protected int rawAnnotationId = 0;
    protected int rawDataFileId = 0;
    protected int rawAnnotationType = ANNOTATION_TYPE_BINARY;
    protected String description = "";
    protected List<int[]> xyPolyPoints = null;
    protected String userId = "";
    protected Date modifyDate = null;

    public SimplePolygonAnnotation() {

    }

    public SimplePolygonAnnotation(int rawAnnotationId, int rawDataFileId, int rawAnnotationType, String description, String userId, Date modifyDate, List<int[]> xyPolyPoints) {
        this.rawAnnotationId = rawAnnotationId;
        this.rawDataFileId = rawDataFileId;
        this.rawAnnotationType = rawAnnotationType;
        this.description = description;
        this.userId = userId;
        this.modifyDate = modifyDate;
        this.xyPolyPoints = xyPolyPoints;
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

    public List<int[]> getXyPolyPoints() {
        return xyPolyPoints;
    }

    public void setXyPolyPoints(List<int[]> xyPolyPoints) {
        this.xyPolyPoints = xyPolyPoints;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + rawAnnotationId;
        result = prime * result + rawAnnotationType;
        result = prime * result + rawDataFileId;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((xyPolyPoints == null) ? 0 : xyPolyPoints.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SimplePolygonAnnotation))
            return false;
        SimplePolygonAnnotation other = (SimplePolygonAnnotation) obj;
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
        if (xyPolyPoints == null) {
            if (other.xyPolyPoints != null)
                return false;
        } else if (!xyPolyPoints.equals(other.xyPolyPoints))
            return false;
        return true;
    }


}
