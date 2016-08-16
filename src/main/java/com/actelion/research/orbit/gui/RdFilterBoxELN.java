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

package com.actelion.research.orbit.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Component to display a raw data filter box and an by ELB/ by project selector.
 * <p>
 * 03.10.2011 (Manuel): lazy loading filterField so that the getFilterField method can be overwritten and a JXSearchField can be used.
 * 28.09.2011 (Manuel): my ELN/USER/MYDATA is disabled because this is handled in RawDataTreeTabs now.
 */
public class RdFilterBoxELN extends JPanel {
    private static final long serialVersionUID = 1L;
    public final static String RD_FILTER = "rdFilter";
    public final static String FILTER_BY_ELN = "filter_by_eln";
    public final static String FILTER_BY_YEARUSER = "filter_by_yearuser";
    public final static String FILTER_MY_DATA = "filter_my_data";
    public final static String INVERT_LEAFS = "rd_invert_leaves";
    protected JTextField _filterField = null;
    private final JButton okButton = new JButton("filter");
    private final JRadioButton rbELN = new JRadioButton("by ELN");
    private final JRadioButton rbYEARUSER = new JRadioButton("by Year/User");
    private final JCheckBox cbMyData = new JCheckBox("my Data", false);
    private final JCheckBox cbInvertLeaves = new JCheckBox("sort desc", true);

    public RdFilterBoxELN() {
        setLayout(new GridBagLayout());

        getFilterField().addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    firePropertyChange(RD_FILTER, null, getFilterField().getText());
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(RD_FILTER, null, getFilterField().getText());
            }
        });


        rbELN.setSelected(false);
        rbELN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(FILTER_BY_ELN, null, rbELN.isSelected());
            }
        });

        rbYEARUSER.setSelected(true);
        rbYEARUSER.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(FILTER_BY_YEARUSER, null, rbYEARUSER.isSelected());
            }
        });

        cbMyData.setToolTipText("show only my data");
        cbMyData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(FILTER_MY_DATA, null, cbMyData.isSelected());
            }
        });

        cbInvertLeaves.setToolTipText("sort ELN descending from high to low");
        cbInvertLeaves.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(INVERT_LEAFS, null, cbInvertLeaves.isSelected());
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbYEARUSER);
        buttonGroup.add(rbELN);
        JPanel rbPanel = new JPanel();
        //	rbPanel.add(rbYEARUSER);
        //	rbPanel.add(rbELN);
        //	rbPanel.add(cbMyData);
        rbPanel.add(cbInvertLeaves);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(0, 5, 0, 5);
        gbc2.weightx = 0.0;
        gbc2.weighty = 0.0;
        gbc2.anchor = GridBagConstraints.NORTHWEST;
        gbc2.fill = GridBagConstraints.NONE;
        gbc2.gridx = 1;
        gbc2.gridy = 0;

//		GridBagConstraints gbc3 = new GridBagConstraints();
//		gbc3.insets = new Insets(0, 0, 0, 0);
//		gbc3.weightx = 0.5;
//		gbc3.weighty = 0.0;
//		gbc3.anchor = GridBagConstraints.NORTHWEST;
//		gbc3.fill = GridBagConstraints.HORIZONTAL;
//		gbc3.gridx = 0;
//		gbc3.gridy = 1;

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(0, 0, 0, 0);
        gbc3.weightx = 0.0;
        gbc3.weighty = 0.0;
        gbc3.anchor = GridBagConstraints.NORTHWEST;
        gbc3.fill = GridBagConstraints.NONE;
        gbc3.gridx = 2;
        gbc3.gridy = 0;


        add(getFilterField(), gbc);
        add(okButton, gbc2);
        add(rbPanel, gbc3);

    }


    protected JTextField getFilterField() {
        if (_filterField == null) {
            _filterField = new JTextField();
        }
        return _filterField;
    }

    public JRadioButton getRbELB() {
        return rbELN;
    }


    public JRadioButton getRbProject() {
        return rbYEARUSER;
    }

    /**
     * Returns the first actionListener of the filter button. Normally the action should load the data into
     * the tree and filter it if a filter text is entered into the filter textfield.
     *
     * @return
     */
    public ActionListener getFilterButtonActionListener() {
        return okButton.getActionListeners()[0];
    }

    public boolean isInvertLeaves() {
        return cbInvertLeaves.isSelected();
    }

    public void setInvertLeaves(boolean invertLeaves) {
        cbInvertLeaves.setSelected(invertLeaves);
    }


}
