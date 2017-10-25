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
 * Checks if a JTextField.getText is in integer. If not, it sets it to the default value.
 */
public class IntInputVerifier extends InputVerifier {

    private int defaultValue = 0;
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public IntInputVerifier(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public IntInputVerifier(int defaultValue, int min, int max) {
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean verify(JComponent input) {
        if (!(input instanceof JTextField)) throw new IllegalArgumentException("input has to be a JTextField.");
        try {
            int val = Integer.parseInt(((JTextField) input).getText());
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
            reset(input, defaultValue);
            return false;
        }
    }

    private void reset(final JComponent input, int val) {
        ((JTextField) input).setText(Integer.toString(val));
        input.repaint();
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }


}
