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

import java.io.File;
import java.io.FileFilter;

public class OrbitFileFilter implements FileFilter {

    private static String[] supportedFileExtensions = new String[]{"tif", "tiff", "jpg", "png", "gif", "bmp", "svs", "dcm", "pcx", "jp2"};

    @Override
    public boolean accept(File pathname) {
        //If a file or directory is hidden, or unreadable, don't show it in the list.
        if (pathname.isHidden())
            return false;
        if (!pathname.canRead())
            return false;
        if (pathname.isDirectory()) return true;

        String fileName = pathname.getName();

        for (String thumb : RawUtilsCommon.STR_THUMBS) {
            if (fileName.contains(thumb)) return false;
        }

        // only list files containing max one point, e.g. 12345.1.tif will be excluded
        String[] split = fileName.split("\\.", -1);
        if (split != null && split.length > 2) return false;

        String fileExtension;
        int mid = fileName.lastIndexOf(".");
        fileExtension = fileName.substring(mid + 1, fileName.length());
        for (String s : supportedFileExtensions) {
            if (s.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

}
