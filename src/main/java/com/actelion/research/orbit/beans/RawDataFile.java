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

import java.io.Serializable;
import java.util.Date;

/**
 * Bean representing a RawDataFile. Will be attached to RawData objects.
 */
public class RawDataFile implements Serializable, Cloneable {

    public static final int Flag_AllOff = 0;
    public static final int Flag_ID_NOT_FINAL = 1;
    public static final int Flag_IS_CONVERTED = 2;
    public static final int Flag_HAS_PYRAMID = 4;
    public static final int Flag_HAS_LINKED_CHANNELS = 8;
    public static final int Flag_CURRENTLY_UPLOADING = 16;
    public static final int Flagbit6 = 32;
    public static final int Flagbit7 = 64;
    public static final int Flagbit8 = 128;

    private static final long serialVersionUID = 1L;
    protected int frameNum = 0; // will not be persisted!!!
    protected int seriesNum = 0; // will not be persisted!!!
    protected int wvlengthNum = 0; // will not be persisted!!! - needed for the histogram extraction during the HCS crawling procedure
    protected int rawDataFileId = 0;
    protected int rawDataId = 0;
    protected int bioSampleId = 0;
    protected String dataPath = "";
    protected String fileName = "";
    protected long fileSize = 0;
    protected String fileType = "";
    protected int flags = 0;
    protected Date referenceDate = null;
    protected Date modifyDate = null;
    protected String userId = "";
    protected String md5 = "";

    public RawDataFile() {
        referenceDate = new Date(System.currentTimeMillis());
        modifyDate = new Date(System.currentTimeMillis());
    }


    public int getRawDataFileId() {
        return rawDataFileId;
    }

    public void setRawDataFileId(int rawDataFileId) {
        this.rawDataFileId = rawDataFileId;
    }

    public int getRawDataId() {
        return rawDataId;
    }

    public void setRawDataId(int rawDataId) {
        this.rawDataId = rawDataId;
    }

    public String getDataPath() {
        // orbitVol->orbitvol bugfix
        if (dataPath != null) return dataPath.replaceAll("orbitVol", "orbitvol");
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
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


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * SampleID is not valid anymore - use barcode meta data instead
     *
     * @return
     */
    @Deprecated
    public int getBioSampleId() {
        return bioSampleId;
    }


    /**
     * SampleID is not valid anymore - use barcode meta data instead
     *
     * @return
     */
    @Deprecated
    public void setBioSampleId(int bioSampleId) {
        this.bioSampleId = bioSampleId;
    }


    public String toString() {
        return getFileName();
    }

    public String toStringDetail() {
        return "RawDataFile{" +
                "bioSampleId=" + bioSampleId +
                ", frameNum=" + frameNum +
                ", seriesNum=" + seriesNum +
                ", rawDataFileId=" + rawDataFileId +
                ", rawDataId=" + rawDataId +
                ", dataPath='" + dataPath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", flags=" + flags +
                ", referenceDate=" + referenceDate +
                ", modifyDate=" + modifyDate +
                ", userId='" + userId + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }


    public boolean isImage() {
        return fileType.equals(RawUtilsCommon.DATA_TYPE_IMAGE_JPG) || fileType.equals(RawUtilsCommon.DATA_TYPE_IMAGE_TIFF) || fileType.equals(RawUtilsCommon.DATA_TYPE_IMAGE_JPEG2000);
    }


    public String getEnding() {
        if (getFileName() == null || getFileName().length() <= 0) return "";
        String[] split = getFileName().split("\\.");
        if (split != null && split.length > 1) {
            return split[split.length - 1];
        } else return "";
    }

    public void setFlagBit(int flag) {
        setFlags(flags | flag);
    }

    public void unsetFlagBit(int flag) {
        setFlags(flags & ~flag);
    }

    public boolean isFlagBit(int flag) {
        return (flags & flag) == flag;
    }


    public int getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(int frameNum) {
        this.frameNum = frameNum;
    }

    public int getSeriesNum() {
        return seriesNum;
    }

    public void setSeriesNum(int seriesNum) {
        this.seriesNum = seriesNum;
    }

    public int getWvlengthNum() {
        return wvlengthNum;
    }

    public void setWvlengthNum(int wvlengthNum) {
        this.wvlengthNum = wvlengthNum;
    }

    public RawDataFile clone() {
        try {
            return (RawDataFile) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bioSampleId;
        result = prime * result + ((dataPath == null) ? 0 : dataPath.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + (int) (fileSize ^ (fileSize >>> 32));
        result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
        result = prime * result + flags;
        result = prime * result + frameNum;
        result = prime * result + ((modifyDate == null) ? 0 : modifyDate.hashCode());
        result = prime * result + rawDataFileId;
        result = prime * result + rawDataId;
        result = prime * result + ((referenceDate == null) ? 0 : referenceDate.hashCode());
        result = prime * result + seriesNum;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((md5 == null) ? 0 : md5.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RawDataFile))
            return false;
        RawDataFile other = (RawDataFile) obj;
        if (bioSampleId != other.bioSampleId)
            return false;
        if (dataPath == null) {
            if (other.dataPath != null)
                return false;
        } else if (!dataPath.equals(other.dataPath))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        if (fileSize != other.fileSize)
            return false;
        if (fileType == null) {
            if (other.fileType != null)
                return false;
        } else if (!fileType.equals(other.fileType))
            return false;
        if (flags != other.flags)
            return false;
        if (frameNum != other.frameNum)
            return false;
        if (modifyDate == null) {
            if (other.modifyDate != null)
                return false;
        } else if (!modifyDate.equals(other.modifyDate))
            return false;
        if (rawDataFileId != other.rawDataFileId)
            return false;
        if (rawDataId != other.rawDataId)
            return false;
        if (referenceDate == null) {
            if (other.referenceDate != null)
                return false;
        } else if (!referenceDate.equals(other.referenceDate))
            return false;
        if (seriesNum != other.seriesNum)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (md5 == null) {
            if (other.md5 != null)
                return false;
        } else if (!md5.equals(other.md5))
            return false;
        return true;
    }


    protected boolean compStr(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        else if (s1 != null && s2 == null) return false;
        else return s1.equals(s2);
    }

    protected int hashObj(Object obj) {
        if (obj == null) return 0;
        else return obj.hashCode();
    }

}
