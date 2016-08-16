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
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public abstract class AbstractOrbitTree extends JTree implements TreeWillExpandListener, IOrbitTree, IRawTree {
    public AbstractOrbitTree() {
    }

    public AbstractOrbitTree(TreeModel newModel) {
        super(newModel);
    }

    public AbstractOrbitTree(TreeNode root) {
        super(root);
    }

    public AbstractOrbitTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public AbstractOrbitTree(Hashtable<?, ?> value) {
        super(value);
    }

    public AbstractOrbitTree(Object[] value) {
        super(value);
    }

    public AbstractOrbitTree(Vector<?> value) {
        super(value);
    }

    public List<String> getExpandedState() {
        return new ArrayList<>();
    }

    ;

    // can return null
    public JComponent createTreeOptionPane() {
        return null;
    }

}
