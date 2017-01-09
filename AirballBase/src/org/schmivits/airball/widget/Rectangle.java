package org.schmivits.airball.widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Widget {

    private final Color mColor;

    public Rectangle(Color color) {
        mColor = color;
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    }
}
