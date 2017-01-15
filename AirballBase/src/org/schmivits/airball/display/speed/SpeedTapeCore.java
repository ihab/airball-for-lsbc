package org.schmivits.airball.display.speed;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class SpeedTapeCore extends Widget {

    public interface Model {
        float getSpeed();

        float getVs0();  // Bottom of white arc

        float getVfe();  // Top of white arc

        float getVs1();  // Bottom of green arc

        float getVno();  // Top of green / bottom of yellow arc

        float getVne();  // Top of yellow / bottom of red arc
    }

    // The below constants, while they may seem silly ;), are useful for
    // tweaking exact RGB values if necessary
    private static final Color WHITE_ARC_COLOR = Color.WHITE;
    private static final Color GREEN_ARC_COLOR = Color.GREEN;
    private static final Color YELLOW_ARC_COLOR = Color.YELLOW;
    private static final Color RED_ARC_COLOR = Color.RED;

    private final DisplayConfiguration mConfig;
    private final Model mModel;

    private final float mVArcRightBoundaryFromRight;
    private final float mTapePixelsPerKnot;
    private final float mTickMarkFivesLength;
    private final float mTickMarkTensLength;
    private final float mTextSize;
    private final float mTextRightBoundaryFromRight;
    private final float mVArcThickness;

    public SpeedTapeCore(Model model, DisplayConfiguration config, float x, float y, float w, float h) {
        super(x, y, w, h);

        mConfig = config;
        mModel = model;

        mVArcThickness = (float) Math.floor(w / 12);
        mVArcRightBoundaryFromRight = 2f * mConfig.mThinLineThickness;
        mTapePixelsPerKnot = (float) Math.floor(w / 10);
        mTickMarkFivesLength = (float) Math.floor(3 * mVArcThickness);
        mTickMarkTensLength = (float) Math.floor(3.25 * mVArcThickness);
        mTextSize = (float) Math.floor(w / 3.5);
        mTextRightBoundaryFromRight = (float) Math.floor(4 * mVArcThickness);
    }

    float speedToCanvasPosition(float speed) {
        return getHeight() / 2 + (speed - mModel.getSpeed()) * mTapePixelsPerKnot;
    }

    private void drawSpeed(Graphics2D g, int speed5) {
        if (speed5 < 0) {
            return;
        }

        int speed = speed5 * 5;
        float y = speedToCanvasPosition(speed);
        boolean isTens = ((speed % 10f) == 0);

        if (isTens) {
            g.setColor(mConfig.mLineColor);
            g.fill(new Rectangle2D.Float(
                    getWidth() - mTickMarkTensLength,
                    y - mConfig.mThickLineThickness / 2,
                    mTickMarkTensLength,
                    mConfig.mThickLineThickness));
            g.setColor(mConfig.mTextColor);
            g.setFont(mConfig.mFont.deriveFont(mTextSize));
            String s = "" + speed;
            float stringWidth = g.getFontMetrics().stringWidth(s);
            g.drawString(
                    s,
                    getWidth() - mTextRightBoundaryFromRight - stringWidth,
                    y + mTextSize * mConfig.mTextBaselineToCenterFactor);
        } else {
            g.setColor(mConfig.mLineColor);
            g.fill(new Rectangle2D.Float(
                    getWidth() - mTickMarkFivesLength,
                    y - mConfig.mThinLineThickness / 2,
                    mTickMarkFivesLength,
                    mConfig.mThinLineThickness));
        }
    }

    private void drawScaleLine(Graphics2D g) {
        float yMin = Math.max(0, speedToCanvasPosition(0f));
        g.setColor(mConfig.mLineColor);
        g.fill(new Rectangle2D.Float(
                getWidth() - mConfig.mThinLineThickness,
                yMin - mConfig.mThinLineThickness / 2,
                mConfig.mThinLineThickness,
                getHeight() - (yMin - mConfig.mThinLineThickness / 2)));
    }

    private void drawVArcs(Graphics2D g) {
        g.setColor(WHITE_ARC_COLOR);
        g.fill(new Rectangle2D.Float(
                getWidth() - mVArcRightBoundaryFromRight - 2 * mVArcThickness,
                speedToCanvasPosition(mModel.getVs0()),
                mVArcThickness,
                speedToCanvasPosition(mModel.getVfe()) - speedToCanvasPosition(mModel.getVs0())));
        g.setColor(GREEN_ARC_COLOR);
        g.fill(new Rectangle2D.Float(
                getWidth() - mVArcRightBoundaryFromRight - mVArcThickness,
                speedToCanvasPosition(mModel.getVs1()),
                mVArcThickness,
                speedToCanvasPosition(mModel.getVno()) - speedToCanvasPosition(mModel.getVs1())));
        g.setColor(YELLOW_ARC_COLOR);
        g.fill(new Rectangle2D.Float(
                getWidth() - mVArcRightBoundaryFromRight - mVArcThickness,
                speedToCanvasPosition(mModel.getVno()),
                mVArcThickness,
                speedToCanvasPosition(mModel.getVne()) - speedToCanvasPosition(mModel.getVno())));
        g.setColor(RED_ARC_COLOR);
        g.fill(new Rectangle2D.Float(
                getWidth() - mVArcRightBoundaryFromRight - mVArcThickness,
                speedToCanvasPosition(mModel.getVne()),
                mVArcThickness,
                getHeight() - speedToCanvasPosition(mModel.getVne())));
    }

    @Override
    protected void drawContents(Graphics2D g) {
        drawScaleLine(g);
        drawVArcs(g);
        int speed5AtTop = (int)
                Math.floor((mModel.getSpeed() - getHeight() / 2 / mTapePixelsPerKnot) / 5)
                - 1;
        int speed5AtBottom = (int)
                Math.ceil((mModel.getSpeed() + getHeight() / 2 / mTapePixelsPerKnot) / 5)
                + 1;
        for (int i = speed5AtTop; i <= speed5AtBottom; i++) {
            drawSpeed(g, i);
        }
    }
}
