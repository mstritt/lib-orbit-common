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

import java.util.Date;

/**
 * Factory class to create {@link RawMeta} instances for {@link RawData} objects.
 * 
 * @author patrick.rammelt@actelion.com (revised)
 */
public class RawMetaFactoryData extends RawMetaFactory
{
    /** Id to be set to new {@link RawMeta} objects by {@link RawMeta#setRawDataId(int)} */
    private int rawDataId = 0;

    /**
     * {@link RawMetaFactoryData} constructor
     * 
     * @param rawDataId {@link #rawDataId}
     * @param modifyDate {@link RawMetaFactory#modifyDate}
     * @param userId {@link RawMetaFactory#userId}
     */
    public RawMetaFactoryData (final int rawDataId, final Date modifyDate, final String userId)
    {
        super(modifyDate, userId);
        this.rawDataId = rawDataId;
    }

    /** Get the {@link #rawDataId} */
    public int getRawDataId ()
    {
        return rawDataId;
    }

    /** Set the {@link #rawDataId} */
    public void setRawDataId (final int rawDataId)
    {
        this.rawDataId = rawDataId;
    }

    @Override
    protected void link (final RawMeta rawMeta)
    {
        rawMeta.setRawDataId(rawDataId);
    }

}
