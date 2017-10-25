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

/**
 * Checks if a JTextField.getText is in double. If not, it sets it to the default value.
 */
public class DoubleInputVerifier extends InputVerifier {

    private double defaultValue = 0.0d;
    private double min = Double.MIN_VALUE;
    private double max = Double.MAX_VALUE;

    public DoubleInputVerifier(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public DoubleInputVerifier(double defaultValue, double min, double max) {
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean verify(JComponent input) {
        if (!(input instanceof JTextField)) throw new IllegalArgumentException("input has to be a JTextField.");
        try {
            double val = Double.parseDouble(((JTextField) input).getText().replaceAll(",", "."));
            if (val > max) {
                reset(input, max);
                return false;
            }
            if (val < min) {
                reset(input, min);
                return false;
            }
            return true;
        } catch (Exception e) {
            ((JTextField) input).setText(Double.toString(defaultValue));
            input.repaint();
            return false;
        }
    }


    private void reset(final JComponent input, double val) {
        ((JTextField) input).setText(Double.toString(val));
        input.repaint();
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }


}
