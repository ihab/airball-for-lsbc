package org.schmivits.airball.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class StrokedPolygon extends AbstractPolygon {

    private Stroke mStroke;

    public StrokedPolygon(float[][] points, Color color, float strokeWidth) {
        super(points, color);
        setClip(false);
        mStroke = new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.setStroke(mStroke);
        g.draw(mShape);
    }
}





