/*
 * The contents of this file are subject to the Sapient Public License
 * Version 1.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://carbon.sf.net/License.html.
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * The Original Code is The Carbon Component Framework.
 *
 * The Initial Developer of the Original Code is Sapient Corporation
 *
 * Copyright (C) 2003 Sapient Corporation. All Rights Reserved.
 */
package com.ambroz.formula.gui.swing.components;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 * This is the template for Classes.
 *
 *
 * @since carbon 1.0
 * @author Greg Hinkle, January 2002
 * @version $Revision: 1.4 $($Author: dvoet $ / $Date: 2003/05/05 21:21:27 $)
 * @copyright 2002 Sapient
 */
public class VerticalLabelUI extends BasicLabelUI {

    static {
        labelUI = new VerticalLabelUI(false);
    }

    protected boolean clockwise;

    public VerticalLabelUI(boolean clockwise) {
        super();
        this.clockwise = clockwise;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension dim = super.getPreferredSize(c);
        return new Dimension(dim.height, dim.width);
    }

    private static final Rectangle PAINT_ICON_R = new Rectangle();
    private static final Rectangle PAINT_TEXT_R = new Rectangle();
    private static final Rectangle PAINT_VIEW_R = new Rectangle();
    private static Insets paintViewInsets = new Insets(0, 0, 0, 0);

    @Override
    public void paint(Graphics g, JComponent c) {

        JLabel label = (JLabel) c;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        paintViewInsets = c.getInsets(paintViewInsets);

        PAINT_VIEW_R.x = paintViewInsets.left;
        PAINT_VIEW_R.y = paintViewInsets.top;

        // Use inverted height & width
        PAINT_VIEW_R.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
        PAINT_VIEW_R.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

        PAINT_ICON_R.x = PAINT_ICON_R.y = PAINT_ICON_R.width = PAINT_ICON_R.height = 0;
        PAINT_TEXT_R.x = PAINT_TEXT_R.y = PAINT_TEXT_R.width = PAINT_TEXT_R.height = 0;

        String clippedText = layoutCL(label, fm, text, icon, PAINT_VIEW_R, PAINT_ICON_R, PAINT_TEXT_R);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform tr = g2.getTransform();
        if (clockwise) {
            g2.rotate(Math.PI / 2);
            g2.translate(0, -c.getWidth());
        } else {
            g2.rotate(-Math.PI / 2);
            g2.translate(-c.getHeight(), 0);
        }

        if (icon != null) {
            icon.paintIcon(c, g, PAINT_ICON_R.x, PAINT_ICON_R.y);
        }

        if (text != null) {
            int textX = PAINT_TEXT_R.x;
            int textY = PAINT_TEXT_R.y + fm.getAscent();

            if (label.isEnabled()) {
                paintEnabledText(label, g, clippedText, textX, textY);
            } else {
                paintDisabledText(label, g, clippedText, textX, textY);
            }
        }

        g2.setTransform(tr);
    }
}
