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
package com.szib.twozerofoureightTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.szib.twozerofoureight.Coords;
import com.szib.twozerofoureight.tile.ArabicNumbersStrategy;
import com.szib.twozerofoureight.tile.ITile;
import com.szib.twozerofoureight.tile.Tile;

public class TileTest {

  private ITile t1;
  private ITile t2;

  @Before
  public void setUp() {
    t1 = new Tile(new Coords(1, 1), new ArabicNumbersStrategy());
    t2 = new Tile(new Coords(1, 2), new ArabicNumbersStrategy());
  }

  @After
  public void tearDown() {
    t1 = t2 = null;
  }

  @Test
  public void testJoinTrue() {
    t1.setNumber(16);
    t2.setNumber(16);
    assertEquals(t1.getNumber(), t2.getNumber());

    assertTrue(t1.canJoinTo(t2));
    assertTrue(t2.canJoinTo(t1));
  }

  @Test
  public void testJoinFalse() {
    t1.setNumber(16);
    t2.setNumber(128);
    assertFalse(t1.canJoinTo(t2));
    assertFalse(t2.canJoinTo(t1));
  }

  @Test
  public void testSameCoordsFalse() {
    assertFalse(t1.hasTheSameCoords(t2));
    assertFalse(t2.hasTheSameCoords(t1));
  }

  @Test
  public void testSameCoordsTrue() {
    t2.setCoords(new Coords(1, 1));
    assertTrue(t1.hasTheSameCoords(t2));
    assertTrue(t2.hasTheSameCoords(t1));
  }
}
