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

import java.util.TreeMap;

public class RomanNumbersStrategy implements TileDrawingStrategy {

  private static final TreeMap<Integer, String> numberMap = new TreeMap<Integer, String>();

  static {
    numberMap.put(1000, "M");
    numberMap.put(900, "CM");
    numberMap.put(500, "D");
    numberMap.put(400, "CD");
    numberMap.put(100, "C");
    numberMap.put(90, "XC");
    numberMap.put(50, "L");
    numberMap.put(40, "XL");
    numberMap.put(10, "X");
    numberMap.put(9, "IX");
    numberMap.put(5, "V");
    numberMap.put(4, "IV");
    numberMap.put(1, "I");
  }

  public RomanNumbersStrategy() {}

  @Override
  public String getStringToDraw(int number) {
    return convertToRoman(number);
  }

  @Override
  public float getFontSize(int drawableSize, int number) {
    String romanNumber = getStringToDraw(number);
    float fontSize = (float) (0.75 - (romanNumber.length() * 0.092)) * drawableSize;
    if (number == 128) {
      fontSize *= 2.25;
    }
    if (number == 2048) {
      fontSize *= 14.8;
    }
    return fontSize;
  }

  private static String convertToRoman(int number) {
    int l = numberMap.floorKey(number);
    if (number == l) {
      return numberMap.get(number);
    }
    return numberMap.get(l) + convertToRoman(number - l);
  }
}
