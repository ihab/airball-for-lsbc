package org.schmivits.airball.display.altitude;

import org.schmivits.airball.display.DisplayConfiguration;
import org.schmivits.airball.widget.Widget;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class AltitudeTapeCore extends Widget {

    public interface Model {
        float getAltitude();
    }

    private final DisplayConfiguration mConfig;
    private final Model mModel;

    private final float mTapePixelsPerFoot;
    private final float mTextThousandsSize;
    private final float mTextHundredsSize;
    private final float mTickMarkTwentiesLength;
    private final float mTickMarkHundredsLength;
    private final float mTickMarkThousandsLength;
    private final float mThousandsEmphasisLineDistanceFromText;
    private final float mThousandsEmphasisLineDistanceFromLeft;
    private final float mTextThousandsRightBoundary;
    private final float mTextHundredsLeftBoundary;
    private final Font mTextThousandsFont;
    private final Font mTextHundredsFont;

    public AltitudeTapeCore(
            Model model,
            DisplayConfiguration config,
            float x, float y, float w, float h) {
        super(x, y, w, h);
        mConfig = config;
        mModel = model;

        mTapePixelsPerFoot = (float) Math.floor(w / 100);

        mTextThousandsSize = (float) Math.floor(w / 3.25);
        mTextHundredsSize = (float) Math.floor(w / 4.5);

        mTextThousandsFont = config.mFont.deriveFont(mTextThousandsSize);
        mTextHundredsFont = config.mFont.deriveFont(mTextHundredsSize);

        mTickMarkTwentiesLength = (float) Math.floor(w / 10);
        mTickMarkHundredsLength = (float) Math.floor(mTickMarkTwentiesLength * 1.5);
        mTickMarkThousandsLength = (float) Math.floor(mTickMarkTwentiesLength * 2);

        mThousandsEmphasisLineDistanceFromText = (float) Math.floor(mTextThousandsSize / 10);
        mThousandsEmphasisLineDistanceFromLeft = (float) Math.floor(mTickMarkTwentiesLength * 2.5);

        mTextThousandsRightBoundary = (float) Math.floor(
                mThousandsEmphasisLineDistanceFromLeft + mTextThousandsSize * 1.125);
        mTextHundredsLeftBoundary = mTextThousandsRightBoundary + (float) Math.floor(mTextThousandsSize / 10);
    }

    float altitudeToCanvasPosition(float alt) {
        return (getHeight() / 2f) - (alt - mModel.getAltitude()) * mTapePixelsPerFoot;
    }

    private void drawAltitude(Graphics2D g, int alt20) {
        if (alt20 < 0) {
            return;
        }

        g.setColor(mConfig.mLineColor);

        float altitude = alt20 * 20f;
        float y = altitudeToCanvasPosition(altitude);
        boolean isHundreds = ((altitude % 100f) == 0);
        boolean isThousands = ((altitude % 1000f) == 0);

        if (isThousands || isHundreds) {

            int thousands = (int) (altitude / 1000);
            int hundreds = (int) Math.floor((altitude - (thousands * 1000)) / 100) * 100;

            String thousandsString = thousands == 0 ? "" : "" + thousands;
            String hundredsString = hundreds == 0 ? "000" : "" + hundreds;

            float thousandsWidth = g.getFontMetrics().stringWidth(thousandsString);

            g.setFont(mTextThousandsFont);
            g.drawString(
                    thousandsString,
                    getWidth() - mTextThousandsRightBoundary - thousandsWidth,
                    y + mTextThousandsSize * mConfig.mTextBaselineToCenterFactor);

            g.setFont(mTextHundredsFont);
            g.drawString(
                    hundredsString,
                    mTextHundredsLeftBoundary,
                    y + mTextHundredsSize * mConfig.mTextBaselineToCenterFactor);

            if (isThousands) {
                g.fill(new Rectangle2D.Float(
                        0,
                        y - mConfig.mVeryThickLineThickness / 2,
                        mTickMarkThousandsLength,
                        mConfig.mVeryThickLineThickness));
                g.fill(new Rectangle2D.Float(
                        mThousandsEmphasisLineDistanceFromLeft,
                        y - mTextThousandsSize / 2 - mThousandsEmphasisLineDistanceFromText - mConfig.mThinLineThickness,
                        getWidth() - mThousandsEmphasisLineDistanceFromLeft,
                        mConfig.mThinLineThickness));
                g.fill(new Rectangle2D.Float(
                        mThousandsEmphasisLineDistanceFromLeft,
                        y + mTextThousandsSize / 2 + mThousandsEmphasisLineDistanceFromText,
                        getWidth() - mThousandsEmphasisLineDistanceFromLeft,
                        mConfig.mThinLineThickness));
            } else if (isHundreds) {
                g.fill(new Rectangle2D.Float(
                        0,
                        y - mConfig.mThickLineThickness / 2,
                        mTickMarkHundredsLength,
                        mConfig.mThickLineThickness));
            }
        } else {
            g.fill(new Rectangle2D.Float(
                    0,
                    y - mConfig.mThinLineThickness / 2,
                    mTickMarkTwentiesLength,
                    mConfig.mThinLineThickness));
        }
    }

    private void drawScaleLine(Graphics2D g) {
        float yMax = Math.min(getHeight(), altitudeToCanvasPosition(0f));
        g.setColor(mConfig.mLineColor);
        g.fill(new Rectangle2D.Float(
                0,
                0,
                mConfig.mThinLineThickness,
                yMax));
    }

    @Override
    protected void drawContents(Graphics2D g) {
        drawScaleLine(g);
        int alt20AtTop = (int) Math.ceil((mModel.getAltitude() + (getHeight() / 2f) * mTapePixelsPerFoot) / 20) + 1;
        int alt20AtBottom = (int) Math.floor((mModel.getAltitude() - (getHeight() / 2f) * mTapePixelsPerFoot) / 20) - 1;
        for (int i = alt20AtBottom; i <= alt20AtTop; i++) {
            drawAltitude(g, i);
        }
    }
}
