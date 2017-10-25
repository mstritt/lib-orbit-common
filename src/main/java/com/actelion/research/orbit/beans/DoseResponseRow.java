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
import java.text.ParseException;

/**
 * Represents one DoseResponse data row as specified in the Orbit DoseResponse Format Specification.
 * Strings must not contain tabulators!
 */
public class DoseResponseRow implements Serializable, Comparable<DoseResponseRow> {
    public static final char DoseResponseSeparator = '\t';
    private static final long serialVersionUID = 1L;

    public enum wellTypes {p0, p100, dose, doseAndP0, doseAndP100}

    public enum compoundTypes {compound, agonist, antagonist}

    private String containerName = "";
    private String wellName = "";
    private String wellType = "";
    private String compoundName = "";
    private String tubeId = "";
    private int compoundId = 0;
    private String compoundType = "";
    private double concentrationFactor = 0d;
    private double concentration = 1d;
    private String group = "";
    private String measurementName = "";
    private double measurementValue = Double.NaN;
    private int substanceNo = -1;
    private int replicateNo = 0;

    public DoseResponseRow() {

    }

    public DoseResponseRow(String containerName, String wellName, String wellType, String compoundName, String tubeId, int compoundId, String compoundType, int substanceNo, int replicateNo, double concentration, double concentrationFactor, String group, String measurementName, double measurementValue) {
        this.containerName = containerName;
        this.wellName = wellName;
        this.wellType = wellType;
        this.compoundName = compoundName;
        this.tubeId = tubeId;
        this.compoundId = compoundId;
        this.compoundType = compoundType;
        this.substanceNo = substanceNo;
        this.replicateNo = replicateNo;
        this.concentration = concentration;
        this.concentrationFactor = concentrationFactor;
        this.group = group;
        this.measurementName = measurementName;
        this.measurementValue = measurementValue;
    }

