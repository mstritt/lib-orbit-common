/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2017 Actelion Pharmaceuticals Ltd., Gewerbestrasse 16, CH-4123 Allschwil, Switzerland.
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

import java.awt.image.ColorModel;
import java.awt.image.Raster;

/**
 * Orbit image interface for multichannel images, e.g. fluorescence images.
 * Extends IOrbitImage and provides additional methods for multi-channel handling.
 */
public interface IOrbitImageMultiChannel extends IOrbitImage {
    ColorModel getColorModel();
    Raster getTileData(int tileX, int tileY, float[] channelContributions);
    String[] getChannelNames();
    void setChannelNames(String[] channelNames);
    float[] getChannelContributions();
    void setChannelContributions(float[] contributions);
    float[] getHues(); // hue values per channel
    void setHues(float[] hues); // hue values per channel
}
