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
import com.actelion.research.orbit.lims.LIMSBioSample;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RawUtilsCommon {
    public final static int SEARCH_LIMIT = 190;
    public final static int THUMBNAIL_WIDTH = 200;
    public static long MIN_SIZE_FOR_IMAGE_PYRAMID = 3500000L;
    public final static int RDFLIST_LIMIT = 2000;  // e.g. search limit in orbit browser (was 1000)

    public final static int LEVEL_LABEL = 1000;
    public final static int LEVEL_OVERVIEW = 1001;
    public final static int LEVEL_8BITPREVIEW = 1002;

    public final static String STR_GENERAL = "General";
    public final static String LOGO_NAME = "/Orbital_bones32.png";
    public final static String STR_FILENAME = "Filename";
    public final static String STR_META_IMAGEADJUSTMENTS = "ImageAdjustments";   // e.g. bri:0;con:232;r:0;g:0;b:0;gamma:100;deconvChan:0;deconvName:H&E
    public final static String STR_META_IMAGE_SCALE = "mMeterPerPixel"; // mu meter per pixel for gauge
    public final static String STR_META_IMAGE_CHANNEL_NAME = "Channel Name";
    public final static String STR_META_IMAGE_IMAGEWIDTH = "ImageWidth";
    public final static String STR_META_IMAGE_IMAGEHEIGHT = "ImageHeight";
    public final static String STR_META_RDF_COMMENT = "RDF Comment";

    public final static String DATA_TYPE_IMAGE_JPG = "image/jpg";
    public final static String DATA_TYPE_IMAGE_PNG = "image/png";
    public final static String DATA_TYPE_IMAGE_TIFF = "image/tiff";
    public final static String DATA_TYPE_IMAGE_JPEG2000 = "image/jp2";
    public final static String[] fileTypesImage = new String[]{DATA_TYPE_IMAGE_TIFF, DATA_TYPE_IMAGE_JPG, DATA_TYPE_IMAGE_PNG, DATA_TYPE_IMAGE_JPEG2000};
    public final static String[] STR_THUMBS = new String[]{"_thumb", "_Thumb", "-thumb", "-Thumb", "_thn", ".orbitthn"};

    public final static String STR_META_CHANNEL = "Channel";
    public final static String STR_META_BARCODE = "Barcode";


    public static final String RAWDATA_SELECTED = "raw_data_selected";
    public static final String RAWDATAFILES_SELECTED = "raw_data_files_selected";
    public static final String RAWDATAFILES_SERIES_SELECTED = "SERIES_CHANGED";

    public static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };


    // HCS specific
    public final static String PlateFormat_1536 = "1536";
    public final static String PlateFormat_384 = "384";
    public final static String PlateFormat_96 = "96";


    // LIMS specific

    public static List<RawMeta> generateBiosampleMetas(LIMSBioSample sample, RawMetaFactoryFile rmff) {
        List<RawMeta> rmList = new ArrayList<RawMeta>();
        rmList.add(rmff.createMetaAuto("Name", sample.getSampleId()));
        rmList.add(rmff.createMetaAuto("Type", sample.getBiotype()));
        rmList.add(rmff.createMetaAuto("Study ID", sample.getStudyId()));
        rmList.add(rmff.createMetaAuto("Study IVV", sample.getStudyIVV()));
        rmList.add(rmff.createMetaAuto("Study Group", sample.getStudyGroup()));
        rmList.add(rmff.createMetaAuto("Study Phase", sample.getStudyPhase()));
        rmList.add(rmff.createMetaAuto("Container Type", sample.getContainerType()));
        rmList.add(rmff.createMetaAuto("Container Location", sample.getContainerLocation()));
        rmList.add(rmff.createMetaAuto("Create User", sample.getCreUser()));
        rmList.add(rmff.createMetaDate("Create Date", sample.getCreDate()));
        rmList.add(rmff.createMetaAuto("Comment", sample.getComment()));
        Map<String, String> metas = sample.getMetaData();
        for (String key : metas.keySet()) {
            rmList.add(rmff.createMetaAuto(key, metas.get(key)));
        }
        Map<String, String> parentMetas = sample.getParentMetaData();
        for (String key : parentMetas.keySet()) {
            rmList.add(rmff.createMetaAuto(key, parentMetas.get(key)));
        }
        return rmList;
    }

    // other

    /**
     * returns the ending of the filename (and converts it toLower is toLower = true)
     *
     * @return
     */
    public static String getExtension(String filename, boolean toLower) {
        String ext = null;
        String s = filename;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1);
        }
        if (ext != null && toLower) ext = ext.toLowerCase();
        return ext;
    }

    /**
     * returns the lower case ending of the filename
     *
     * @return
     */
    public static String getExtension(String filename) {
        return getExtension(filename, true);
    }


    /**
     * returns the lowercase ending of the filename
     *
     * @param f
     * @return
     */
    public static String getExtension(File f) {
        return getExtension(f.getName());
    }


    /**
     * Formats fileSize (in byte) in B/KB/MB/GB
     *
     * @param fileSize
     * @return
     */
    public static String formatFileSize(long fileSize) {
        String unit = "B";
        double size = 0;

        if (fileSize < 1024) {
            unit = "B";
            size = (double) fileSize;
        } else if (fileSize < (1024 * 1024L)) {
            unit = "KB";
            size = (double) fileSize / (1024L);
        } else if (fileSize < (1024 * 1024 * 1024L)) {
            unit = "MB";
            size = (double) fileSize / (1024 * 1024L);
        } else {
            unit = "GB";
            size = (double) fileSize / (1024 * 1024 * 1024L);
        }

        return String.format("%1$.2f", size) + " " + unit;
    }

    /**
     * formats time in seconds.
     *
     * @param s
     * @return
     */
    public static String formatTime(double s) {
        String d = "";
        if (s > 3600 * 24) {
            d = Integer.toString((int) (s / (3600 * 24))) + "d +";
            s -= 3600 * 24;
        }
        return d + dateFormat.get().format(new Date((long) s * 1000L - 3600000));
    }

    /**
     * formats a millisec time
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms) {
        double s = ms / 1000d;
        return formatTime(s);
    }

    public static void centerComponent(Component comp) {
        Toolkit toolkit = comp.getToolkit();
        Dimension size = toolkit.getScreenSize();
        comp.setLocation((size.width - comp.getWidth()) / 2, (size.height - comp.getHeight()) / 2);
    }


    /**
     * checks if a file ends with tiff or tif
     *
     * @param filename
     * @return
     */
    public static boolean isTiffFileStrict(String filename) {
        String ext = getExtension(filename);
        if (ext != null) {
            return ext.equals("tiff") || ext.equals("tif");
        }
        return false;
    }

    /**
     * checks if a file ends with tiff, tif, svs, ndpi
     *
     * @param filename
     * @return
     */
    public static boolean isTiffFile(String filename) {
        String ext = getExtension(filename);
        if (ext != null) {
            return ext.equals("tiff") || ext.equals("tif") || ext.equals("svs") || ext.equals("ndpi");
        }
        return false;
    }

    public static boolean isTiffFile(File f) {
        if (f == null) return false;
        return isTiffFile(f.getName());
    }

    public static boolean isNDPIFile(File f) {
        if (f == null) return false;
        return isNDPIFile(f.getName());
    }

    public static boolean isNDPIFile(String filename) {
        if (filename == null) return false;
        return filename.toLowerCase().endsWith(".ndpi");
    }

    public static boolean isSVSFile(String filename) {
        if (filename == null) return false;
        return filename.toLowerCase().endsWith(".svs");
    }

    public static boolean isTifFile(String filename) {
        if (filename == null) return false;
        return filename.toLowerCase().endsWith(".tif") || filename.toLowerCase().endsWith(".tiff");
    }

    public static boolean isZVIFile(String filename) {
        if (filename == null) return false;
        return filename.toLowerCase().endsWith(".zvi");
    }

    public static boolean isLIFFile(String filename) {
        if (filename == null) return false;
        return filename.toLowerCase().endsWith(".lif");
    }

    public static boolean isZVIFile(File f) {
        if (f == null) return false;
        return isZVIFile(f.getName());
    }

    public static boolean isLIFFile(File f) {
        if (f == null) return false;
        return isLIFFile(f.getName());
    }


    public static boolean isImageFile(String filename) {
        String extension = getExtension(filename);
        if (extension != null) {
            return extension.equals("tiff") ||
                    extension.equals("tif") ||
                    extension.equals("jpeg") ||
                    extension.equals("jpg") ||
                    extension.equals("jp2") ||
                    extension.equals("j2k") ||
                    extension.equals("gif") ||
                    extension.equals("png") ||
                    extension.equals("bmp") ||
                    extension.equals("svs") ||
                    extension.equals("zvi") ||
                    extension.equals("lif") ||
                    extension.equals("ndpi") ||
                    extension.equals("ndpis") ||
                    extension.equals("dcm") ||
                    extension.equals("pcx") ||
                    extension.equals("psd");
        }

        return false;
    }

    public static boolean isStandardImageFile(String filename) {
        String extension = getExtension(filename);
        if (extension != null) {
            return extension.equals("jpeg") ||
                    extension.equals("jpg") ||
                    extension.equals("gif") ||
                    extension.equals("png") ||
                    extension.equals("bmp");
        }

        return false;
    }

    public static boolean isImageFile(File f) {
        if (f == null) return false;
        return isImageFile(f.getName());
    }

    public static HashMap<String, RawMeta> getHashFromMetaList(List<RawMeta> rmList) {
        if (rmList == null) return null;
        HashMap<String, RawMeta> rmHash = new HashMap<String, RawMeta>(rmList.size());
        for (RawMeta rm : rmList) {
            if (rm.getValue() == null) continue;
            rmHash.put(rm.getName(), rm);
        }
        return rmHash;
    }


    public static String getContentStr(URL url) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine + "\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    public static void getContentToOutputstream(URL url, OutputStream out) {
        byte[] bytes = new byte[1024];
        InputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            int bytesRead = 0;
            while ((bytesRead = is.read(bytes, 0, bytes.length)) > 0) {
                out.write(bytes, 0, bytesRead);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Returns the content of the url or of the redirected page (one-time redirection only)
     *
     * @param url
     * @return
     */
    public String getRedirectedContentStr(URL url) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String newUrl = conn.getHeaderField("Location");   // the random page will redirect us...
            conn.disconnect();
            URL url2 = newUrl != null ? new URL(newUrl) : url;

            in = new BufferedReader(
                    new InputStreamReader(url2.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine + "\n");
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }


    public static String packStringList(List<String> strList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strList.size(); i++) {
            sb.append(strList.get(i));
            if (i < strList.size() - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static List<String> unpackStringList(String string) {
        List<String> list = new ArrayList<String>();
        String[] split = string.split(";");
        if (split != null && split.length > 0 && !split[0].isEmpty()) {
            for (String s : split) {
                list.add(s);
            }
        }
        return list;
    }


}
