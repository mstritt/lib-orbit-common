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

package com.actelion.research.orbit.gui;

import com.actelion.research.orbit.utils.Logger;
import com.actelion.research.orbit.utils.RawUtilsCommon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Component to display a searchfield and an AND/OR selector.
 */
public class RdfSearchBox extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(RdfSearchBox.class);
    public static String RDF_SEARCH = "rdfSearch";
    public static String CONCATMODE_AND = "concatmode_and";
    public static String CONCATMODE_OR = "concatmode_or";
    public static String SEARCHMODE_FAST = "SEARCHMODE_FAST";
    public static String SEARCHMODE_EXHAUSTIVE = "SEARCHMODE_EXHAUSTIVE";

    public static String RDF_VIEWMODE = "rdf_viewmode";
    public static String VIEWMODE_LIST = "viewmode_list";
    public static String VIEWMODE_SMALLTHUMB = "viewmode_smallthumb";
    public static String VIEWMODE_BIGTHUMB = "viewmode_bigthumb";
    public static String VIEWFILTER_ADD_FILETYPE = "viewfilter_add_filetype";
    public static String VIEWFILTER_REMOVE_FILETYPE = "viewfilter_remove_filetype";
    private String currentViewMode = VIEWMODE_SMALLTHUMB;

    protected JTextField _searchField = null;
    ;
    private JButton okButton = null;
    private JRadioButton rbAnd = null;
    private JRadioButton rbOr = null;
    private JRadioButton rbFast = null;
    private JRadioButton rbExhaustive = null;

    private JToggleButton btnList = null;
    private JToggleButton btnSmall = null;
    private JToggleButton btnBig = null;

    private final JRadioButton showAllImages = new JRadioButton("all images",true);
    private final JRadioButton showNDPIImages = new JRadioButton("NDPI",false);
    private final JRadioButton showNDPISImages = new JRadioButton("NDPIS",false);


    public RdfSearchBox() {
        this(false, true, true);
    }

    public RdfSearchBox(boolean showViewModeButtons) {
        this(showViewModeButtons, true, true);
    }

    public RdfSearchBox(boolean showViewModeButtons, boolean showConcatMode, boolean showFileFilter) {
        setLayout(new GridBagLayout());

        okButton = new JButton("search");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(RDF_SEARCH, null, getSearchField().getText());
            }
        });

		/*
        rbAnd = new JRadioButton("all words");
		rbAnd.setSelected(true);
		rbAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange(CONCATMODE_AND, null, rbAnd.isSelected());
			}
		});
		rbOr = new JRadioButton("any word");
		rbOr.setSelected(false);
		rbOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firePropertyChange(CONCATMODE_OR, null, rbOr.isSelected());
			}
		});
		*/

        rbFast = new JRadioButton("fast");
        rbFast.setSelected(true);
        rbFast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(SEARCHMODE_FAST, null, rbFast.isSelected());
            }
        });
        rbExhaustive = new JRadioButton("exhaustive");
        rbExhaustive.setSelected(false);
        rbExhaustive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(SEARCHMODE_EXHAUSTIVE, null, rbExhaustive.isSelected());
            }
        });

        // fast/exhaustive search is not added anymore - exhaustive search is too slow

