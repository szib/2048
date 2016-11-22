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
package com.szib.twozerofoureight.tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import com.szib.twozerofoureight.Coords;
import com.szib.twozerofoureight.Direction;

public class Tile implements ITile {

  protected int number;
  protected Coords coords;
  protected boolean joinedInThisRound;

  private TileDrawingStrategy drawingStrategy;

  public Tile(Coords coords, TileDrawingStrategy drawingStrategy) {
    this.setNumber(Math.random() < 0.9 ? 2 : 4);
    this.setCoords(coords);
    this.setJoinedInThisRound(false);
    this.drawingStrategy = drawingStrategy;
  }

  public boolean canJoin(List<ITile> neighbourList) {
    for (ITile neighbour : neighbourList) {
      if (canJoinTo(neighbour)) {
        return true;
      }
    }
    return false;
  }

  public boolean canJoin(List<ITile> neighbourList, Direction direction) {
    Coords neighboursCoords = Coords.getCoords(this.getCoords(), direction);
    for (ITile neighbour : neighbourList) {
      if (neighbour.getCoords().equals(neighboursCoords)) {
        if (canJoinTo(neighbour)) {
          return true;
        } else {
          return false;
        }
      }
    }
    return true;
  }

  public boolean canJoinTo(ITile tile) {
    return !this.isJoinedInThisRound()
        & !tile.isJoinedInThisRound()
        & this.getNumber() == tile.getNumber();
  }

  public void doubleUp() {
    this.number *= 2;
    setJoinedInThisRound(true);
  }

  public Coords getCoords() {
    return coords;
  }

  public int getNumber() {
    return number;
  }

  public boolean hasTheSameCoords(ITile tile) {
    return this.getCoords().equals(tile.getCoords());
  }

  public boolean isInColoumn(int coloumn) {
    return this.coords.getX() == coloumn;
  }

  public boolean isInRow(int row) {
    return this.coords.getY() == row;
  }

  public boolean isJoinedInThisRound() {
    return joinedInThisRound;
  }

  public void setCoords(Coords coords) {
    this.coords = coords;
  }

  public void setJoinedInThisRound(boolean joined) {
    this.joinedInThisRound = joined;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "[" + number + "] ";
  }

  public Color getBackgroundColor() {
    switch (getNumber()) {
      case 2:
        return new Color(0xEE, 0xE4, 0xDA);
      case 4:
        return new Color(0xED, 0xE8, 0xC8);
      case 8:
        return new Color(0xF2, 0xB1, 0x79);
      case 16:
        return new Color(0xF5, 0x95, 0x63);
      case 32:
        return new Color(0xF6, 0x7C, 0x5F);
      case 64:
        return new Color(0xF6, 0x5E, 0x3B);
      case 128:
        return new Color(0xED, 0xCF, 0x72);
      case 256:
        return new Color(0xED, 0xCC, 0x61);
      case 512:
        return new Color(0xED, 0xC8, 0x50);
      case 1024:
        return new Color(0xED, 0xC5, 0x3F);
      case 2048:
        return new Color(0xED, 0xC2, 0x2E);

      default:
        return Color.BLACK;
    }
  }

  public Color getFontColor() {
    if (getNumber() > 4) {
      return new Color(0xED, 0xE8, 0xC8);
    } else {
      return new Color(0x77, 0x6E, 0x65);
    }
  }

  public void draw(Graphics graphics, int drawableSize, int tileSize, int gap) {
    BufferedImage image =
        getImage(
            graphics.getFont(), new FontRenderContext(null, true, true), tileSize, getNumber());
    graphics.drawImage(
        image,
        getCoords().getX() * (tileSize + gap) + gap,
        getCoords().getY() * (tileSize + gap) + gap,
        null);
  }

  BufferedImage getImage(
      Font font, FontRenderContext fontRenderContext, int drawableSize, int number) {
    float fontSize = drawingStrategy.getFontSize(drawableSize, number);

    Font scaledFont = font.deriveFont(fontSize);
    Rectangle2D rectangle =
        scaledFont.getStringBounds(drawingStrategy.getStringToDraw(number), fontRenderContext);
    BufferedImage image = new BufferedImage(drawableSize, drawableSize, BufferedImage.TYPE_INT_RGB);

    Graphics graphics = image.getGraphics();
    graphics.setColor(getBackgroundColor());
    int frameSize = 0;
    graphics.fillRect(
        frameSize, frameSize, image.getWidth() - 2 * frameSize, image.getHeight() - 2 * frameSize);

    int coordX =
        (drawableSize / 2)
            - ((int) Math.round(rectangle.getWidth()) / 2)
            - (int) Math.round(rectangle.getX());
    int coordY =
        (drawableSize / 2)
            - ((int) Math.round(rectangle.getHeight()) / 2)
            - (int) Math.round(rectangle.getY());

    graphics.setFont(scaledFont);
    graphics.setColor(getFontColor());
    graphics.drawString(drawingStrategy.getStringToDraw(number), coordX, coordY);
    graphics.dispose();
    return image;
  }

  public void setDrawingStrategy(TileDrawingStrategy drawingStrategy) {
    this.drawingStrategy = drawingStrategy;
  }
}
