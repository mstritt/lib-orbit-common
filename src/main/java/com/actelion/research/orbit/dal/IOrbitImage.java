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

package com.actelion.research.orbit.dal;

import com.actelion.research.orbit.exceptions.OrbitImageServletException;

import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.io.Closeable;

public interface IOrbitImage extends Closeable {
    String readInfoString(String filename) throws OrbitImageServletException;

    Raster getTileData(int tileX, int tileY);

    String getFilename();

    int getWidth();

    int getHeight();

    int getTileWidth();

    int getTileHeight();

    int getTileGridXOffset();

    int getTileGridYOffset();

    int getMinX();

    int getMinY();

    int getNumBands();

    ColorModel getColorModel();

    SampleModel getSampleModel();

    int getOriginalBitsPerSample();

    boolean getOriginalWasGrayScale();

}