//        JPanel rbPanel = null;
//        if (showConcatMode) {
//            ButtonGroup buttonGroup;
//            buttonGroup = new ButtonGroup();
//            buttonGroup.add(rbFast);
//            buttonGroup.add(rbExhaustive);
//            rbPanel = new JPanel();
//            rbPanel.add(rbFast);
//            rbPanel.add(rbExhaustive);
//        }

        ActionListener imageTypefilter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<String> fileTypes = new ArrayList<>();
                if (showNDPIImages.isSelected()) fileTypes.add(RawUtilsCommon.DATA_TYPE_IMAGE_NDPI);
                else if (showNDPISImages.isSelected()) fileTypes.add(RawUtilsCommon.DATA_TYPE_IMAGE_NDPIS);
                else fileTypes = Arrays.asList(RawUtilsCommon.fileTypesImage);
                firePropertyChange("EXPLICITE_FILEFILTER", null, fileTypes);
            }
        };

        showAllImages.addActionListener(imageTypefilter);
        showNDPIImages.addActionListener(imageTypefilter);
        showNDPISImages.addActionListener(imageTypefilter);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(showAllImages);
        buttonGroup.add(showNDPIImages);
        buttonGroup.add(showNDPISImages);

        JPanel fileFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        fileFilterPanel.add(showAllImages);
        fileFilterPanel.add(showNDPIImages);
        fileFilterPanel.add(showNDPISImages);

        // view mode

        JPanel viewModePanel = null;
        if (showViewModeButtons) viewModePanel = getViewModePanel();
        JPanel filterPanel = getFilterPanel(); // will be null if not overwritten


        GridBagConstraints gbcSearchField = new GridBagConstraints();
        gbcSearchField.weightx = 1.0;
        gbcSearchField.weighty = 0.0;
        gbcSearchField.anchor = GridBagConstraints.NORTHWEST;
        gbcSearchField.fill = GridBagConstraints.HORIZONTAL;
        gbcSearchField.gridx = 0;
        gbcSearchField.gridy = 0;

        GridBagConstraints gbcOkButton = new GridBagConstraints();
        gbcOkButton.insets = new Insets(0, 5, 0, 5);
        gbcOkButton.weightx = 0.0;
        gbcOkButton.weighty = 0.0;
        gbcOkButton.anchor = GridBagConstraints.NORTHEAST;
        gbcOkButton.fill = GridBagConstraints.NONE;
        gbcOkButton.gridx = 1;
        gbcOkButton.gridy = 0;

        GridBagConstraints gbcSearchPane = new GridBagConstraints();
        gbcSearchPane.insets = new Insets(0, 0, 0, 0);
        gbcSearchPane.weightx = 1.0;
        gbcSearchPane.weighty = 0.0;
        gbcSearchPane.gridwidth = GridBagConstraints.REMAINDER;
        gbcSearchPane.anchor = GridBagConstraints.NORTHWEST;
        gbcSearchPane.fill = GridBagConstraints.HORIZONTAL;
        gbcSearchPane.gridx = 0;
        gbcSearchPane.gridy = 0;


        GridBagConstraints gbcViewMode = new GridBagConstraints();
        gbcViewMode.insets = new Insets(0, 0, 0, 0);
        gbcViewMode.weightx = 1.0;
        gbcViewMode.weighty = 0.0;
        gbcViewMode.anchor = GridBagConstraints.WEST;
        gbcViewMode.fill = GridBagConstraints.NONE;
        gbcViewMode.gridx = 0;
        gbcViewMode.gridy = 1;

        GridBagConstraints gbcFilter = new GridBagConstraints();
        gbcFilter.insets = new Insets(0, 0, 0, 0);
        gbcFilter.weightx = 1.0;
        gbcFilter.weighty = 0.0;
        gbcFilter.anchor = GridBagConstraints.CENTER;
        gbcFilter.fill = GridBagConstraints.NONE;
        gbcFilter.gridx = 1;
        gbcFilter.gridy = 1;

        GridBagConstraints gbcAndOrMode = new GridBagConstraints();
        gbcAndOrMode.insets = new Insets(0, 0, 0, 0);
        gbcAndOrMode.weightx = 0.0;
        gbcAndOrMode.weighty = 0.0;
        gbcAndOrMode.anchor = GridBagConstraints.NORTHEAST;
        gbcAndOrMode.fill = GridBagConstraints.NONE;
        gbcAndOrMode.gridx = 2;
        gbcAndOrMode.gridy = 1;


        JPanel sp = new JPanel(new GridBagLayout());
        sp.add(getSearchField(), gbcSearchField);
        sp.add(okButton, gbcOkButton);

        add(sp, gbcSearchPane);
        if (viewModePanel != null) add(viewModePanel, gbcViewMode);
        if (filterPanel != null) add(filterPanel, gbcFilter);
       // if (showConcatMode) add(rbPanel, gbcAndOrMode);
        if (showFileFilter)
            add(fileFilterPanel, gbcAndOrMode);
    }


    protected JPanel getViewModePanel() {

        ImageIcon listIcon = null;
        ImageIcon smallThumbIcon = null;
        ImageIcon bigThumbIcon = null;
        try {
            listIcon = new ImageIcon(this.getClass().getResource("/resource/list.png"));
            smallThumbIcon = new ImageIcon(this.getClass().getResource("/resource/smallthumb.png"));
            bigThumbIcon = new ImageIcon(this.getClass().getResource("/resource/bigthumb.png"));
        } catch (Exception e) {
            logger.error("error reading icons: " + e.getMessage());
        }

        btnList = new JToggleButton(listIcon, false);
        btnSmall = new JToggleButton(smallThumbIcon, true);
        btnBig = new JToggleButton(bigThumbIcon, false);

        btnList.setToolTipText("List display");
        btnList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnList.setSelected(true);
                btnSmall.setSelected(false);
                btnBig.setSelected(false);
                currentViewMode = VIEWMODE_LIST;
                firePropertyChange(RDF_VIEWMODE, null, VIEWMODE_LIST);
            }
        });
        btnSmall.setToolTipText("Thumbnails and details");
        btnSmall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnList.setSelected(false);
                btnSmall.setSelected(true);
                btnBig.setSelected(false);
                currentViewMode = VIEWMODE_SMALLTHUMB;
                firePropertyChange(RDF_VIEWMODE, null, VIEWMODE_SMALLTHUMB);
            }
        });
        btnBig.setToolTipText("Preview images");
        btnBig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnList.setSelected(false);
                btnSmall.setSelected(false);
                btnBig.setSelected(true);
                currentViewMode = VIEWMODE_BIGTHUMB;
                firePropertyChange(RDF_VIEWMODE, null, VIEWMODE_BIGTHUMB);
            }
        });

        JPanel panel = new JPanel(null);
        final int btnHeight = 30;
        final int btnWidth = 30;
        btnList.setBounds(0, 0, btnWidth, btnHeight);
        btnSmall.setBounds(btnWidth - 2, 0, btnWidth, btnHeight);
        btnBig.setBounds(btnWidth * 2 - 4, 0, btnWidth, btnHeight);
        panel.setPreferredSize(new Dimension(btnWidth * 3 - 4, btnHeight));
        panel.setMinimumSize(new Dimension(btnWidth * 3 - 4, btnHeight));
        panel.add(btnList);
        panel.add(btnSmall);
        panel.add(btnBig);
        return panel;
    }

    protected JTextField getSearchField() {
        if (_searchField == null) {
            _searchField = new JTextField();
            _searchField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        firePropertyChange(RDF_SEARCH, null, getSearchField().getText());
                    }
                }
            });
        }
        return _searchField;
    }

    /**
     * Can be overwritten to display file filter (e.g. togglebuttons).
     *
     * @return
     */
    protected JPanel getFilterPanel() {
        return null;
    }

    public JRadioButton getRbAnd() {
        return rbAnd;
    }

    public void setRbAnd(JRadioButton rbAnd) {
        this.rbAnd = rbAnd;
    }

    public JRadioButton getRbOr() {
        return rbOr;
    }

    public void setRbOr(JRadioButton rbOr) {
        this.rbOr = rbOr;
    }

    public String getViewModeList() {
        return currentViewMode;
    }

    public void setViewModeList(String viewModeList) {
        if (viewModeList.equals(VIEWMODE_LIST)) {
            btnList.getActionListeners()[0].actionPerformed(null);
        } else if (viewModeList.equals(VIEWMODE_SMALLTHUMB)) {
            btnSmall.getActionListeners()[0].actionPerformed(null);
        } else if (viewModeList.equals(VIEWMODE_BIGTHUMB)) {
            btnBig.getActionListeners()[0].actionPerformed(null);
        }
    }

    public JButton getOkButton() {
        return okButton;
    }
}