    /**
     * Parses a DoseResponseRow.toString() string and create a DoseResponseRow instance.<br/>
     * compundId can be missing, then it will be set to 0.<br/>
     * measurementValue can be missing, then it will be set to Double.NaN.<br/>
     * Returns null if s is null.
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static DoseResponseRow parseDoseResponseRow(String s) throws ParseException {
        if (s == null) return null;
        String[] split = s.split(String.valueOf(DoseResponseSeparator), -1);
        if (split == null || split.length < 13)
            throw new ParseException("argument is not a valid DoseResponseRow (null or length<12)", 0);
        double mValue = Double.NaN;
        try {
            mValue = Double.parseDouble(split[13]);
        } catch (Exception e) {
            mValue = Double.NaN;
        }
        int cId;
        try {
            cId = Integer.parseInt(split[5]);
        } catch (Exception e) {
            cId = 0;
        }
        int substanceNo;
        try {
            substanceNo = Integer.parseInt(split[7]);
        } catch (Exception e) {
            substanceNo = -1;
        }
        int replicateNo;
        try {
            replicateNo = Integer.parseInt(split[8]);
        } catch (Exception e) {
            replicateNo = 0;
        }
        return new DoseResponseRow(split[0], split[1], split[2], split[3], split[4], cId, split[6], substanceNo, replicateNo, Double.parseDouble(split[9]), Double.parseDouble(split[10]), split[11], split[12], mValue);
    }

    /**
     * Returns one data line as a string with respect to DoseResponseSeparator.<br/>
     */
    @Override
    public String toString() {
        String s = containerName + DoseResponseSeparator +
                wellName + DoseResponseSeparator +
                wellType + DoseResponseSeparator +
                compoundName + DoseResponseSeparator +
                tubeId + DoseResponseSeparator +
                compoundId + DoseResponseSeparator +
                compoundType + DoseResponseSeparator +
                substanceNo + DoseResponseSeparator +
                replicateNo + DoseResponseSeparator +
                concentration + DoseResponseSeparator +
                concentrationFactor + DoseResponseSeparator +
                group + DoseResponseSeparator +
                measurementName + DoseResponseSeparator +
                measurementValue;
        return s;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoseResponseRow that = (DoseResponseRow) o;


        if (compoundId != that.compoundId) return false;
        if (Double.compare(that.substanceNo, substanceNo) != 0) return false;
        if (Double.compare(that.replicateNo, replicateNo) != 0) return false;
        if (Double.compare(that.concentration, concentration) != 0) return false;
        if (Double.compare(that.concentrationFactor, concentrationFactor) != 0) return false;
        if (Double.compare(that.measurementValue, measurementValue) != 0) return false;
        if (compoundType != null ? !compoundType.equals(that.compoundType) : that.compoundType != null) return false;
        if (containerName != null ? !containerName.equals(that.containerName) : that.containerName != null)
            return false;
        if (group != null ? !group.equals(that.group) : that.group != null)
            return false;
        if (measurementName != null ? !measurementName.equals(that.measurementName) : that.measurementName != null)
            return false;
        if (compoundName != null ? !compoundName.equals(that.compoundName) : that.compoundName != null) return false;
        if (tubeId != null ? !tubeId.equals(that.tubeId) : that.tubeId != null) return false;
        if (wellName != null ? !wellName.equals(that.wellName) : that.wellName != null) return false;

        return wellType != null ? wellType.equals(that.wellType) : that.wellType == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = containerName != null ? containerName.hashCode() : 0;
        result = 31 * result + (wellName != null ? wellName.hashCode() : 0);
        result = 31 * result + (wellType != null ? wellType.hashCode() : 0);
        result = 31 * result + (compoundName != null ? compoundName.hashCode() : 0);
        result = 31 * result + (tubeId != null ? tubeId.hashCode() : 0);
        result = 31 * result + compoundId;
        result = 31 * result + (compoundType != null ? compoundType.hashCode() : 0);
        result = 31 * result + substanceNo;
        result = 31 * result + replicateNo;
        temp = Double.doubleToLongBits(concentration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(concentrationFactor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (measurementName != null ? measurementName.hashCode() : 0);
        temp = Double.doubleToLongBits(measurementValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public int compareTo(DoseResponseRow that) {
        if (this.containerName.compareTo(that.containerName) < 0) {
            return -1;
        } else if (this.containerName.compareTo(that.containerName) > 0) {
            return 1;
        }

        if (this.wellName.compareTo(that.wellName) < 0) {
            return -1;
        } else if (this.wellName.compareTo(that.wellName) > 0) {
            return 1;
        }

        if (this.wellType.compareTo(that.wellType) < 0) {
            return -1;
        } else if (this.wellType.compareTo(that.wellType) > 0) {
            return 1;
        }

        if (this.compoundType.compareTo(that.compoundType) < 0) {
            return -1;
        } else if (this.compoundType.compareTo(that.compoundType) > 0) {
            return 1;
        }

        if (this.group.compareTo(that.group) < 0) {
            return -1;
        } else if (this.group.compareTo(that.group) > 0) {
            return 1;
        }

        if (this.measurementName.compareTo(that.measurementName) < 0) {
            return -1;
        } else if (this.measurementName.compareTo(that.measurementName) > 0) {
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

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getWellType() {
        return wellType;
    }

    public void setWellType(String wellType) {
        this.wellType = wellType;
    }

    public String getCompoundName() {
        return compoundName;
    }

    public void setCompoundName(String compoundName) {
        this.compoundName = compoundName;
    }

    public String getTubeId() {
        return tubeId;
    }

    public void setTubeId(String tubeId) {
        this.tubeId = tubeId;
    }

    public int getCompoundId() {
        return compoundId;
    }

    public void setCompoundId(int compoundId) {
        this.compoundId = compoundId;
    }

    public String getCompoundType() {
        return compoundType;
    }

    public void setCompoundType(String compoundType) {
        this.compoundType = compoundType;
    }

    public double getConcentration() {
        return concentration;
    }

    public void setConcentration(double concentration) {
        this.concentration = concentration;
    }

    public double getConcentrationFactor() {
        return concentrationFactor;
    }

    public void setConcentrationFactor(double concentrationFactor) {
        this.concentrationFactor = concentrationFactor;
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName = measurementName;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public int getReplicateNo() {
        return replicateNo;
    }

    public void setReplicateNo(int replicateNo) {
        this.replicateNo = replicateNo;
    }

    public int getSubstanceNo() {
        return substanceNo;
    }

    public void setSubstanceNo(int substanceNo) {
        this.substanceNo = substanceNo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
