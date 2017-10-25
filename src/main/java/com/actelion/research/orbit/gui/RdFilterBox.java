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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Not used anymore in production. Has been used in combination with RawDataTree - which has been replaced by JOrbitTree.
 * <p>
 * Component to display a raw data filter box and an by ELB/ by project selector.
 */
public class RdFilterBox extends JPanel {
    private static final long serialVersionUID = 1L;
    public final static String RD_FILTER = "rdFilter";
    public final static String FILTER_BY_ELB = "filter_by_elb";
    public final static String FILTER_BY_PROJECT = "filter_by_project";
    public final static String GROUP_BY_ELB = "group_by_elb";
    private final JTextField filterField = new JTextField();
    private final JButton okButton = new JButton("filter");
    private final JRadioButton rbELB = new JRadioButton("by ELB");
    private final JRadioButton rbProject = new JRadioButton("by Project");
    private final JCheckBox cbGroupByELB = new JCheckBox("group ELB", true);

    public RdFilterBox() {
        this(true);
    }

    public RdFilterBox(boolean addGroupBy) {
        setLayout(new GridBagLayout());

        filterField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    firePropertyChange(RD_FILTER, null, filterField.getText());
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(RD_FILTER, null, filterField.getText());
            }
        });


        rbELB.setSelected(true);
        rbELB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(FILTER_BY_ELB, null, rbELB.isSelected());
            }
        });

        rbProject.setSelected(false);
        rbProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(FILTER_BY_PROJECT, null, rbProject.isSelected());
            }
        });

        cbGroupByELB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firePropertyChange(GROUP_BY_ELB, null, cbGroupByELB.isSelected());
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbELB);
        buttonGroup.add(rbProject);
        JPanel rbPanel = new JPanel();
        rbPanel.add(rbELB);
        rbPanel.add(rbProject);
        rbPanel.add(cbGroupByELB);

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

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.insets = new Insets(0, 0, 0, 0);
        gbc3.weightx = 0.5;
        gbc3.weighty = 0.0;
        gbc3.anchor = GridBagConstraints.NORTHWEST;
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.gridx = 0;
        gbc3.gridy = 1;


        add(filterField, gbc);
        add(okButton, gbc2);
        if (addGroupBy) {
            add(rbPanel, gbc3);
        }
    }

    public JRadioButton getRbELB() {
        return rbELB;
    }


    public JRadioButton getRbProject() {
        return rbProject;
    }


}
