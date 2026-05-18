// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class snake extends JPanel implements ActionListener {
   private static final int BOARD_WIDTH = 600;
   private static final int BOARD_HEIGHT = 600;
   private static final int DOT_SIZE = 20;
   private static final int ALL_DOTS = 900;
   private static final int RAND_POS = 30;
   private static final int DELAY = 120;
   private final int[] x = new int[900];
   private final int[] y = new int[900];
   private int bodyParts;
   private int appleX;
   private int appleY;
   private int score;
   private boolean leftDirection = false;
   private boolean rightDirection = true;
   private boolean upDirection = false;
   private boolean downDirection = false;
   private boolean inGame = true;
   private Timer timer;

   public snake() {
      this.initBoard();
   }

   private void initBoard() {
      this.setBackground(Color.BLACK);
      this.setFocusable(true);
      this.setPreferredSize(new Dimension(600, 600));
      this.addKeyListener(new TAdapter());
      this.initGame();
   }

   private void initGame() {
      this.bodyParts = 3;
      this.score = 0;

      for(int var1 = 0; var1 < this.bodyParts; ++var1) {
         this.x[var1] = 100 - var1 * 20;
         this.y[var1] = 100;
      }

      this.locateApple();
      this.timer = new Timer(120, this);
      this.timer.start();
   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      this.draw(var1);
   }

   private void draw(Graphics var1) {
      if (this.inGame) {
         var1.setColor(Color.RED);
         var1.fillOval(this.appleX, this.appleY, 20, 20);

         for(int var2 = 0; var2 < this.bodyParts; ++var2) {
            if (var2 == 0) {
               var1.setColor(Color.GREEN.brighter());
            } else {
               var1.setColor(Color.GREEN.darker());
            }

            var1.fillRect(this.x[var2], this.y[var2], 20, 20);
         }

         this.showScore(var1);
         Toolkit.getDefaultToolkit().sync();
      } else {
         this.gameOver(var1);
      }

   }

   private void showScore(Graphics var1) {
      String var2 = "Score: " + this.score;
      var1.setColor(Color.WHITE);
      var1.setFont(new Font("Helvetica", 1, 18));
      var1.drawString(var2, 10, 20);
   }

   private void gameOver(Graphics var1) {
      String var2 = "Game Over";
      String var3 = "Final Score: " + this.score;
      String var4 = "Press R to restart";
      var1.setColor(Color.WHITE);
      var1.setFont(new Font("Helvetica", 1, 36));
      FontMetrics var5 = this.getFontMetrics(var1.getFont());
      var1.drawString(var2, (600 - var5.stringWidth(var2)) / 2, 280);
      var1.setFont(new Font("Helvetica", 0, 22));
      var5 = this.getFontMetrics(var1.getFont());
      var1.drawString(var3, (600 - var5.stringWidth(var3)) / 2, 320);
      var1.drawString(var4, (600 - var5.stringWidth(var4)) / 2, 355);
   }

   private void locateApple() {
      int var1 = (int)(Math.random() * (double)30.0F);
      this.appleX = var1 * 20;
      var1 = (int)(Math.random() * (double)30.0F);
      this.appleY = var1 * 20;
   }

   private void move() {
      for(int var1 = this.bodyParts; var1 > 0; --var1) {
         this.x[var1] = this.x[var1 - 1];
         this.y[var1] = this.y[var1 - 1];
      }

      if (this.leftDirection) {
         int[] var10000 = this.x;
         var10000[0] -= 20;
      }

      if (this.rightDirection) {
         int[] var2 = this.x;
         var2[0] += 20;
      }

      if (this.upDirection) {
         int[] var3 = this.y;
         var3[0] -= 20;
      }

      if (this.downDirection) {
         int[] var4 = this.y;
         var4[0] += 20;
      }

   }

   private void checkApple() {
      if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
         ++this.bodyParts;
         this.score += 10;
         this.locateApple();
      }

   }

   private void checkCollision() {
      for(int var1 = this.bodyParts; var1 > 0; --var1) {
         if (var1 > 3 && this.x[0] == this.x[var1] && this.y[0] == this.y[var1]) {
            this.inGame = false;
         }
      }

      if (this.y[0] >= 600) {
         this.inGame = false;
      }

      if (this.y[0] < 0) {
         this.inGame = false;
      }

      if (this.x[0] >= 600) {
         this.inGame = false;
      }

      if (this.x[0] < 0) {
         this.inGame = false;
      }

      if (!this.inGame) {
         this.timer.stop();
      }

   }

   public void actionPerformed(ActionEvent var1) {
      if (this.inGame) {
         this.checkApple();
         this.checkCollision();
         this.move();
      }

      this.repaint();
   }

   private static void createAndShowGui() {
      JFrame var0 = new JFrame("Snake Game");
      snake var1 = new snake();
      var0.add(var1);
      var0.setDefaultCloseOperation(3);
      var0.pack();
      var0.setLocationRelativeTo((Component)null);
      var0.setResizable(false);
      var0.setVisible(true);
   }

   public static void main(String[] var0) {
      SwingUtilities.invokeLater(snake::createAndShowGui);
   }

   private class TAdapter extends KeyAdapter {
      private TAdapter() {
         Objects.requireNonNull(snake.this);
         super();
      }

      public void keyPressed(KeyEvent var1) {
         int var2 = var1.getKeyCode();
         if (var2 == 37 && !snake.this.rightDirection) {
            snake.this.leftDirection = true;
            snake.this.upDirection = false;
            snake.this.downDirection = false;
         }

         if (var2 == 39 && !snake.this.leftDirection) {
            snake.this.rightDirection = true;
            snake.this.upDirection = false;
            snake.this.downDirection = false;
         }

         if (var2 == 38 && !snake.this.downDirection) {
            snake.this.upDirection = true;
            snake.this.rightDirection = false;
            snake.this.leftDirection = false;
         }

         if (var2 == 40 && !snake.this.upDirection) {
            snake.this.downDirection = true;
            snake.this.rightDirection = false;
            snake.this.leftDirection = false;
         }

         if (!snake.this.inGame && var2 == 82) {
            snake.this.inGame = true;
            snake.this.leftDirection = false;
            snake.this.rightDirection = true;
            snake.this.upDirection = false;
            snake.this.downDirection = false;
            snake.this.initGame();
         }

      }
   }
}
