package org.schmivits.airball.display;

import java.awt.Color;
import java.awt.Font;

public class DisplayConfiguration {

    public final Color mTextColor = Color.WHITE;
    public final Color mLineColor = Color.WHITE;
    public final Color mPointerColor = Color.decode("#ffcc33");
    public final Color mClimbRateColor = Color.MAGENTA;

    public final float mCautionStripeThickness;
    public final float mThinLineThickness;
    public final float mThickLineThickness;
    public final float mVeryThickLineThickness;
    public final float mPointerShapeSize;
    public final float mPointerToScaleOffset;


    public final Font mFont = Font.decode(Font.MONOSPACED);

    public final float mTextBaselineToCenterFactor = 0.375f;

    public DisplayConfiguration(float w, float h) {
        mCautionStripeThickness = (float) Math.floor(w / 100);
        mThinLineThickness = (float) Math.floor(w / 300);
        mThickLineThickness = (float) Math.floor(2f * mThinLineThickness);
        mVeryThickLineThickness = (float) Math.floor(2.5f * mThinLineThickness);
        mPointerShapeSize = (float) Math.floor(w / 30);
        mPointerToScaleOffset = (float) Math.floor(mPointerShapeSize / 3);
    }
}
