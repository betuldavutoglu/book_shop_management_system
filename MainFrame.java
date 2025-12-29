package ui;

import javax.swing.*;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private BookPanel bookPanel;
    private CustomerPanel customerPanel;
    private OrderPanel orderPanel;
    private DashboardPanel dashboardPanel;
    
    public MainFrame() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Book Shop Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        
        JMenuBar menuBar = new JMenuBar();
        
       
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Book Shop Management System\nVersion 1.0\nDeveloped with Java Swing",
            "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
       
        tabbedPane = new JTabbedPane();
        
        
        dashboardPanel = new DashboardPanel();
        bookPanel = new BookPanel();          
        customerPanel = new CustomerPanel();  
        orderPanel = new OrderPanel();
        
        
        tabbedPane.addTab("Dashboard", dashboardPanel);
        tabbedPane.addTab("Books", bookPanel);           
        tabbedPane.addTab("Customers", customerPanel);   
        tabbedPane.addTab("Orders", orderPanel);
        
        add(tabbedPane);
        
       
        try {
            ImageIcon icon = new ImageIcon("book_icon.png");
            if (icon.getImage() != null) {
                setIconImage(icon.getImage());
            }
        } catch (Exception e) {
            System.out.println("Icon file not found, continuing without icon.");
        }
    }
    
    public static void main(String[] args) {
        
        try {
            database.veriTabani.initialize();
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Database initialization failed: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
    
    public BookPanel getBookPanel() {
        return bookPanel;
    }

    public CustomerPanel getCustomerPanel() {
        return customerPanel;
    }
}
