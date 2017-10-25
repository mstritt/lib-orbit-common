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
import java.awt.event.KeyEvent;


public class IntegerTextField extends JTextField {

    private static final long serialVersionUID = 1L;
    final static String badchars = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";

    public IntegerTextField(int value, int defaultValue) {
        setInputVerifier(new IntInputVerifier(defaultValue));
        setHorizontalAlignment(JTextField.RIGHT);
        setText(value + "");
    }

    public IntegerTextField(int value, int defaultValue, int min, int max) {
        setInputVerifier(new IntInputVerifier(defaultValue, min, max));
        setHorizontalAlignment(JTextField.RIGHT);
        setText(value + "");
    }

    public void processKeyEvent(KeyEvent ev) {

        char c = ev.getKeyChar();

        if ((Character.isLetter(c) && !ev.isAltDown())
                || badchars.indexOf(c) > -1) {
            ev.consume();
            return;
        }
        if (c == '-' && getDocument().getLength() > 0)
            ev.consume();
        else super.processKeyEvent(ev);

    }

    public void setInt(int i) {
        setText(i + "");
    }

    public int getInt() {
        return Integer.parseInt(getText());
    }
}