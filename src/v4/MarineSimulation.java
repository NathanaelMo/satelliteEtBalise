package v4;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import v4.antlr.MarineShellLexer;
import v4.antlr.MarineShellParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class MarineSimulation {
    private static final int TAILLE_FENETRE = 800;
    private static ArrayList<BaliseView> baliseViews = new ArrayList<>();
    private static ArrayList<SatelliteView> satelliteViews = new ArrayList<>();
    private static volatile boolean running = true;
    private static NiRectangle container;
    private static NiSpace space;
    private static boolean shellMarineIsRunning= true;
    private static BufferedReader reader;

    public static void main(String[] args) {
        // Démarrer l'interface graphique dans l'EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            System.out.println("DEBUG: Starting MarineSimulation in EDT");
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Création de l'espace de simulation
        space = new NiSpace("Marine Simulation", new Dimension(TAILLE_FENETRE, 600));
        container = new NiRectangle();
        container.setLayout(null);
        container.setSize(new Dimension(TAILLE_FENETRE, 600));
        container.setVisible(true);
        space.add(container);
        space.setVisible(true);

        // Ajout du ciel et de la mer
        MarineSky sky = new MarineSky(TAILLE_FENETRE, 320);
        sky.setLocation(0, 0);
        sky.setVisible(true);
        container.add(sky);

        MarineSea sea = new MarineSea(TAILLE_FENETRE, 300);
        sea.setLocation(0, 320);
        sea.setVisible(true);
        container.add(sea);

        space.openInWindow();

        // Démarrer le thread de mise à jour
        startUpdateThread();

        Thread shellThread = new Thread(() -> {
            System.out.println("MarineShell started. Type 'exit' to quit.");
            reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (shellMarineIsRunning) {
                    System.out.print("marine> ");
                    String input = reader.readLine();

                    if (input == null || input.equalsIgnoreCase("exit")) {
                        shellMarineIsRunning = false;
                        continue;
                    }

                    try {
                        // Ajouter un point-virgule si l'utilisateur l'a oublié
                        if (!input.trim().endsWith(";")) {
                            input += ";";
                        }

                        // Création du lexer
                        MarineShellLexer lexer = new MarineShellLexer(
                                CharStreams.fromString(input)
                        );
                        lexer.removeErrorListeners();
                        lexer.addErrorListener(new CustomErrorListener());

                        // Création du stream de tokens
                        CommonTokenStream tokens = new CommonTokenStream(lexer);

                        // Création du parser
                        MarineShellParser parser = new MarineShellParser(tokens);
                        parser.removeErrorListeners();
                        parser.addErrorListener(new CustomErrorListener());

                        // Parsing et exécution
                        ParseTree tree = parser.program();
                        visitor.visit(tree);

                    } catch (Exception e) {
                        System.err.println("Error executing command: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                cleanup();
            }
        });

        shellThread.setName("MarineShell-Thread");
        shellThread.start();
    }


    public static void addSatelliteView(SatelliteView view) {
        if (view != null) {
            System.out.println("DEBUG: Adding satellite view");
            // S'assurer que l'ajout se fait dans l'EDT
            SwingUtilities.invokeLater(() -> {
                satelliteViews.add(view);
                view.setVisible(true);
                container.add(view);
                container.revalidate();
                container.repaint();
                System.out.println("DEBUG: Satellite view added to container, total: " + satelliteViews.size());
            });
        }
    }

    public static void addBaliseView(BaliseView view) {
        if (view != null) {
            System.out.println("DEBUG: Adding balise view");
            // S'assurer que l'ajout se fait dans l'EDT
            SwingUtilities.invokeLater(() -> {
                baliseViews.add(view);
                view.setVisible(true);
                container.add(view);
                container.revalidate();
                container.repaint();
                System.out.println("DEBUG: Balise view added to container, total: " + baliseViews.size());
            });
        }
    }

    public static void updateViews() {
        // Mettre à jour les positions dans l'EDT
        SwingUtilities.invokeLater(() -> {
            // Update satellites
            for (SatelliteView view : satelliteViews) {
                if (view != null && view.getModel() != null) {
                    view.getModel().update();
                    view.update();
                    System.out.println("DEBUG: Updated satellite position: " + 
                        view.getModel().getX() + "," + view.getModel().getY());
                }
            }

            // Update balises
            for (BaliseView view : baliseViews) {
                if (view != null && view.getModel() != null) {
                    view.getModel().update();
                    view.update();
                    System.out.println("DEBUG: Updated balise position: " + 
                        view.getModel().getX() + "," + view.getModel().getY());
                }
            }

            container.revalidate();
            container.repaint();
        });
    }
    public void stopShell() {
        shellMarineIsRunning = false;
    }

    private static void cleanup() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Error closing reader: " + e.getMessage());
        }
    }
    public static void startUpdateThread() {
        Thread updateThread = new Thread(() -> {
            System.out.println("DEBUG: Update thread started");
            while (running) {
                updateViews();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Update Thread");
        
        // Ne pas utiliser un thread daemon pour être sûr qu'il continue à s'exécuter
        updateThread.setDaemon(false);
        updateThread.start();
    }
}
