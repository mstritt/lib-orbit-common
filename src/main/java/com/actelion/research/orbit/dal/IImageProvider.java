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

package com.actelion.research.orbit.dal;

import com.actelion.research.orbit.beans.*;
import com.actelion.research.orbit.gui.AbstractOrbitTree;
import com.actelion.research.orbit.gui.IFileListCellRenderer;
import com.actelion.research.orbit.lims.LIMSBioSample;
import com.actelion.research.orbit.utils.IRdfToInputStream;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface IImageProvider extends Closeable {

    // RawData
    List<RawData> LoadRawDataByBioLabJournal(String elb) throws Exception;

    RawData LoadRawData(int rawDataId) throws Exception;

    boolean UpdateRawData(RawData rd) throws Exception;

    // RawDataFile
    RawDataFile LoadRawDataFile(int rdfId) throws Exception;

    List<RawDataFile> LoadRawDataFiles(int rawDataId) throws Exception;

    List<RawDataFile> LoadRawDataFiles(int rawDataId, int limit) throws Exception;

    List<RawDataFile> LoadRawDataFiles(int rawDataId, List<String> fileTypes, int limit) throws Exception;

    List<RawDataFile> LoadRawDataFilesSearch(String elb, boolean andMode) throws Exception;

    List<RawDataFile> LoadRawDataFilesSearch(String search, boolean andMode, int limit, List<String> fileTypes) throws Exception;

    List<RawDataFile> LoadRawDataFilesByFilenameStart(String search, boolean andMode, int searchLimit, List<String> fileTypes, String orderHint) throws Exception; // orderhint might by sql.. ?

    List<RawDataFile> LoadRawDataFilesSearchFast(String search, int limit, List<String> fileTypes) throws Exception;

    List<RawDataFile> LoadRawDataFilesByPlateName(String plateName, int plateBatch) throws Exception;

    List<RawDataFile> browseImages(Object parent) throws Exception;  // null for user canceled dialog, empty list for selected nothing. For swing parent is of type Component.

    boolean useCustomBrowseImagesDialog(); // then browseImages() must be implemented, otherwise a generic browse dialog will be created automatically

    boolean UpdateRawDataFile(RawDataFile rdf) throws Exception;

    URL getRawDataFileUrl(RawDataFile rdf);

    URL getRawDataFileUrl(RawDataFile rdf, int level);

    URL getRawDataFileThumbnailUrl(RawDataFile rdf);

    BufferedImage getThumbnail(RawDataFile rdf) throws Exception;

    BufferedImage getOverviewImage(RawDataFile rdf) throws Exception;

    BufferedImage getLabelImage(RawDataFile rdf) throws Exception;

    IRdfToInputStream getRdfToInputStream(); // e.g. used in downloader


    // RawMeta
    List<RawMeta> LoadRawMetasByRawDataFile(int rdfId) throws Exception;

    List<RawMeta> LoadRawMetasByRawData(int rdfId) throws Exception;

    List<RawMeta> LoadRawMetasByRawDataFileAndName(int rdfId, String name) throws Exception;

    List<RawMeta> LoadRawMetasByRawDataAndName(int rawDataId, String name) throws Exception;

    int InsertRawMeta(RawMeta rm) throws Exception;

    boolean UpdateRawMeta(RawMeta rm) throws Exception;

    boolean DeleteRawMeta(int rawMetaId) throws Exception;

    // RawAnnotation
    RawAnnotation LoadRawAnnotation(int rawAnnotationId) throws Exception;

    List<RawAnnotation> LoadRawAnnotationsByRawDataFile(int rdfID) throws Exception;

    List<RawAnnotation> LoadRawAnnotationsByRawDataFile(int rdfID, int rawAnnotationType) throws Exception;

    List<RawAnnotation> LoadRawAnnotationsByType(int annotationType) throws Exception;

    int InsertRawAnnotation(RawAnnotation rawAnnotation) throws Exception; // set rawAnnotation.rawDataFileId <0  for non file specific annotations, e.g. models

    boolean UpdateRawAnnotation(RawAnnotation rawAnnotation) throws Exception;

    boolean DeleteRawAnnotation(int rawAnnotationId) throws Exception;

    boolean DeleteRawAnnotationAllWithType(int rdfId, int annotationType) throws Exception;

    String getReplacementMetadata(Object result); // can return null; result will be of type Map<Integer, List<KeyValue<String, T>>> result, where KeyValue is from mapReduceGeneric framework

    HCSMetaData LoadHCSMetaData(int rdfId) throws Exception; // can return null if no HCS meta data is available

    //Image
    IOrbitImage createOrbitImage(RawDataFile rdf, int level) throws Exception;

    // TODO: update filename  (currently via UpdateRawDataFile)

    //Employee
    OrbitUser getOrbitUser(String username);

    // biosample
    LIMSBioSample getLIMSBiosample(RawDataFile rdf) throws Exception;   // to establish an (optional) link to a biosample stored in a LIMS.

    List<LIMSBioSample> LoadByContainerId(String barcode) throws Exception;

    // browser
    void openBrowser(String username, String password); // open an image browser in a modal window, username and password are optional and can be null

    // misc
    AbstractOrbitTree createOrbitTree();

    boolean authenticateUser(String username, String password);

    boolean authenticateUserScaleout(); // scaleout clients (e.g. on a Spark cluster) use this to authenticate

    void setPooledConnectionEnabled(boolean enabled);

    void setDBConnectionName(String name);

    ConcurrentHashMap<String, Object> getHints(); // key-value store for specific hints (can return an empty map, but not null)

    IFileListCellRenderer getFileListCellRenderer();

    void logUsage(final String username, final String method); // for usage statistics

    boolean enforceLoginDialogAtStartup(); // if login is forced or for read-only access a default used is used

    List<String> getAdminUsers(); // can return empty list

    default IImageProvider getLocalImageProvider() { return null; }

    default void setLocalImageProvider(IImageProvider localImageProvider) { };

}
