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

package com.actelion.research.orbit.browser;

import com.actelion.research.orbit.beans.RawDataFile;
import com.actelion.research.orbit.utils.IRdfToInputStream;
import com.actelion.research.orbit.utils.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

public class DownloadDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(DownloadDialog.class);
    protected static final int BUFFER_SIZE = 1024 * 64;
    final protected JLabel label = new JLabel();
    final protected JProgressBar progressBar = new JProgressBar();
    final protected JButton cancelBtn = new JButton("Cancel");

    protected List<RawDataFile> rdfList = null;
    protected String dir = "";
    protected boolean cancel = false;
    protected int numFiles = 1;
    protected IRdfToInputStream rdfToInputStream;

    public DownloadDialog(String title, List<RawDataFile> rdfList, String dir, IRdfToInputStream rdfToInputStream) {

        this.rdfList = rdfList;
        this.dir = dir;
        this.rdfToInputStream = rdfToInputStream;

        setModal(false);
        setAlwaysOnTop(true);
        setTitle(title);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);


        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancel = true;
                setVisible(false);
            }
        });
        JPanel btnPanel = new JPanel();
        btnPanel.add(cancelBtn);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                cancel = true;
                setVisible(false);
            }
        });

        //layout
        setLayout(new GridBagLayout());
        add(label, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        add(progressBar, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, 1, 1, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
        add(btnPanel, new GridBagConstraints(0, 2, GridBagConstraints.REMAINDER, 1, 1, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        setSize(new Dimension(400, 150));

    }


    public void downloadRDFs() {
        setModal(true);
        SwingWorker<Void, Void> downloadWorker = new DownloadWorker(rdfList, dir);
        SwingUtilities.invokeLater(downloadWorker);
        //downloadWorker.execute();
    }


    protected class DownloadWorker extends SwingWorker<Void, Void> {
        List<RawDataFile> rdfList = null;
        String dir = "";

        public DownloadWorker(List<RawDataFile> rdfList, String dir) {
            this.rdfList = rdfList;
            this.dir = dir;
        }

        @Override
        protected Void doInBackground() throws Exception {
            if (rdfList == null || rdfList.size() == 0 || dir.equals("")) return null;
            BufferedOutputStream os = null;
            BufferedInputStream is = null;
            byte[] buf = new byte[BUFFER_SIZE];
            try {
                numFiles = rdfList.size();
                int numDone = 0;
                for (RawDataFile rdf : rdfList) {
                    os = new BufferedOutputStream(new FileOutputStream(dir + rdf.getFileName()));
                    is = new BufferedInputStream(rdfToInputStream.getInputStream(rdf));
                    int bytesRead = 0;
                    while ((bytesRead = is.read(buf)) > 0) {
                        if (cancel) {
                            try {
                                os.flush();
                                os.close();
                                is.close();
                                buf = null;
                            } catch (Exception e) {
                            }
                            return null;
                        }
                        os.write(buf, 0, bytesRead);
                    }
                    os.flush();
                    os.close();
                    is.close();

                    numDone++;
                    //firePropertyChange("numDone", null, new Integer(numDone));
                    final int val = (int) ((numDone / (double) numFiles) * 100d);
                    progressBar.setValue(val);
                    progressBar.setString(val + "%");
                    label.setText("Download: " + numDone + " / " + numFiles);
                    repaint();

                }
                cancelBtn.setText("Close");

            } catch (Exception e) {
                logger.error("download error: " + e.getMessage());
            } finally {
                try {
                    buf = null;
                    os.close();
                    is.close();
                } catch (Exception e) {
                }
            }
            return null;
        }

    }

}
