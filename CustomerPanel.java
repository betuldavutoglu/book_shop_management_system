package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import service.CustomerService;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class CustomerPanel extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private CustomerService customerService;
    
    public CustomerPanel() {
        this.customerService = new CustomerService();
        initializeUI();
        loadCustomers();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Top panel with title and search
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");
        
        searchButton.addActionListener(e -> searchCustomers());
        clearButton.addActionListener(e -> {
            searchField.setText("");
            loadCustomers();
        });
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Table - DİKKAT: Eğer tablonuzda address alanı yoksa bunu kaldırın veya tabloya ekleyin
        String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone", "Registration Date"}; // Address yerine Registration Date
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Integer.class : Object.class;
            }
        };
        
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.setAutoCreateRowSorter(true); // Sıralama özelliği
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addButton = new JButton("Add Customer");
        JButton editButton = new JButton("Edit Customer");
        JButton deleteButton = new JButton("Delete Customer");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddEditDialog(null));
        editButton.addActionListener(e -> editSelectedCustomer());
        deleteButton.addActionListener(e -> deleteSelectedCustomer());
        refreshButton.addActionListener(e -> loadCustomers());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadCustomers() {
        tableModel.setRowCount(0);
        List<Customer> customers = customerService.getAllCustomers();
        
        if (customers != null) {
            for (Customer customer : customers) {
                Object[] row = {
                    customer.getCustomerId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getRegistrationDate() != null ? 
                        customer.getRegistrationDate().toString() : "N/A"
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void searchCustomers() {
        String keyword = searchField.getText().trim();
        if (!keyword.isEmpty()) {
            tableModel.setRowCount(0);
            List<Customer> customers = customerService.searchCustomers(keyword);
            
            if (customers != null) {
                for (Customer customer : customers) {
                    Object[] row = {
                        customer.getCustomerId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        customer.getRegistrationDate() != null ? 
                            customer.getRegistrationDate().toString() : "N/A"
                    };
                    tableModel.addRow(row);
                }
            }
        }
    }
    
    private void showAddEditDialog(Customer customer) {
        // Frame cast hatasını önlemek için
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parentWindow, 
            customer == null ? "Add New Customer" : "Edit Customer", 
            Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        // Eğer tablonuzda address alanı varsa bunu ekleyin
        // JTextField addressField = new JTextField(20);
        
        if (customer != null) {
            firstNameField.setText(customer.getFirstName());
            lastNameField.setText(customer.getLastName());
            emailField.setText(customer.getEmail());
            phoneField.setText(customer.getPhone() != null ? customer.getPhone() : "");
            // addressField.setText(customer.getAddress() != null ? customer.getAddress() : "");
        }
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("First Name*:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(firstNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Last Name*:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(lastNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email*:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(phoneField, gbc);
        
       
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                
                if (firstNameField.getText().trim().isEmpty() ||
                    lastNameField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "First Name, Last Name and Email are required!", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                String email = emailField.getText().trim();
                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter a valid email address!", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Customer newCustomer = new Customer();
                if (customer != null) {
                    newCustomer.setCustomerId(customer.getCustomerId());
                }
                newCustomer.setFirstName(firstNameField.getText().trim());
                newCustomer.setLastName(lastNameField.getText().trim());
                newCustomer.setEmail(emailField.getText().trim());
                newCustomer.setPhone(phoneField.getText().trim());
               
                
                boolean success;
                if (customer == null) {
                    success = customerService.addCustomer(newCustomer);
                } else {
                    success = customerService.updateCustomer(newCustomer);
                }
                
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Customer saved successfully!");
                    dialog.dispose();
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Error saving customer!\n" +
                        "Email might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setVisible(true);
    }
    
    private void editSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            
            int modelRow = customerTable.convertRowIndexToModel(selectedRow);
            int customerId = (int) tableModel.getValueAt(modelRow, 0);
            Customer customer = customerService.getCustomer(customerId);
            if (customer != null) {
                showAddEditDialog(customer);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit!",
                "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            
            int modelRow = customerTable.convertRowIndexToModel(selectedRow);
            int customerId = (int) tableModel.getValueAt(modelRow, 0);
            String customerName = tableModel.getValueAt(modelRow, 1) + " " + 
                                 tableModel.getValueAt(modelRow, 2);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete '" + customerName + "'?\n" +
                "Note: This will fail if customer has existing orders.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (customerService.deleteCustomer(customerId)) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting customer!\n" +
                        "Customer might have existing orders.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!",
                "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
