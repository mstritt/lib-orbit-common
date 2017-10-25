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

package com.actelion.research.orbit.utils;

import com.actelion.research.orbit.beans.RawMeta;

import java.io.Serializable;
import java.util.Date;

/**
 * Factory class to create {@link RawMeta} instances for {@link RawDataFile} objects.
 * 
 * @author patrick.rammelt@actelion.com (revised)
 */
public class RawMetaFactoryFile extends RawMetaFactory implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** Id to to be set to new {@link RawMeta} objects by {@link RawMeta#setRawDataFileId(int)} */
    private int rawDataFileId = 0;

    /**
     * {@link RawMetaFactoryFile} constructor
     * 
     * @param rawDataFileId {@link #rawDataFileId}
     * @param modifyDate {@link RawMetaFactory#modifyDate}
     * @param userId {@link RawMetaFactory#userId}
     */
    public RawMetaFactoryFile (final int rawDataFileId, final Date modifyDate, final String userId)
    {
        super(modifyDate, userId);
        this.rawDataFileId = rawDataFileId;
    }

    /** Get the {@link #rawDataFileId} */
    public int getRawDataFileId ()
    {
        return rawDataFileId;
    }

    /** Set the {@link #rawDataFileId} */
    public void setRawDataFileId (int rawDataFileId)
    {
        this.rawDataFileId = rawDataFileId;
    }

    @Override
    protected void link (final RawMeta rawMeta)
    {
        rawMeta.setRawDataFileId(rawDataFileId);
    }

}
