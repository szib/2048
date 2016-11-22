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

import java.awt.Graphics;

import javax.swing.JPanel;

import com.szib.twozerofoureight.Board;
import com.szib.twozerofoureight.tile.TileDrawingStrategy;

public class BoardPanel extends JPanel {

  private static final long serialVersionUID = -6115698763263796312L;
  private Board board;
  private TileDrawingStrategy drawingStrategy;

  public BoardPanel(Board board, TileDrawingStrategy drawingStrategy) {
    this.board = board;
    this.drawingStrategy = drawingStrategy;
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    board.draw(graphics, this.getSize());

    if (!board.hasMoreMove()) {
      graphics.drawImage(EndImage.getLostImage(this.board.getBoardDimension()), 0, 0, null);
    }

    if (board.hasReachedTarget()) {
      graphics.drawImage(EndImage.getWonImage(this.board.getBoardDimension()), 0, 0, null);
    }
  }

  public void setDrawingStrategy(TileDrawingStrategy drawingStrategy) {
    this.drawingStrategy = drawingStrategy;
    board.setDrawingStrategy(drawingStrategy);
    this.paintComponent(this.getGraphics());
  }
}
