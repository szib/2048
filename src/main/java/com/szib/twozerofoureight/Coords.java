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
package com.szib.twozerofoureight;

public class Coords {

  private int coordinateX, coordinateY;

  public static Coords getCoords(Coords coords, Direction direction) {
    return new Coords(coords.getX() + direction.getX(), coords.getY() + direction.getY());
  }

  public Coords(int coordinateX, int coordinateY) {
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
  }

  public int getX() {
    return coordinateX;
  }

  public int getY() {
    return coordinateY;
  }

  public void setX(int coordinateX) {
    this.coordinateX = coordinateX;
  }

  public void setY(int coordinateY) {
    this.coordinateY = coordinateY;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Coords)) {
      return false;
    }
    Coords other = (Coords) obj;
    if (coordinateX != other.coordinateX) {
      return false;
    }
    if (coordinateY != other.coordinateY) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + coordinateX;
    result = prime * result + coordinateY;
    return result;
  }

  @Override
  public String toString() {
    return "Coords [coordinateX=" + coordinateX + ", coordinateY=" + coordinateY + "]";
  }
}
