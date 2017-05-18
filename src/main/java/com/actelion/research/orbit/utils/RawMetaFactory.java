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

import com.actelion.research.orbit.beans.RawData;
import com.actelion.research.orbit.beans.RawDataFile;
import com.actelion.research.orbit.beans.RawMeta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Abstract base class for classes creating {@link RawMeta} objects.
 * 
 * @author patrick.rammelt@actelion.com
 */
public abstract class RawMetaFactory
{
    /** {@link Date} to be set to new {@link RawMeta}s by {@link RawMeta#setModifyDate(Date)} */
    protected Date modifyDate = null;

    /** User Id to be set to new {@link RawMeta}s by {@link RawMeta#setUserId(String)} */
    protected String userId = "";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);

    /**
     * {@link RawMetaFactory} constructor
     * 
     * @param modifyDate {@link #modifyDate}
     * @param userId {@link #userId}
     */
    public RawMetaFactory (final Date modifyDate, final String userId)
    {
        this.modifyDate = modifyDate;
        this.userId = userId;
    }

    /** Create a {@link RawMeta} by the given {@code name} holding the given {@link String} */
    public RawMeta createMetaStr (final String name, final String value)
    {
        final RawMeta rawMeta = new RawMeta();
        rawMeta.setName(name);
        rawMeta.setValue(value);
        rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_STRING);
        rawMeta.setModifyDate(modifyDate);
        rawMeta.setUserId(userId);
        link(rawMeta);
        return rawMeta;
    }

    /** Create a {@link RawMeta} by the given {@code name} holding the given {@code int} */
    public RawMeta createMetaInt (final String name, final int value)
    {
        final RawMeta rawMeta = new RawMeta();
        rawMeta.setName(name);
        rawMeta.setValue(Integer.toString(value));
        rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_INTEGER);
        rawMeta.setModifyDate(modifyDate);
        rawMeta.setUserId(userId);
        link(rawMeta);
        return rawMeta;
    }

    /** Create a {@link RawMeta} by the given {@code name} holding the given {@code double} */
    public RawMeta createMetaDouble (final String name, final double value)
    {
        final RawMeta rawMeta = new RawMeta();
        rawMeta.setName(name);
        rawMeta.setValue(Double.toString(value));
        rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_DOUBLE);
        rawMeta.setModifyDate(modifyDate);
        rawMeta.setUserId(userId);
        link(rawMeta);
        return rawMeta;
    }

    /** Create a {@link RawMeta} by the given {@code name} holding the given {@link Date} */
    public RawMeta createMetaDate (final String name, final Date value)
    {
        final RawMeta rawMeta = new RawMeta();
        rawMeta.setName(name);
        rawMeta.setValue(dateFormat.format(value));
        rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
        rawMeta.setModifyDate(modifyDate);
        rawMeta.setUserId(userId);
        link(rawMeta);
        return rawMeta;
    }

    /**
     * Create a {@link RawMeta} by the given {@code name} holding the given value, trying to detect
     * the data type automatically.
     */
    public RawMeta createMetaAuto (final String name, final String value)
    {
        final RawMeta rawMeta = new RawMeta();
        rawMeta.setName(name);
        rawMeta.setModifyDate(modifyDate);
        rawMeta.setUserId(userId);
        rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_STRING);
        link(rawMeta);

        String val = value.trim();

        // is it an int?
        boolean typeFound = false;
        try {
            Integer intValue = Integer.parseInt(val);
            val = intValue.toString();
            rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_INTEGER);
            typeFound = true;
        } catch (Exception e) {
            typeFound = false;
        }

        // is it a double?
        if (!typeFound) {
            try {
                Double doubleValue = Double.parseDouble(val.replaceAll(",", "."));
                val = doubleValue.toString();
                rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_DOUBLE);
                typeFound = true;
            } catch (Exception e) {
                typeFound = false;
            }
        } // if not dtFound

        // is it a date (dateFormat1)?
        if (!typeFound) {
            try {
                Date date = dateFormat.parse(val.replaceAll("/", ".").replaceAll("-", "."));
                val = dateFormat.format(date);
                rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
                typeFound = true;
            } catch (Exception e) {
                typeFound = false;
            }
        } // if not dtFound

        // is it a date (dateFormat2)?
        if (!typeFound) {
            try {
                Date date = dateFormat2.parse(val.replaceAll("/", ".").replaceAll("-", "."));
                val = dateFormat.format(date); // always use standard dateFormat
                rawMeta.setRawTypeId(RawDbTypes.RAW_TYPE_DATE);
                typeFound = true;
            } catch (Exception e) {
                typeFound = false;
            }
        } // if not dtFound

        // else it remains a string attribute
        rawMeta.setValue(value);
        return rawMeta;
    }

    /** Get the {@link #modifyDate} */
    public Date getModifyDate ()
    {
        return modifyDate;
    }

    /** Set the {@link #modifyDate} */
    public void setModifyDate (Date modifyDate)
    {
        this.modifyDate = modifyDate;
    }

    /** Get the {@link #userId} */
    public String getUserId ()
    {
        return userId;
    }

    /** Set the {@link #userId} */
    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    /**
     * Sets the Id of the parent element (either a {@link RawData} Id or a {@link RawDataFile} Id)
     * to the given {@link RawMeta} object.
     */
    protected abstract void link (final RawMeta rawMeta);

}
