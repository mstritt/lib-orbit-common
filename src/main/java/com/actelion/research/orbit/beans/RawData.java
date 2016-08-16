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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing a container for raw data files. This class contains all the meta data that is
 * common for all data providers. Further meta data is stored in RawMeta objects. The files are stored
 * in DataFile objects.
 */
public class RawData implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private int rawDataId = 0;
    private String bioLabJournal = "";
    private String chemLabJournal = "";
    private String origin = "";
    private String labname = "";
    private String userId = "";
    private Date referenceDate = null;
    private Date modifyDate = null;
    private int durability = 0;
    private int rating = 0;
    private String keywords = "";
    private String description = "";
    private String comment = "";
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public RawData() {

    }

    public int getRawDataId() {
        return rawDataId;
    }

    public void setRawDataId(int rawDataId) {
        this.rawDataId = rawDataId;
    }

    public String getBioLabJournal() {
        return bioLabJournal;
    }

    public void setBioLabJournal(String bioLabJournal) {
        this.bioLabJournal = bioLabJournal;
    }

    public String getChemLabJournal() {
        return chemLabJournal;
    }

    public void setChemLabJournal(String chemLabJournal) {
        this.chemLabJournal = chemLabJournal;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLabname() {
        return labname;
    }

    public void setLabname(String labname) {
        this.labname = labname;
    }

    public String toStringPlain() {
        if (this.getBioLabJournal() != null && this.getBioLabJournal().length() > 0)
            return this.getBioLabJournal();
        else if (this.getChemLabJournal() != null && this.getChemLabJournal().length() > 0)
            return this.getChemLabJournal();
        else
            return Integer.toString(this.getRawDataId());
    }

    public String toString() {
        return toStringPlain() + " (" + dateFormat.format(getReferenceDate()) + ")";
    }


    public String toStringDetail() {
        return "id:" + rawDataId + " | bioLabJournal:" + bioLabJournal + " | chemLabJournal:" + chemLabJournal + " | userId:" + userId + " | modifyDate: " + modifyDate + " | labName:" + labname + " | origin:" + origin + " | durability:" + durability + " | rating:" + rating;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bioLabJournal == null) ? 0 : bioLabJournal.hashCode());
        result = prime * result + ((chemLabJournal == null) ? 0 : chemLabJournal.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + durability;
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((labname == null) ? 0 : labname.hashCode());
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + rating;
        result = prime * result + rawDataId;
        result = prime * result + ((referenceDate == null) ? 0 : referenceDate.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RawData))
            return false;
        RawData other = (RawData) obj;
        if (bioLabJournal == null) {
            if (other.bioLabJournal != null)
                return false;
        } else if (!bioLabJournal.equals(other.bioLabJournal))
            return false;
        if (chemLabJournal == null) {
            if (other.chemLabJournal != null)
                return false;
        } else if (!chemLabJournal.equals(other.chemLabJournal))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (durability != other.durability)
            return false;
        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;
        if (labname == null) {
            if (other.labname != null)
                return false;
        } else if (!labname.equals(other.labname))
            return false;
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (rating != other.rating)
            return false;
        if (rawDataId != other.rawDataId)
            return false;
        if (referenceDate == null) {
            if (other.referenceDate != null)
                return false;
        } else if (!referenceDate.equals(other.referenceDate))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    public RawData clone() {
        try {
            return (RawData) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
