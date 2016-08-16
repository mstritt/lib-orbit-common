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

package com.actelion.research.orbit.lims;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LIMSBioSample implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id = 0;
    private String sampleId = "";
    private String name = "";
    private String elb = "";
    private String biotype = "";
    private String studyId = "";
    private String studyIVV = "";
    private String studyGroup = "";
    private String studyPhase = "";
    private String parentId = "";
    private String parentSampleId = "";
    private String parentName = "";
    private String containerId = "";
    private String containerType = "";
    private String containerLocation = "";
    private String creUser = "";
    private Date creDate = new Date();
    private String comment = "";
    private final Map<String, String> metaData = new HashMap<String, String>();
    private final Map<String, String> parentMetaData = new HashMap<String, String>();

    public LIMSBioSample() {

    }


    @Override
    public String toString() {
        return "LIMSBioSample{" +
                "biotype='" + biotype + '\'' +
                ", id=" + id +
                ", sampleId='" + sampleId + '\'' +
                ", name='" + name + '\'' +
                ", elb='" + elb + '\'' +
                ", studyId='" + studyId + '\'' +
                ", studyIVV='" + studyIVV + '\'' +
                ", studyGroup='" + studyGroup + '\'' +
                ", studyPhase='" + studyPhase + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentSampleId='" + parentSampleId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", containerId='" + containerId + '\'' +
                ", containerType='" + containerType + '\'' +
                ", containerLocation='" + containerLocation + '\'' +
                ", creUser='" + creUser + '\'' +
                ", creDate=" + creDate +
                ", metaData=" + metaData +
                ", parentMetaData=" + parentMetaData +
                ", comment=" + comment +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElb() {
        return elb;
    }

    public void setElb(String elb) {
        this.elb = elb;
    }

    public String getBiotype() {
        return biotype;
    }

    public void setBiotype(String biotype) {
        this.biotype = biotype;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }

    public String getStudyPhase() {
        return studyPhase;
    }

    public void setStudyPhase(String studyPhase) {
        this.studyPhase = studyPhase;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentSampleId() {
        return parentSampleId;
    }

    public void setParentSampleId(String parentSampleId) {
        this.parentSampleId = parentSampleId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getContainerLocation() {
        return containerLocation;
    }

    public void setContainerLocation(String containerLocation) {
        this.containerLocation = containerLocation;
    }

    public String getCreUser() {
        return creUser;
    }

    public void setCreUser(String creUser) {
        this.creUser = creUser;
    }

    public String getStudyIVV() {
        return studyIVV;
    }

    public void setStudyIVV(String studyIVV) {
        this.studyIVV = studyIVV;
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public Map<String, String> getParentMetaData() {
        return parentMetaData;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
