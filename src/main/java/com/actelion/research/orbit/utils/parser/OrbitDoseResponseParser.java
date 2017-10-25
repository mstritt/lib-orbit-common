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

package com.actelion.research.orbit.utils.parser;

import com.actelion.research.orbit.beans.DoseResponseContainer;
import com.actelion.research.orbit.beans.DoseResponseRow;
import com.actelion.research.orbit.utils.RawUtilsCommon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrbitDoseResponseParser implements IDoseResponseParser {

    private static final String TestFile = "D:\\temp\\drplate.dr384";

    public List<DoseResponseContainer> parseFile(String fileName) throws Exception {
        List<DoseResponseContainer> containerList = new ArrayList<DoseResponseContainer>();
        BufferedReader reader = null;
        try {
            Date date = new Date(new File(fileName).lastModified());
            reader = new BufferedReader(new FileReader(fileName));
            String plateFormat = RawUtilsCommon.PlateFormat_384;              // .dr384
            String ending = RawUtilsCommon.getExtension(fileName);
            if (ending.contains(RawUtilsCommon.PlateFormat_96)) plateFormat = RawUtilsCommon.PlateFormat_96; // .dr96
            String line;
            // heaser
            while (!(line = reader.readLine()).contains("ContainerName")) {
                // skip empty lines lines
            }
            // data
            DoseResponseContainer container = null;
            while ((line = reader.readLine()) != null) {
                DoseResponseRow row = DoseResponseRow.parseDoseResponseRow(line);
                if (container == null || (!container.getContainerName().equals(row.getContainerName()))) {
                    container = new DoseResponseContainer(row.getContainerName(), date, new ArrayList<DoseResponseRow>(), plateFormat);
                    containerList.add(container);
                }
                container.getData().add(row);
            }
//				if (container!=null) {
//					containerList.add(container);
//				}
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e) {
            }
        }
        return containerList;
    }


    public boolean acceptFile(File f) {
        if (!f.isFile()) return false;
        return f.getName().toLowerCase().endsWith(".dr" + RawUtilsCommon.PlateFormat_384) || f.getName().toLowerCase().endsWith(".dr" + RawUtilsCommon.PlateFormat_96);
    }

    public String getNewFileName(String fileName, int frameNum) {
        String fn = fileName.replaceAll("(?i).txt", "-" + frameNum).replaceAll("(?i).dr384", "-" + frameNum).replaceAll("(?i).dr96", "-" + frameNum);
        String plateFormat = RawUtilsCommon.PlateFormat_384;
        try {
            DoseResponseContainer container = parseFile(fileName).get(frameNum);
            fn = container.getContainerName();
            plateFormat = container.getPlateFormat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fn += ".dr" + plateFormat;
        return fn;
    }

    public String getParserName() {
        return "Orbit Dose Response Format";
    }

    @Override
    public String toString() {
        return getParserName();
    }


    public static void main(String[] args) throws Exception {
        OrbitDoseResponseParser parser = new OrbitDoseResponseParser();
        List<DoseResponseContainer> containers = parser.parseFile(TestFile);
        System.out.println("NumContainer: " + containers.size());
        for (DoseResponseContainer container : containers) {
            System.out.println(container.getDataAsString());
        }
    }


}
