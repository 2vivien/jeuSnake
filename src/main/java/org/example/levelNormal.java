import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class levelNormal extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public levelNormal() {
        setTitle("Snake Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GameBoard()); // Ajoute ton panneau de jeu ici
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            levelNormal game = new levelNormal();
            game.setVisible(true);
        });
    }
}

class GameBoard extends JPanel implements ActionListener {

    private static final int DOT_SIZE = 20;
    private static final int MAX_DOTS = (800 * 600) / (DOT_SIZE * DOT_SIZE);
    private static final int DELAY = 140;
    private static final int BONUS_DELAY = 15000;

    private final int[] x = new int[MAX_DOTS];
    private final int[] y = new int[MAX_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int bonus_x;
    private int bonus_y;
    private boolean bonusVisible = false;
    private int bonusTimer = 0;

    private int score = 0;
    private int fruitsEaten = 0;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean paused = false;

    private Timer timer;
    private Random random;

    private JButton restartButton;
    private JButton quitButton;
    private String advice;

    public GameBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        random = new Random();
        initGame();
    }

    private void initGame() {
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 100 - z * DOT_SIZE;
            y[z] = 100;
        }

        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();

        advice = getRandomAdvice();

        restartButton = new JButton("Rejouer");
        quitButton = new JButton("Quitter");

        restartButton.setBounds(300, 500, 100, 40);
        quitButton.setBounds(400, 500, 100, 40);

        restartButton.setVisible(false);
        quitButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());
        quitButton.addActionListener(e -> System.exit(0));

        restartButton.setBackground(new Color(0x4CAF50));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setFocusPainted(false);

        quitButton.setBackground(new Color(0xF44336));
        quitButton.setForeground(Color.WHITE);
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.setFocusPainted(false);

        add(restartButton);
        add(quitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawObjects(g);
        } else {
            gameOver(g);
        }
    }

    private void drawObjects(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 14));
        String scoreText = "Score: " + score;
        g.drawString(scoreText, 10, 20);

        g.setColor(Color.RED);
        g.fillRect(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

        if (bonusVisible) {
            g.setColor(Color.YELLOW);
            g.fillOval(bonus_x, bonus_y, DOT_SIZE, DOT_SIZE);
        }

        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(new Color(0, 128, 0));
            }
            g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        String message = "Game Over";
        String finalScore = "Score: " + score;
        String performance = getPerformance(score);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(message, (800 - metrics.stringWidth(message)) / 2, 200);

        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString(finalScore, (800 - metrics.stringWidth(finalScore)) / 2, 250);
        g.drawString("Performance: " + performance, (800 - metrics.stringWidth(performance)) / 2, 280);

        g.drawString(advice, 400 - g.getFontMetrics().stringWidth(advice) / 2, 320);

        restartButton.setVisible(true);
        quitButton.setVisible(true);
    }

    private String getPerformance(int score) {
        if (score < 500) {
            return "Nul";
        } else if (score < 1000) {
            return "Pas mal";
        } else if (score < 1500) {
            return "Assez bien";
        } else if (score < 2000) {
            return "Bien";
        } else if (score < 5000) {
            return "Fort";
        } else if (score < 10000) {
            return "Excellent";
        } else {
            return "Master!";
        }
    }

    private String getRandomAdvice() {
        String[] advices = {
                "Planifiez vos déplacements à l'avance !",
                "Faites attention aux coins !",
                "Concentrez-vous sur les fruits bonus !",
                "Restez calme sous pression !",
                "La vitesse augmente, restez vigilant !",
                "Utilisez les espaces ouverts à votre avantage !",
                "Anticipez vos prochains mouvements !",
                "Ne poursuivez pas tous les fruits !",
                "La pratique rend parfait !",
                "Restez concentré sur votre objectif !"
        };
        return advices[random.nextInt(advices.length)];
    }

    private void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            score += 10;
            fruitsEaten++;

            if (fruitsEaten % 4 == 0) {
                spawnBonusFruit();
            }

            locateApple();
        }
    }

    private void spawnBonusFruit() {
        bonusVisible = true;
        bonusTimer = BONUS_DELAY;
        bonus_x = random.nextInt(800 / DOT_SIZE) * DOT_SIZE;
        bonus_y = random.nextInt(600 / DOT_SIZE) * DOT_SIZE;
    }

    private void checkBonusFruit() {
        if (bonusVisible && x[0] == bonus_x && y[0] == bonus_y) {
            score += 30;
            bonusVisible = false;
        }
    }

    private void locateApple() {
        apple_x = random.nextInt(800 / DOT_SIZE) * DOT_SIZE;
        apple_y = random.nextInt(600 / DOT_SIZE) * DOT_SIZE;
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
            if (x[0] < 0) {
                x[0] = 800 - DOT_SIZE;
            }
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
            if (x[0] >= 800) {
                x[0] = 0;
            }
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
            if (y[0] < 0) {
                y[0] = 600 - DOT_SIZE;
            }
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
            if (y[0] >= 600) {
                y[0] = 0;
            }
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void restartGame() {
        inGame = true;
        paused = false;
        dots = 3;
        score = 0;
        fruitsEaten = 0;
        bonusVisible = false;
        bonusTimer = 0;

        for (int z = 0; z < dots; z++) {
            x[z] = 100 - z * DOT_SIZE;
            y[z] = 100;
        }

        locateApple();
        advice = getRandomAdvice();

        restartButton.setVisible(false);
        quitButton.setVisible(false);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && !paused) {
            checkApple();
            checkBonusFruit();
            move();
            checkCollision();

            if (bonusVisible) {
                bonusTimer -= DELAY;
                if (bonusTimer <= 0) {
                    bonusVisible = false;
                }
            }

            repaint();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && !upDirection) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if (key == KeyEvent.VK_SPACE) {
                paused = !paused;
            }
        }
    }
}
