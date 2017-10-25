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

import com.actelion.research.orbit.beans.RawData;
import com.actelion.research.orbit.utils.RawUtilsCommon;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RawDataMetaPanel extends JPanel implements PropertyChangeListener {

    private static final long serialVersionUID = 1L;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final JLabel labDescription = new JLabel("Description");
    private final JLabel labComment = new JLabel("Comment");
    private final JLabel labKeywords = new JLabel("Keywords");
    private final JLabel labUser = new JLabel("User");
    private final JLabel labLabname = new JLabel("Labname");
    private final JLabel labOrigin = new JLabel("Origin");
    private final JLabel labRefDate = new JLabel("Reference Date");

    private final JTextArea tfDescription = new JTextArea();
    private final JTextArea tfComments = new JTextArea();
    private final JTextArea tfKeywords = new JTextArea();

    public RawDataMetaPanel() {

        tfDescription.setEditable(false);
        tfComments.setEditable(false);
        tfKeywords.setEditable(false);

        tfDescription.setLineWrap(true);
        tfComments.setLineWrap(true);
        tfKeywords.setLineWrap(true);

        tfDescription.setWrapStyleWord(true);
        tfComments.setWrapStyleWord(true);
        tfKeywords.setWrapStyleWord(true);


        // layout
        setLayout(new GridBagLayout());
        add(labDescription, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        add(tfDescription, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(2, 5, 0, 0), 0, 0));

        add(labComment, new GridBagConstraints(1, 0, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        add(tfComments, new GridBagConstraints(1, 1, GridBagConstraints.REMAINDER, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(2, 5, 0, 0), 0, 0));

        add(labKeywords, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        add(tfKeywords, new GridBagConstraints(0, 3, 1, GridBagConstraints.REMAINDER, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(2, 5, 0, 0), 0, 0));

        add(labUser, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
        add(labLabname, new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 40, 0, 0), 0, 0));
        add(labOrigin, new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
        add(labRefDate, new GridBagConstraints(2, 3, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 40, 0, 0), 0, 0));

    }

    public void setRawData(RawData rawData) {
        if (rawData == null) return;
        tfDescription.setText(rawData.getDescription());
        tfComments.setText(rawData.getComment());
        tfKeywords.setText(rawData.getKeywords());
        labUser.setText("User: " + rawData.getUserId());
        labLabname.setText("Labname: " + rawData.getLabname());
        labOrigin.setText("Origin: " + rawData.getOrigin());
        if (rawData.getReferenceDate() != null)
            labRefDate.setText("Reference Date: " + dateFormat.format(rawData.getReferenceDate()));
        repaint();
    }

    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(RawUtilsCommon.RAWDATA_SELECTED)) {
            List<RawData> rdList = (List) evt.getNewValue();
            if (rdList != null && rdList.size() > 0) {
                RawData rawData = rdList.get(0);
                setRawData(rawData);
            }
        }
    }

}
