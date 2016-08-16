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

import java.util.*;


public class RawMetaListBuilder {

    private static ListWithNameComparator comparator = new ListWithNameComparator();
    private static RawMetaComparator comparatorRawMeta = new RawMetaComparator();
    private static final String[] blacklistArr = new String[]{"Flipr.File", "Flipr.Read Plate name", "Flipr.Export In", "Flipr.Read Plate Barcode", "Flipr.Source Plate 1 Barcode", "Flipr.Source Plate 2 Barcode", "Flipr.Source Plate 3 Barcode", "Flipr.Source Plate 1 name", "Flipr.Source Plate 2 name", "Flipr.Source Plate 3 name", "PhotometricInterpretation", "StripOffsets", "BitsPerSample", "PlanarConfiguration"};
    private static HashSet<String> blacklist = null;
    private static Logger logger = Logger.getLogger(RawMetaListBuilder.class);

    public static List<ListWithName<RawMeta>> buildRawMetaHash(List<RawMeta> rawMetaList) {
        if (blacklist == null) { //initialize
            blacklist = new HashSet<String>(blacklistArr.length);
            for (String bs : blacklistArr) {
                blacklist.add(bs);
            }
        }

        if (rawMetaList == null) return null;
        Hashtable<String, List<RawMeta>> resHash = new Hashtable<String, List<RawMeta>>();
        for (RawMeta rm : rawMetaList) {
            String key = RawUtilsCommon.STR_GENERAL;
            String s = rm.getNameCleanSpecial();
            // discard metas in blacklist
            if (blacklist.contains(s)) continue;

            String[] splitKey = s.split("\\.");
            if (splitKey.length > 1) {
                key = splitKey[0];
                if (rm.getName() != null && splitKey[0] != null) {
                    try {
                        rm.setName(rm.getName().replaceAll(splitKey[0] + ".", ""));
                    } catch (Exception e) {
                        logger.error("error changing meta name: " + e.getMessage());
                    }
                }
            }

            if (!resHash.containsKey(key)) {
                resHash.put(key, new ArrayList<RawMeta>());
            }

            resHash.get(key).add(rm);
        }


        List<ListWithName<RawMeta>> resList = new ArrayList<ListWithName<RawMeta>>();
        for (String key : resHash.keySet()) {
            ListWithName<RawMeta> lwn = new ListWithName<RawMeta>(key, resHash.get(key));
            resList.add(lwn);
        }

        for (ListWithName<RawMeta> metaList : resList) {
            Collections.sort(metaList.getList(), comparatorRawMeta);
        }
        Collections.sort(resList, comparator);
        return resList;
    }


}
