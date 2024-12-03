package gui;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JTabbedPane tabbedPane;
    
    public MainWindow() {
        setTitle("Sistema de Vendas de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Veículos", new VeiculosPanel());
        tabbedPane.addTab("Clientes", new ClientesPanel());
        
        add(tabbedPane);
        
        // Center the window on screen
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // Ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
} 