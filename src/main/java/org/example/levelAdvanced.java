import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class levelAdvanced extends JPanel implements ActionListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int DOT_SIZE = 20;
    private final int MAX_DOTS = (WIDTH * HEIGHT) / (DOT_SIZE * DOT_SIZE);
    private final int DELAY = 140;
    private final int BONUS_DELAY = 15000;  // 15 seconds for bonus fruit

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

    private Timer timer;
    private Random random;

    private JButton restartButton;
    private JButton quitButton;
    private String advice;

    public levelAdvanced() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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

        // Initialize advice
        advice = getRandomAdvice();

        // Initialize buttons
        restartButton = new JButton("Rejouer");
        quitButton = new JButton("Quitter");

        // Set buttons style and position
        restartButton.setBounds(WIDTH / 4 - 50, HEIGHT - 100, 100, 40);
        quitButton.setBounds(WIDTH / 2 + 50, HEIGHT - 100, 100, 40);

        restartButton.setVisible(false);
        quitButton.setVisible(false);

        restartButton.addActionListener(e -> restartGame());
        quitButton.addActionListener(e -> System.exit(0));

        restartButton.setBackground(new Color(0x4CAF50)); // Green
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setFocusPainted(false);

        quitButton.setBackground(new Color(0xF44336)); // Red
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

        // Draw apple
        g.setColor(Color.RED);
        g.fillRect(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

        // Draw bonus fruit
        if (bonusVisible) {
            g.setColor(Color.YELLOW);
            g.fillOval(bonus_x, bonus_y, DOT_SIZE, DOT_SIZE);
        }

        // Draw the snake
        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                g.setColor(Color.GREEN); // Snake head
            } else {
                g.setColor(new Color(0, 128, 0)); // Snake body
            }
            g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
        }

        // Draw walls (the "frame" or barriers)
        drawWalls(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        String message = "Game Over";
        String finalScore = "Score: " + score;
        String performance = getPerformance(score);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(message, (WIDTH - metrics.stringWidth(message)) / 2, HEIGHT / 3);

        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString(finalScore, (WIDTH - metrics.stringWidth(finalScore)) / 2, HEIGHT / 2);
        g.drawString("Performance: " + performance, (WIDTH - metrics.stringWidth(performance)) / 2, HEIGHT / 2 + 30);

        // Show advice and buttons
        g.drawString(advice, WIDTH / 2 - g.getFontMetrics().stringWidth(advice) / 2, HEIGHT / 2 + 60);

        // Show buttons
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
                "Évitez les murs !",
                "Planifiez vos déplacements à l'avance !",
                "Faites attention aux coins !",
                "Concentrez-vous sur les fruits bonus !",
                "Restez calme sous pression !",
                "La vitesse augmente, restez vigilant !",
                "Utilisez les espaces ouverts à votre avantage !",
                "Anticipez vos prochains mouvements !",
                "Ne poursuivez pas tous les fruits !",
                "La pratique rend parfait !"
        };
        return advices[random.nextInt(advices.length)];
    }

    private void drawWalls(Graphics g) {
        g.setColor(Color.GRAY);

        // Draw walls around the corners
        g.fillRect(0, 0, DOT_SIZE * 3, DOT_SIZE); // Top-left corner
        g.fillRect(WIDTH - DOT_SIZE * 3, 0, DOT_SIZE * 3, DOT_SIZE); // Top-right corner
        g.fillRect(0, HEIGHT - DOT_SIZE, DOT_SIZE * 3, DOT_SIZE); // Bottom-left corner
        g.fillRect(WIDTH - DOT_SIZE * 3, HEIGHT - DOT_SIZE, DOT_SIZE * 3, DOT_SIZE); // Bottom-right corner

        // Draw a wall in the middle of the game area
        for (int i = 0; i < HEIGHT / DOT_SIZE; i++) {
            g.fillRect(WIDTH / 2 - DOT_SIZE, i * DOT_SIZE, DOT_SIZE * 2, DOT_SIZE); // Vertical wall in the middle
        }
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
        bonus_x = random.nextInt(WIDTH / DOT_SIZE) * DOT_SIZE;
        bonus_y = random.nextInt(HEIGHT / DOT_SIZE) * DOT_SIZE;
    }

    private void checkBonusFruit() {
        if (bonusVisible && x[0] == bonus_x && y[0] == bonus_y) {
            score += 30;
            bonusVisible = false;
        }
    }

    private void locateApple() {
        apple_x = random.nextInt(WIDTH / DOT_SIZE) * DOT_SIZE;
        apple_y = random.nextInt(HEIGHT / DOT_SIZE) * DOT_SIZE;
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        // Check collision with the walls
        for (int z = dots; z > 0; z--) {
            if (z > 3 && x[0] == x[z] && y[0] == y[z]) {
                resetGame();
            }
        }

        // Check if the snake hits the walls or the middle wall
        if (x[0] < DOT_SIZE * 3 || x[0] >= WIDTH - DOT_SIZE * 3 || y[0] < DOT_SIZE || y[0] >= HEIGHT - DOT_SIZE) {
            resetGame();
        }

        // Check if the snake hits the middle wall
        if (x[0] >= WIDTH / 2 - DOT_SIZE && x[0] <= WIDTH / 2 + DOT_SIZE && y[0] >= 0 && y[0] < HEIGHT) {
            resetGame();
        }
    }

    private void resetGame() {
        dots = 3;
        fruitsEaten = 0;
        score = 0;
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;
        inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
            checkApple();
            checkBonusFruit();
            checkCollision();
        } else {
            timer.stop();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    private void restartGame() {
        inGame = true;
        restartButton.setVisible(false);
        quitButton.setVisible(false);
        advice = getRandomAdvice();
        initGame();
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game - Niveau Avancé");
        levelAdvanced board = new levelAdvanced();
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
