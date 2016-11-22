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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

import com.szib.twozerofoureight.Board;
import com.szib.twozerofoureight.Direction;
import com.szib.twozerofoureight.HighScore;
import com.szib.twozerofoureight.tile.ArabicNumbersStrategy;
import com.szib.twozerofoureight.tile.RomanNumbersStrategy;
import com.szib.twozerofoureight.tile.TileDrawingStrategy;

public class GUI extends JFrame {

  private final class ActionBoardSize implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JRadioButtonMenuItem source = (JRadioButtonMenuItem) e.getSource();
      if (source.getText() == "5x5") {
        setBoardSize(5);
      } else {
        setBoardSize(4);
      }
      resetGame();
    }
  }

  private final class ActionNewGame implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent e) {
      resetGame();
    }
  }

  private final class ActionShowAboutDialog implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent e) {
      JDialog about = new AboutDialog();
      about.setModal(true);
      about.setVisible(true);
    }
  }

  private final class ActionSetArabicNumbers implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      setDrawingStrategy(new ArabicNumbersStrategy());
    }
  }

  private final class ActionSetRomanNumbers implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      setDrawingStrategy(new RomanNumbersStrategy());
    }
  }

  private static final long serialVersionUID = 7923504931787121247L;

  private Board board;

  private TileDrawingStrategy drawingStrategy;

  private final BoardPanel boardPanel;

  Dimension screenSize, frameMinimumSize;

  private JLabel lblScore;

  private JLabel lblHighScore;

  private boolean running;

  private Dimension frameMaximumSize;

  private int targetNumber = 2048;

  private int boardSize = 4;

  private int highScore;

  /** Launch the application. */
  public static void main(String[] args) {
    EventQueue.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            GUI frame = new GUI();
            frame.setVisible(true);
            frame.addWindowListener(
                new WindowAdapter() {
                  public void windowClosing(WindowEvent e) {
                    HighScore.saveHighScore(frame.getHighScore());
                  }
                });
          }
        });
  }

  /** Create the frame. */
  public GUI() {
    initFields();
    setTitle("2048 Game");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setMinimumSize(frameMinimumSize);
    setMaximumSize(frameMaximumSize);
    setBounds(100, 20, frameMinimumSize.width, frameMinimumSize.height);

    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu mnFile = new JMenu("File");
    menuBar.add(mnFile);

    JMenuItem mntmNewGame = new JMenuItem("New game");
    mntmNewGame.addActionListener(new ActionNewGame());
    mnFile.add(mntmNewGame);

    JMenuItem mntmExit = new JMenuItem("Exit");
    mntmExit.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose();
            System.exit(0);
          }
        });
    mnFile.add(mntmExit);

    JMenu mnSettings = new JMenu("Settings");
    menuBar.add(mnSettings);

    JMenu mnBoardSize = new JMenu("Board size");
    mnSettings.add(mnBoardSize);

    JRadioButtonMenuItem rdbtnmntmSize4 = new JRadioButtonMenuItem("4x4");
    rdbtnmntmSize4.addActionListener(new ActionBoardSize());
    rdbtnmntmSize4.setSelected(true);
    mnBoardSize.add(rdbtnmntmSize4);

    JRadioButtonMenuItem rdbtnmntmSize5 = new JRadioButtonMenuItem("5x5");
    rdbtnmntmSize5.addActionListener(new ActionBoardSize());
    mnBoardSize.add(rdbtnmntmSize5);

    ButtonGroup boardSizeGroup = new ButtonGroup();
    boardSizeGroup.add(rdbtnmntmSize4);
    boardSizeGroup.add(rdbtnmntmSize5);

    JMenu mnTileStyle = new JMenu("Tile style");
    mnSettings.add(mnTileStyle);

    JRadioButtonMenuItem rdbtnmntmRomanNumbers = new JRadioButtonMenuItem("Roman numbers");
    rdbtnmntmRomanNumbers.addActionListener(new ActionSetRomanNumbers());
    mnTileStyle.add(rdbtnmntmRomanNumbers);

    JRadioButtonMenuItem rdbtnmntmArabicNumbers = new JRadioButtonMenuItem("Arabic numbers");
    rdbtnmntmArabicNumbers.addActionListener(new ActionSetArabicNumbers());
    rdbtnmntmArabicNumbers.setSelected(true);
    mnTileStyle.add(rdbtnmntmArabicNumbers);

    ButtonGroup tileStyleGroup = new ButtonGroup();
    tileStyleGroup.add(rdbtnmntmArabicNumbers);
    tileStyleGroup.add(rdbtnmntmRomanNumbers);

    JMenu mnHelp = new JMenu("Help");
    menuBar.add(mnHelp);

    JMenuItem mntmAbout = new JMenuItem("About");
    mntmAbout.addActionListener(new ActionShowAboutDialog());
    mnHelp.add(mntmAbout);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(20, 0));
    setContentPane(contentPane);

    boardPanel = new BoardPanel(board, drawingStrategy);
    boardPanel.setPreferredSize(new Dimension(510, 510));
    boardPanel.setBackground(Color.BLACK);
    contentPane.add(boardPanel, BorderLayout.CENTER);

    JPanel sidePanel = new JPanel();
    sidePanel.setBackground(Color.LIGHT_GRAY);
    contentPane.add(sidePanel, BorderLayout.SOUTH);
    sidePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

    lblScore = new JLabel("");
    sidePanel.add(lblScore);

    lblHighScore = new JLabel("High score: 0");
    sidePanel.add(lblHighScore);
    pack();
    updateScore();

    registerKeyBindings();
  }

  private void initFields() {
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frameMinimumSize = new Dimension(200, 200);
    frameMaximumSize = screenSize;
    highScore = HighScore.loadHighScore();
    board = new Board(targetNumber, boardSize);
    setRunning(true);
  }

  private void registerKeyBindings() {
    InputMap inputMap = boardPanel.getInputMap(JComponent.WHEN_FOCUSED);
    inputMap.put(KeyStroke.getKeyStroke("UP"), Direction.UP);
    inputMap.put(KeyStroke.getKeyStroke("DOWN"), Direction.DOWN);
    inputMap.put(KeyStroke.getKeyStroke("LEFT"), Direction.LEFT);
    inputMap.put(KeyStroke.getKeyStroke("RIGHT"), Direction.RIGHT);

    inputMap = boardPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke("UP"), Direction.UP);
    inputMap.put(KeyStroke.getKeyStroke("DOWN"), Direction.DOWN);
    inputMap.put(KeyStroke.getKeyStroke("LEFT"), Direction.LEFT);
    inputMap.put(KeyStroke.getKeyStroke("RIGHT"), Direction.RIGHT);

    ActionMap actionMap = boardPanel.getActionMap();
    for (Direction dir : Direction.values()) {
      actionMap.put(dir, new KeyAction(this, dir));
    }
  }

  protected void moveTiles(Direction direction) {
    board.moveTiles(direction);
    updateScore();

    if (board.hasReachedTarget() | !board.hasMoreMove()) {
      setRunning(false);
    }
  }

  private void updateScore() {
    lblScore.setText("Score: " + board.getScore());
    if (board.getScore() > highScore) {
      highScore = board.getScore();
    }
    lblHighScore.setText("High score: " + highScore);
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
    if (!running) {
      HighScore.saveHighScore(highScore);
    }
  }

  public int getBoardSize() {
    return boardSize;
  }

  public void setBoardSize(int boardSize) {
    this.boardSize = boardSize;
  }

  /** */
  private void resetGame() {
    setRunning(false);
    board.resetBoard(targetNumber, boardSize);
    updateScore();
    setRunning(true);
    repaint();
  }

  public int getHighScore() {
    return highScore;
  }

  public void setDrawingStrategy(TileDrawingStrategy drawingStrategy) {
    this.drawingStrategy = drawingStrategy;
    boardPanel.setDrawingStrategy(drawingStrategy);
  }
}
