package org.schmivits.airball.widget;

import java.awt.Color;
import java.awt.Graphics2D;

public class FilledPolygon extends AbstractPolygon {

    public FilledPolygon(float[][] points, Color color) {
        super(points, color);
        setClip(true);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.fill(mShape);
    }
}
