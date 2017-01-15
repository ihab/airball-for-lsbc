package org.schmivits.airball.widget;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class Widget {

    protected float mX;
    protected float mY;
    protected float mW;
    protected float mH;
    private boolean mVisible = true;
    private boolean mClip = true;
    private boolean mSizing = false;
    private boolean mMoving = false;
    private Rectangle2D.Float mClipRect = new Rectangle2D.Float();

    protected Widget() {
        this(0, 0, 0, 0);
    }

    protected Widget(float x, float y, float w, float h) {
        mX = x;
        mY = y;
        mW = w;
        mH = h;
        mClipRect.setRect(0, 0, mW, mH);
    }

    public void moveTo(float x, float y) {
        mX = x;
        mY = y;
        if (!mMoving) {
            mMoving = true;
            onMove();
            mMoving = false;
        }
    }

    public void sizeTo(float w, float h) {
        mW = w;
        mH = h;
        mClipRect.setRect(0, 0, mW, mH);
        if (!mSizing) {
            mSizing = true;
            onResize();
            mSizing = false;
        }
    }

    public final float getX() {
        return mX;
    }

    public final void setX(float x) {
        moveTo(x, mY);
    }

    public final float getY() {
        return mY;
    }

    public final void setY(float y) {
        moveTo(mX, y);
    }

    public final float getWidth() {
        return mW;
    }

    public final void setWidth(float w) {
        sizeTo(w, mH);
    }

    public final float getHeight() {
        return mH;
    }

    public final void setHeight(float h) {
        sizeTo(mW, h);
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public boolean isClip() {
        return mClip;
    }

    public void setClip(boolean clip) {
        mClip = clip;
    }

    protected void onResize() {
    }

    protected void onMove() {
    }

    public final void draw(Graphics2D g) {
        if (!mVisible) {
            return;
        }
        java.awt.Rectangle oldClip = g.getClipBounds();
        g.translate(mX, mY);
        if (mClip) {
            g.clip(mClipRect);
        }
        drawContents(g);
        g.translate(-mX, -mY);
        g.setClip(oldClip);
    }

    protected abstract void drawContents(Graphics2D g);
}
