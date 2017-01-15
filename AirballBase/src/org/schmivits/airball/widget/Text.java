package org.schmivits.airball.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Widget {

    private final String mText;
    private final Color mColor;
    private final float mSize;
    private final Font mFont;

    public Text(String text, float size, Color color, Font font) {
        mText = text;
        mColor = color;
        mSize = size;
        mFont = font.deriveFont(size);
        setClip(false);
    }

    @Override
    protected void drawContents(Graphics2D g) {
        g.setColor(mColor);
        g.setFont(mFont);
        g.translate(0, mSize);
        g.drawString(mText, 0, 0);
        g.translate(0, -mSize);
    }
}
