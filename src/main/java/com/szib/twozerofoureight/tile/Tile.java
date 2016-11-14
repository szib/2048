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

import java.util.List;

import com.szib.twozerofoureight.Coords;
import com.szib.twozerofoureight.Direction;

public class Tile {

  protected int number;
  protected Coords coords;
  protected boolean joinedInThisRound;

  public Tile() {
    super();
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
}
