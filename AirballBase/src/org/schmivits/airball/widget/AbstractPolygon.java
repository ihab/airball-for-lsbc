package org.schmivits.airball.widget;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

public abstract class AbstractPolygon extends Widget {

    protected final Color mColor;
    private final float[][] mPoints;
    protected Shape mShape;

    public AbstractPolygon(float[][] points, Color color) {
        mColor = color;
        mPoints = points;
        makePath();
    }

    @Override
    public void onResize() {
        makePath();
    }

    private void makePath() {
        GeneralPath p = new GeneralPath();
        p.moveTo(mPoints[0][0] * getWidth(), mPoints[0][1] * getHeight());
        for (int i = 1; i < mPoints.length; i++) {
            p.lineTo(mPoints[i][0] * getWidth(), mPoints[i][1] * getHeight());
        }
        p.lineTo(mPoints[0][0] * getWidth(), mPoints[0][1] * getHeight());
        mShape = p;
    }
}
