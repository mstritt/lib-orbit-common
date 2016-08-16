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

package com.actelion.research.orbit.utils;

import com.actelion.research.orbit.beans.RawMeta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * class for creating RawMeta instances.
 */
public class RawMetaFactoryData {

    private int rawDataId = 0;
    private Date modifyDate = null;
    private String userId = "";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //  'at' HH:mm:ss
    private static final DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.LONG, Locale.US); // flipr date

    public RawMetaFactoryData(int rawDataId, Date modifyDate, String userId) {
        this.rawDataId = rawDataId;
        this.modifyDate = modifyDate;
        this.userId = userId;
    }

    public RawMeta createMetaStr(String name, String value) {
        RawMeta rm = new RawMeta();
        rm.setName(name);
        rm.setValue(value);
        rm.setRawTypeId(RawDbTypes.RAW_TYPE_STRING);
        rm.setModifyDate(modifyDate);
        rm.setRawDataId(rawDataId);
        rm.setUserId(userId);
        return rm;
    }

    public RawMeta createMetaInt(String name, int value) {
        RawMeta rm = new RawMeta();
        rm.setName(name);
        rm.setValue(Integer.toString(value));
        rm.setRawTypeId(RawDbTypes.RAW_TYPE_INTEGER);
        rm.setModifyDate(modifyDate);
        rm.setRawDataId(rawDataId);
        rm.setUserId(userId);
        return rm;
    }

    public RawMeta createMetaDouble(String name, double value) {
        RawMeta rm = new RawMeta();
        rm.setName(name);
        rm.setValue(Double.toString(value));
        rm.setRawTypeId(RawDbTypes.RAW_TYPE_DOUBLE);
        rm.setModifyDate(modifyDate);
        rm.setRawDataId(rawDataId);
        rm.setUserId(userId);
        return rm;
    }

    public RawMeta createMetaDate(String name, Date value) {
        RawMeta rm = new RawMeta();
        rm.setName(name);
        rm.setValue(dateFormat.format(value));
        rm.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
        rm.setModifyDate(modifyDate);
        rm.setRawDataId(rawDataId);
        rm.setUserId(userId);
        return rm;
    }


    /**
     * Creates a RawMeta instance. It will try to detect the data type automatically.
     *
     * @param name
     * @param value
     * @return
     */
    public RawMeta createMetaAuto(String name, String value) {
        RawMeta rm = new RawMeta();
        rm.setName(name);
        rm.setModifyDate(modifyDate);
        rm.setRawDataId(rawDataId);
        rm.setUserId(userId);
        rm.setRawTypeId(RawDbTypes.RAW_TYPE_STRING);
        String val = value.trim();

        // is it an int?
        boolean dtFound = false;
        try {
            Integer i = Integer.parseInt(val);
            val = i.toString();
            rm.setRawTypeId(RawDbTypes.RAW_TYPE_INTEGER);
            dtFound = true;
        } catch (Exception e) {
            dtFound = false;
        }

        // is it a double?
        if (!dtFound) {
            try {
                Double d = Double.parseDouble(val.replaceAll(",", "."));
                val = d.toString();
                rm.setRawTypeId(RawDbTypes.RAW_TYPE_DOUBLE);
                dtFound = true;
            } catch (Exception e) {
                dtFound = false;
            }
        } // if not dtFound


        // is it a date (dateFormat1)?
        if (!dtFound) {
            try {
                Date d = dateFormat.parse(val.replaceAll("/", ".").replaceAll("-", "."));
                val = dateFormat.format(d);
                rm.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
                dtFound = true;
            } catch (Exception e) {
                dtFound = false;
            }
        } // if not dtFound

        // is it a date (dateFormat2)?
        if (!dtFound) {
            try {
                Date d = dateFormat2.parse(val.replaceAll("/", ".").replaceAll("-", "."));
                val = dateFormat.format(d); // always use standard dateFormat
                rm.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
                dtFound = true;
            } catch (Exception e) {
                dtFound = false;
            }
        } // if not dtFound


        // else it remains a string attribute
        rm.setValue(value);
        return rm;
    }


    public int getRawDataId() {
        return rawDataId;
    }

    public void setRawDataId(int rawDataId) {
        this.rawDataId = rawDataId;
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


}
