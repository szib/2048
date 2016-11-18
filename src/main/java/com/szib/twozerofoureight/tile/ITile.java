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
import java.awt.Graphics;
import java.util.List;

import com.szib.twozerofoureight.Coords;
import com.szib.twozerofoureight.Direction;

public interface ITile {

  boolean canJoin(List<ITile> neighbourList);

  boolean canJoin(List<ITile> neighbourList, Direction direction);

  boolean canJoinTo(ITile tile);

  void doubleUp();

  void draw(Graphics graphics, int drawableSize, int tileSize, int gap);

  Coords getCoords();

  int getNumber();

  boolean hasTheSameCoords(ITile tile);

  boolean isInColoumn(int coloumn);

  boolean isInRow(int row);

  boolean isJoinedInThisRound();

  void setCoords(Coords coords);

  void setJoinedInThisRound(boolean joined);

  void setNumber(int number);

  String toString();

  Color getFontColor();

  Color getBackgroundColor();
}
