import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class level extends JFrame {

    public level() {
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

        // Ajouter une action au clic pour chaque niveau
        basicLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre de niveau basique
                new basicLevel().setVisible(true); // Crée la fenêtre du niveau basique
                dispose(); // Ferme la fenêtre level actuelle
            }
        });

        normalLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre de niveau normal
                new levelNormal().setVisible(true); // Crée la fenêtre du niveau normal
                dispose(); // Ferme la fenêtre level actuelle
            }
        });

        advancedLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir la fenêtre de niveau avancé
                new levelAdvanced().setVisible(true); // Crée la fenêtre du niveau avancé
                dispose(); // Ferme la fenêtre level actuelle
            }
        });

        // Ajouter le panel à la fenêtre
        add(panel);

        // Afficher la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Lancer la fenêtre
        new level();
    }
}
