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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.szib.twozerofoureight.tile.ITile;
import com.szib.twozerofoureight.tile.StandardTile;

public class Board {

  private Set<ITile> tiles;
  private List<Coords> emptyCoords;
  private int targetNumber;
  private int boardSize;

  private Random rng;
  private Map<Direction, List<Integer>> linesToMove;
  private int score;
  private int boardDimension;

  public Board() {
    this(2048, 4);
  }

  public Board(int target, int boardSize) {
    resetBoard(target, boardSize);
  }

  private void addNewTile() {
    if (!isFull()) {
      tiles.add(new StandardTile(emptyCoords.remove(rng.nextInt(emptyCoords.size()))));
    }
  }

  private void clear() {
    tiles.clear();
    emptyCoords.clear();
    for (int x = 0; x < boardSize; x++) {
      for (int y = 0; y < boardSize; y++) {
        emptyCoords.add(new Coords(x, y));
      }
    }
  }

  private void initLinesToMove() {
    linesToMove = new HashMap<>();
    for (Direction direction : Direction.values()) {
      if (direction == Direction.DOWN | direction == Direction.RIGHT) {
        linesToMove.put(
            direction,
            IntStream.range(0, boardSize - 1)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList()));
      } else {
        linesToMove.put(
            direction, IntStream.range(1, boardSize).boxed().collect(Collectors.toList()));
      }
    }
  }

  public int getBoardSize() {
    return boardSize;
  }

  public List<Integer> getLinesToMove(Direction direction) {
    return linesToMove.get(direction);
  }

  private Optional<ITile> getNeighbour(Coords coords, Direction direction) {
    return tiles
        .stream()
        .filter((tile) -> tile.getCoords().equals(Coords.getCoords(coords, direction)))
        .findFirst();
  }

  private List<ITile> getNeighbours(ITile tile) {
    List<ITile> list = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      getNeighbour(tile.getCoords(), direction).ifPresent(list::add);
    }
    return list;
  }

  public int getTargetNumber() {
    return targetNumber;
  }

  private Optional<ITile> getTile(Coords coords) {
    return tiles.stream().filter((tile) -> tile.getCoords().equals(coords)).findFirst();
  }

  private List<ITile> getTilesToMove(Direction direction) {
    List<Integer> lines = getLinesToMove(direction);
    List<ITile> sortedTiles = new ArrayList<>();

    if (direction.isHorizontal()) {
      for (int line : lines) {
        tiles.stream().filter((tile) -> tile.isInColoumn(line)).forEach(sortedTiles::add);
      }
    } else {
      for (int line : lines) {
        tiles.stream().filter((tile) -> tile.isInRow(line)).forEach(sortedTiles::add);
      }
    }
    return sortedTiles;
  }

  private void joinToNeighbour(ITile tile, Optional<ITile> neighbour) {
    emptyCoords.add(tile.getCoords());
    tiles.remove(tile);
    neighbour.get().doubleUp();
    incrementScoreBy(neighbour.get().getNumber());
  }

  private boolean moveOneTile(Direction direction, ITile tile) {
    boolean hasMoved = false;
    while (tile.canJoin(getNeighbours(tile), direction)
        && isValidCoord(Coords.getCoords(tile.getCoords(), direction))) {
      Optional<ITile> neighbour = getNeighbour(tile.getCoords(), direction);
      if (neighbour.isPresent()) {
        joinToNeighbour(tile, neighbour);
        hasMoved = true;
      } else {
        moveToTheAdjacentCell(tile, direction);
        hasMoved = true;
      }
    }
    return hasMoved;
  }

  public boolean moveTiles(Direction direction) {
    boolean needNewTiles = false;
    for (ITile tile : getTilesToMove(direction)) {
      needNewTiles = moveOneTile(direction, tile) | needNewTiles;
    }
    resetTiles();
    if (needNewTiles) {
      addNewTile();
    }
    return !isFull() | hasMoreMove();
  }

  private void moveToTheAdjacentCell(ITile tile, Direction direction) {
    emptyCoords.add(tile.getCoords());
    tile.setCoords(Coords.getCoords(tile.getCoords(), direction));
    emptyCoords.remove(tile.getCoords());
  }

  public void resetBoard() {
    resetBoard(targetNumber, boardSize);
  }

  public void resetBoard(int target, int boardSize) {
    this.targetNumber = target;
    this.boardSize = boardSize;

    this.tiles = new HashSet<>();
    this.emptyCoords = new ArrayList<>();
    this.rng = new Random();
    this.score = 0;

    clear();
    initLinesToMove();
    addNewTile();
    addNewTile();
  }

  private void resetTiles() {
    tiles
        .stream()
        .filter((tile) -> tile.isJoinedInThisRound())
        .forEach((tile) -> tile.setJoinedInThisRound(false));
  }

  public boolean hasMoreMove() {
    if (isFull()) {
      for (ITile tile : tiles) {
        if (tile.canJoin(getNeighbours(tile))) {
          return true;
        }
      }
    } else {
      return true;
    }
    return false;
  }

  public boolean hasReachedTarget() {
    return tiles.stream().filter((tile) -> tile.getNumber() == targetNumber).findAny().isPresent();
  }

  public boolean isFull() {
    return getBoardSize() * getBoardSize() == tiles.size();
  }

  private boolean isValidCoord(Coords coords) {
    return coords.getX() >= 0
        && coords.getX() < boardSize
        && coords.getY() >= 0
        && coords.getY() < boardSize;
  }

  private void incrementScoreBy(int score) {
    this.score += score;
  }

  public int getScore() {
    return score;
  }

  public int getBoardDimension() {
    return boardDimension;
  }

  public void setBoardDimension(int drawableSize) {
    this.boardDimension = drawableSize;
  }

  public void draw(Graphics graphics, Dimension boardDimension) {
    setBoardDimension((int) Math.min(boardDimension.getWidth(), boardDimension.getHeight()));
    int gapBetweenTiles = (int) (getBoardDimension() * 0.01 + 1);
    int tileSize =
        (getBoardDimension() - ((getBoardSize() + 1) * gapBetweenTiles)) / getBoardSize();

    graphics.setColor(new Color(0.95f, 0.9f, 0.85f, 0.15f));
    graphics.fillRect(2, 2, getBoardDimension() - 4, getBoardDimension() - 4);
    tiles
        .stream()
        .forEach((tile) -> tile.draw(graphics, getBoardDimension(), tileSize, gapBetweenTiles));
  }

  @Override
  public String toString() {
    StringBuilder sBuilder = new StringBuilder();

    for (int y = 0; y < getBoardSize(); y++) {
      for (int x = 0; x < getBoardSize(); x++) {
        Optional<ITile> tile = getTile(new Coords(x, y));
        if (tile.isPresent()) {
          sBuilder.append(tile.get().toString());
        } else {
          sBuilder.append("[ ] ");
        }
      }
      sBuilder.append('\n');
    }
    return sBuilder.toString();
  }
}
