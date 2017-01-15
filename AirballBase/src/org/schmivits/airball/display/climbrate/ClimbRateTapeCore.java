package org.schmivits.airball.display.climbrate;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.Text;
import org.schmivits.airball.widget.Widget;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class ClimbRateTapeCore extends Container {

    public interface Model {
        float getClimbRate();
    }

    private final Model mModel;
    private final DisplayConfiguration mConfig;
    private final float mIndicatorThickness;
    private final float mTapePixelsPerFpm;

    public ClimbRateTapeCore(Model model, DisplayConfiguration config,
                             float x, float y, float w, float h) {
        moveTo(x, y);
        sizeTo(w, h);

        mModel = model;
        mConfig = config;
        mIndicatorThickness = (float) Math.floor(w * 0.45);
        mTapePixelsPerFpm = (float) 0.9 * (getHeight() / 2) / 2000;
        addConstantItems();
    }

    private float getYCoordinate(float value) {
        return getHeight() / 2f - value * mTapePixelsPerFpm;
    }

    private void addTickMark(float thickness, float length, float textSize, float value, String text) {
        float y = getYCoordinate(value);
        float mTextLeftBoundaryFromLeft = (float) Math.floor(getWidth() * 0.6);
        Widget tick = new Rectangle(mConfig.mLineColor);
        tick.moveTo(0, y - thickness / 2f);
        tick.sizeTo(length, thickness);
        mChildren.add(tick);
        if (text != null) {
            Widget textWidget = new Text(text, textSize, mConfig.mTextColor, mConfig.mFont.deriveFont(textSize));
            textWidget.moveTo(mTextLeftBoundaryFromLeft, y  - textSize * (1f - mConfig.mTextBaselineToCenterFactor));
            mChildren.add(textWidget);
        }
    }

    private void addConstantItems() {
        float mTickMarkLength1000 = (float) Math.floor(getWidth() * 0.55);
        float mTickMarkLength500 = (float) Math.floor(getWidth() * 0.45);

        float mTextSize1000 = (float) Math.floor(getWidth() * 0.6);

        Widget line = new Rectangle(mConfig.mLineColor);
        line.moveTo(0f, 0f);
        line.sizeTo(mConfig.mThinLineThickness, getHeight());
        mChildren.add(line);

        for (int i = -2; i <= 2; i++) {
            addTickMark(
                    mConfig.mThickLineThickness,
                    mTickMarkLength1000,
                    mTextSize1000,
                    i * 1000f,
                    "" + Math.abs(i));
        }
        for (int i = -3; i <= 3; i += 2) {
            addTickMark(
                    mConfig.mThinLineThickness,
                    mTickMarkLength500,
                    0f,
                    i * 500f,
                    null);
        }
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mConfig.mClimbRateColor);
        float barHeight = mModel.getClimbRate() * mTapePixelsPerFpm;
        Shape s;
        if (barHeight < 0) {
            g.fill(s = new Rectangle2D.Float(
                    mConfig.mThinLineThickness * 2f,
                    getHeight() / 2f + barHeight,
                    mIndicatorThickness,
                    -barHeight));
        } else {
            g.fill(s = new Rectangle2D.Float(
                    mConfig.mThinLineThickness * 2f,
                    getHeight() / 2f,
                    mIndicatorThickness,
                    barHeight));
        }
        System.out.println("h = " + barHeight + " s = " + s);
        super.drawContents(g);
    }
}
