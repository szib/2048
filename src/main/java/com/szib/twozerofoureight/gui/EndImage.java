/*
 * MIT License
 * 
 * Copyright (c) 2016 Ivan Szebenszki
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.szib.twozerofoureight.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class EndImage {

  public static BufferedImage getImage(String text, int boardDrawableSize) {
    BufferedImage image =
        new BufferedImage(boardDrawableSize, boardDrawableSize, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = image.createGraphics();

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, boardDrawableSize, boardDrawableSize);
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

    g.setColor(Color.WHITE);
    Font font = g.getFont().deriveFont((float) (boardDrawableSize / 10.0));
    Rectangle2D textBounds = font.getStringBounds(text, new FontRenderContext(null, true, true));
    g.setFont(font);
    g.drawString(
        text,
        (boardDrawableSize / 2)
            - ((int) Math.round(textBounds.getWidth()) / 2)
            - (int) Math.round(textBounds.getX()),
        (boardDrawableSize / 2)
            - ((int) Math.round(textBounds.getHeight()) / 2)
            - (int) Math.round(textBounds.getY()));

    g.dispose();

    return image;
  }

  public static BufferedImage getLostImage(int boardDrawableSize) {
    return getImage("Game Over", boardDrawableSize);
  }

  public static BufferedImage getWonImage(int boardDrawableSize) {
    return getImage("You won!", boardDrawableSize);
  }
}
