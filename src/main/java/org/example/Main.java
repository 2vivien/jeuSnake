import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {

    public Main() {
        // Configurer la fenêtre principale
        setTitle("Jeu Snake");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal pour la page d'accueil
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(135, 206, 250)); // Bleu ciel

                // Dessiner le titre
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", Font.BOLD, 50));
                g.drawString("Jeu Snake", 280, 100);

                // Dessiner le serpent (plus dynamique)
                g.setColor(new Color(34, 139, 34)); // Vert foncé pour le corps
                int startX = 200, startY = 250, segmentSize = 30;
                for (int i = 0; i < 6; i++) {
                    if (i % 2 == 0) {
                        g.setColor(new Color(50, 205, 50)); // Alternance de couleurs
                    } else {
                        g.setColor(new Color(34, 139, 34));
                    }
                    g.fillRect(startX + i * segmentSize, startY, segmentSize, segmentSize);
                }

                // Dessiner le fruit en carré jaune
                g.setColor(Color.YELLOW);
                g.fillRect(350, 180, 30, 30);
            }
        };
        panel.setLayout(null);

        // Créer un bouton "Jouer"
        JButton playButton = new JButton("Jouer");
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        playButton.setBackground(new Color(34, 139, 34)); // Vert foncé
        playButton.setForeground(Color.WHITE); // Texte blanc
        playButton.setBounds(300, 400, 200, 60); // Position et taille du bouton
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);

        // Ajouter une animation de survol
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setBackground(new Color(50, 205, 50)); // Vert clair au survol
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setBackground(new Color(34, 139, 34)); // Revenir au vert foncé
            }
        });

        // Ajouter une action au clic
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre Level
                new Level();
                dispose(); // Fermer la fenêtre actuelle
            }
        });

        // Ajouter le bouton au panel
        panel.add(playButton);

        // Ajouter le texte d'instruction
        JLabel instructionLabel = new JLabel("Appuyez ici pour commencer");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBounds(300, 480, 300, 30); // Position du texte
        panel.add(instructionLabel);

        // Ajouter le panel à la fenêtre
        add(panel);

        // Afficher la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Lancer l'application
        SwingUtilities.invokeLater(Main::new);
    }
}

// Nouvelle fenêtre Level
class Level extends JFrame {

    public Level() {
        // Configurer la fenêtre Level
        setTitle("Sélection du Niveau");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal pour les niveaux
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(135, 206, 250)); // Fond bleu ciel

        // Ajouter des boutons pour les niveaux
        JButton basicLevelButton = new JButton("Niveau Basique");
        JButton normalLevelButton = new JButton("Niveau Normal");
        JButton advancedLevelButton = new JButton("Niveau Avancé");

        // Configurer les boutons
        JButton[] buttons = {basicLevelButton, normalLevelButton, advancedLevelButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setBackground(new Color(34, 139, 34)); // Vert
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(50, 205, 50)); // Vert clair au survol
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(new Color(34, 139, 34)); // Retour au vert foncé
                }
            });
            panel.add(button);
        }

        // Ajouter une action au clic
        basicLevelButton.addActionListener(e -> {
            new LevelBasic();
            dispose();
        });

        normalLevelButton.addActionListener(e -> {
            new LevelNormal();
            dispose();
        });

        advancedLevelButton.addActionListener(e -> {
            new LevelAdvanced();
            dispose();
        });

        // Ajouter le panel à la fenêtre
        add(panel);

        // Afficher la fenêtre
        setVisible(true);
    }
}

// Classe LevelBasic
class LevelBasic extends JFrame {
    public LevelBasic() {
        setTitle("Niveau Basique");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Bienvenue au Niveau Basique", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
        setVisible(true);
    }
}

// Classe LevelNormal
class LevelNormal extends JFrame {
    public LevelNormal() {
        setTitle("Niveau Normal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Bienvenue au Niveau Normal", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
        setVisible(true);
    }
}

// Classe LevelAdvanced
class LevelAdvanced extends JFrame {
    public LevelAdvanced() {
        setTitle("Niveau Avancé");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Bienvenue au Niveau Avancé", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
        setVisible(true);
    }
}
