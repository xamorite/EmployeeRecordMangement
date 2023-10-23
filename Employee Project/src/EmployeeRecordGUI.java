import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
//Have a wonderful experience going through my code 😂😂
public class EmployeeRecordGUI {
    private final JFrame frame;
    private final EmployeeRecordManager recordManager;
    private JTable table;

    private JTable deleteTable;

    private JTextField searchFirstNameField;
    private JTextField searchLastNameField;
    private JTextField searchDepartmentField;

    private DefaultTableModel deleteTableModel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField basicSalaryField;
    private JTextField departmentField;
    private JTextField dateOfJoiningField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField phoneNumberField;
    private JCheckBox selectAllCheckbox;

    public EmployeeRecordGUI() {
        frame = new JFrame("Employee Records Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        recordManager = new EmployeeRecordManager();

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane, BorderLayout.CENTER);

        JPanel addEmployeePanel = createAddEmployeePanel();
        tabbedPane.addTab("Create New Employees", addEmployeePanel);

        JPanel viewEmployeePanel = createViewEmployeePanel();
        tabbedPane.addTab("View existing Employees", viewEmployeePanel);

        JPanel modifyEmployeePanel = createModifyEmployeePanel();
        tabbedPane.addTab("Modify Employees Record", modifyEmployeePanel);

        JPanel searchEmployeePanel = createSearchEmployeePanel();
        tabbedPane.addTab("Search Employees", searchEmployeePanel);

        JPanel countEmployeePanel = createCountEmployeePanel();
        tabbedPane.addTab("Total Employees", countEmployeePanel);

        JPanel deleteEmployeePanel = createDeleteEmployeePanel();
        tabbedPane.addTab("Delete Employees", deleteEmployeePanel);

        tabbedPane.addChangeListener(e -> {
            // When the "Delete Employees" tab is selected, update the table
            if (tabbedPane.getSelectedIndex() == 5) { // Assuming the "Delete Employees" tab is at index 5
                updateDeleteTable();
            }
        });


        tabbedPane.addChangeListener(e -> {
            // When the "Delete Employees" tab is selected, update the table
            if (tabbedPane.getSelectedIndex() == 1) { // Assuming the "Delete Employees" tab is at index 5
                updateDisplayTable();
            }
        });

        tabbedPane.addChangeListener(e -> {

            if (tabbedPane.getSelectedIndex() == 2) {

                clearDisplayTable();
            }
        });

        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeRecordGUI gui = new EmployeeRecordGUI();
            gui.show();
        });
    }

    private JPanel createAddEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        // Input fields for adding employees
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        ageField = new JTextField(5);
        basicSalaryField = new JTextField(10);
        departmentField = new JTextField(20);
        dateOfJoiningField = new JTextField(12);
        addressField = new JTextField(30);
        cityField = new JTextField(15);
        phoneNumberField = new JTextField(15);

        JLabel welcome = new JLabel("Create New record");
        Font monospacedFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
        welcome.setFont(monospacedFont);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(welcome, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("First Name"), c);
        c.gridx = 1;
        panel.add(firstNameField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Last Name"), c);
        c.gridx = 1;
        panel.add(lastNameField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Age"), c);
        c.gridx = 1;
        panel.add(ageField, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(new JLabel("Basic Salary"), c);
        c.gridx = 1;
        panel.add(basicSalaryField, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(new JLabel("Department"), c);
        c.gridx = 1;
        panel.add(departmentField, c);

        c.gridx = 0;
        c.gridy = 6;
        panel.add(new JLabel("Date of Joining (DD-MM-YYYY)"), c);
        c.gridx = 1;
        panel.add(dateOfJoiningField, c);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(new JLabel("Address"), c);
        c.gridx = 1;
        panel.add(addressField, c);

        c.gridx = 0;
        c.gridy = 8;
        panel.add(new JLabel("City"), c);
        c.gridx = 1;
        panel.add(cityField, c);

        c.gridx = 0;
        c.gridy = 9;
        panel.add(new JLabel("Phone Number"), c);
        c.gridx = 1;
        panel.add(phoneNumberField, c);

        // Add Employee button logic
        JButton addButton = new JButton("Add Employee");
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        panel.add(addButton, c);

        addButton.addActionListener(e -> {
            if (validateInput()) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int age = Integer.parseInt(ageField.getText());
                double basicSalary = Double.parseDouble(basicSalaryField.getText());
                String department = departmentField.getText();
                String dateOfJoining = dateOfJoiningField.getText();
                String address = addressField.getText();
                String city = cityField.getText();
                String phoneNumber = phoneNumberField.getText();

                Employee employee = new Employee(firstName, lastName, age, basicSalary, department);
                employee.setAddressCity(address, city);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    employee.setDateOfJoining(dateFormat.parse(dateOfJoining));
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format (DD-MM-YYYY).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                employee.setPhoneNumber(phoneNumber);

                recordManager.addEmployee(employee);
                updateDisplayArea();
                clearInputFields();
                JOptionPane.showMessageDialog(frame, "Employee added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

//    private JPanel createViewEmployeePanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        // Create the table with headings
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("Employee Number");
//        model.addColumn("First Name");
//        model.addColumn("Last Name");
//        model.addColumn("Age");
//        model.addColumn("Basic Salary");
//        model.addColumn("Department");
//        model.addColumn("Date of Joining");
//        model.addColumn("Address");
//        model.addColumn("City");
//        model.addColumn("Phone Number");
//
//        table = new JTable(model);
//        JScrollPane tableScrollPane = new JScrollPane(table);
//        panel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // Update the table with employee records
//        updateDisplayTable();
//
//        return panel;
//    }
private JPanel createViewEmployeePanel() {
    JPanel panel = new JPanel(new BorderLayout());

    // Create the table with headings
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Employee Number");
    model.addColumn("First Name");
    model.addColumn("Last Name");
    model.addColumn("Age");
    model.addColumn("Basic Salary");
    model.addColumn("Department");
    model.addColumn("Date of Joining");
    model.addColumn("Address");
    model.addColumn("City");
    model.addColumn("Phone Number");

    table = new JTable(model);
    table.setDefaultRenderer(Object.class, new CenterTableCellRenderer()); // Apply custom renderer
    JScrollPane tableScrollPane = new JScrollPane(table);
    panel.add(tableScrollPane, BorderLayout.CENTER);

    // Update the table with employee records
    updateDisplayTable();

    return panel;
}

//    private void updateDisplayTable() {
//        // Code to update the table with employee records
//    }

    // Custom cell renderer to center-align the data in each column
    private class CenterTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
        public CenterTableCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cellComponent;
        }
    }


    private void updateDisplayTable() {
        List<Employee> allEmployees = recordManager.getAllEmployees();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Clear the table
        model.setRowCount(0);

        // Populate the table with employee records
        for (Employee employee : allEmployees) {
            Object[] row = new Object[11];
            row[0] = employee.getEmployeeNumber();
            row[1] = employee.getFirstName();
            row[2] = employee.getLastName();
            row[3] = employee.getAge();
            row[4] = employee.getBasicSalary();
            row[5] = employee.getDepartment();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            row[6] = dateFormat.format(employee.getDateOfJoining());
            row[7] = employee.getAddress();
            row[8] = employee.getCity();
            row[9] = employee.getPhoneNumber();
            model.addRow(row);
        }
    }

    private JPanel createModifyEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel welcome = new JLabel("Modify Existing Record");
        Font monospacedFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
        welcome.setFont(monospacedFont);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.PAGE_START;
        panel.add(welcome, c);

        // Search bar for employee modification
        JTextField firstNameSearchField = new JTextField(20);
        JTextField lastNameSearchField = new JTextField(20);
        JTextField departmentSearchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Search by First Name"), c);
        c.gridx = 1;
        panel.add(firstNameSearchField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Search by Last Name"), c);
        c.gridx = 1;
        panel.add(lastNameSearchField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Search by Department"), c);
        c.gridx = 1;
        panel.add(departmentSearchField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        panel.add(searchButton, c);

        // Input fields for modifying employees
        JTextField modifiedFirstNameField = new JTextField(20);
        JTextField modifiedLastNameField = new JTextField(20);
        JTextField modifiedAgeField = new JTextField(5);
        JTextField modifiedBasicSalaryField = new JTextField(10);
        JTextField modifiedDepartmentField = new JTextField(20);
        JTextField modifiedDateOfJoiningField = new JTextField(12);
        JTextField modifiedAddressField = new JTextField(30);
        JTextField modifiedCityField = new JTextField(15);
        JTextField modifiedPhoneNumberField = new JTextField(15);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        panel.add(new JLabel("Modified First Name"), c);
        c.gridx = 1;
        panel.add(modifiedFirstNameField, c);

        c.gridx = 0;
        c.gridy = 6;
        panel.add(new JLabel("Modified Last Name"), c);
        c.gridx = 1;
        panel.add(modifiedLastNameField, c);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(new JLabel("Modified Age"), c);
        c.gridx = 1;
        panel.add(modifiedAgeField, c);

        c.gridx = 0;
        c.gridy = 8;
        panel.add(new JLabel("Modified Basic Salary"), c);
        c.gridx = 1;
        panel.add(modifiedBasicSalaryField, c);

        c.gridx = 0;
        c.gridy = 9;
        panel.add(new JLabel("Modified Department"), c);
        c.gridx = 1;
        panel.add(modifiedDepartmentField, c);

        c.gridx = 0;
        c.gridy = 10;
        panel.add(new JLabel("Modified Date of Joining (DD-MM-YYYY)"), c);
        c.gridx = 1;
        panel.add(modifiedDateOfJoiningField, c);

        c.gridx = 0;
        c.gridy = 11;
        panel.add(new JLabel("Modified Address"), c);
        c.gridx = 1;
        panel.add(modifiedAddressField, c);

        c.gridx = 0;
        c.gridy = 12;
        panel.add(new JLabel("Modified City"), c);
        c.gridx = 1;
        panel.add(modifiedCityField, c);

        c.gridx = 0;
        c.gridy = 13;
        panel.add(new JLabel("Modified Phone Number"), c);
        c.gridx = 1;
        panel.add(modifiedPhoneNumberField, c);

        // Modify Employee button logic
        JButton modifyButton = new JButton("Modify Employee");
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 2;
        panel.add(modifyButton, c);

        // Clear Fields button logic
        JButton clearModifyButton = new JButton("Clear Fields");
        c.gridx = 0;
        c.gridy = 15;
        panel.add(clearModifyButton, c);

        searchButton.addActionListener(e -> {
            // Implement code to search for an employee and populate input fields with their data

            String firstNameQuery = firstNameSearchField.getText();
            String lastNameQuery = lastNameSearchField.getText();
            String departmentQuery = departmentSearchField.getText();


            List<Employee> searchResults = new ArrayList<>();

            // Check if any combination of search fields have input
            if (!firstNameQuery.isEmpty() && !lastNameQuery.isEmpty() && !departmentQuery.isEmpty()) {
                // Search employees based on all three fields
                searchResults = recordManager.searchEmployees(firstNameQuery, lastNameQuery, departmentQuery);
            } else if (!firstNameQuery.isEmpty() && !lastNameQuery.isEmpty()) {
                // Search employees based on first name and last name
                searchResults = recordManager.searchEmployees(firstNameQuery, lastNameQuery, "");
            } else if (!firstNameQuery.isEmpty() && !departmentQuery.isEmpty()) {
                // Search employees based on first name and department
                searchResults = recordManager.searchEmployees(firstNameQuery, "", departmentQuery);
            } else if (!lastNameQuery.isEmpty() && !departmentQuery.isEmpty()) {
                // Search employees based on last name and department
                searchResults = recordManager.searchEmployees("", lastNameQuery, departmentQuery);
            } else if (!firstNameQuery.isEmpty()) {
                // Search employees based on first name
                searchResults = recordManager.searchEmployees(firstNameQuery, "", "");
            } else if (!lastNameQuery.isEmpty()) {
                // Search employees based on last name
                searchResults = recordManager.searchEmployees("", lastNameQuery, "");
            } else if (!departmentQuery.isEmpty()) {
                // Search employees based on department
                searchResults = recordManager.searchEmployees("", "", departmentQuery);
            }

            if (!searchResults.isEmpty()) {
                Employee employee = searchResults.get(0); // Get the first result from the list
                modifiedFirstNameField.setText(employee.getFirstName());
                modifiedLastNameField.setText(employee.getLastName());
                modifiedAgeField.setText(Integer.toString(employee.getAge()));
                modifiedBasicSalaryField.setText(Double.toString(employee.getBasicSalary()));
                modifiedDepartmentField.setText(employee.getDepartment());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                modifiedDateOfJoiningField.setText(dateFormat.format(employee.getDateOfJoining()));
                modifiedAddressField.setText(employee.getAddress());
                modifiedCityField.setText(employee.getCity());
                modifiedPhoneNumberField.setText(employee.getPhoneNumber());
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        modifyButton.addActionListener(e -> {
            // Implement code to modify the employee's record
            String modifiedFirstName = modifiedFirstNameField.getText();
            String modifiedLastName = modifiedLastNameField.getText();
            int modifiedAge = Integer.parseInt(modifiedAgeField.getText());
            double modifiedBasicSalary = Double.parseDouble(modifiedBasicSalaryField.getText());
            String modifiedDepartment = modifiedDepartmentField.getText();
            String modifiedDateOfJoining = modifiedDateOfJoiningField.getText();
            String modifiedAddress = modifiedAddressField.getText();
            String modifiedCity = modifiedCityField.getText();
            String modifiedPhoneNumber = modifiedPhoneNumberField.getText();

            Employee modifiedEmployee = new Employee(modifiedFirstName, modifiedLastName, modifiedAge, modifiedBasicSalary, modifiedDepartment);
            modifiedEmployee.setAddressCity(modifiedAddress, modifiedCity);
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                modifiedEmployee.setDateOfJoining(dateFormat.parse(modifiedDateOfJoining));
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date format (DD-MM-YYYY).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            modifiedEmployee.setPhoneNumber(modifiedPhoneNumber);

            boolean success = recordManager.modifyEmployee(firstNameSearchField.getText(), lastNameSearchField.getText(), departmentSearchField.getText(), modifiedEmployee);

            if (success) {
                updateDisplayArea();
                clearInputFields();
                JOptionPane.showMessageDialog(frame, "Employee modified successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Modification Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearModifyButton.addActionListener(e -> {
            // Implement code to clear the fields on the "Modify Employees" tab
            firstNameSearchField.setText("");
            lastNameSearchField.setText("");
            departmentSearchField.setText("");
            modifiedFirstNameField.setText("");
            modifiedLastNameField.setText("");
            modifiedAgeField.setText("");
            modifiedBasicSalaryField.setText("");
            modifiedDepartmentField.setText("");
            modifiedDateOfJoiningField.setText("");
            modifiedAddressField.setText("");
            modifiedCityField.setText("");
            modifiedPhoneNumberField.setText("");
        });

        return panel;
    }


//    private JPanel createSearchEmployeePanel() {
//        JPanel panel = new JPanel(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.insets = new Insets(3, 5, 2, 3);
//        JLabel welcome = new JLabel("Search Existing record");
//        Font monospacedFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
//        welcome.setFont(monospacedFont);
//        welcome.setBounds(2,0,2,2);
//        welcome.getBounds();
//        c.gridx = 2;
//        c.gridy = 0;
//        c.gridwidth = 3;
//        c.anchor = GridBagConstraints.PAGE_END;
//        panel.add(welcome,c);
//        // Initialize search fields for employee search
//        searchFirstNameField = new JTextField(20);
//        searchLastNameField = new JTextField(20);
//        searchDepartmentField = new JTextField(20);
//        JButton searchButton = new JButton("Search");
//
//        c.gridx = 0;
//        c.gridy = 1;
//        panel.add(new JLabel("Search by First Name"), c);
//        c.gridx = 1;
//        panel.add(searchFirstNameField, c);
//
//        c.gridx = 0;
//        c.gridy = 2;
//        panel.add(new JLabel("Search by Last Name"), c);
//        c.gridx = 1;
//        panel.add(searchLastNameField, c);
//
//        c.gridx = 0;
//        c.gridy = 3;
//        panel.add(new JLabel("Search by Department"), c);
//        c.gridx = 1;
//        panel.add(searchDepartmentField, c);
//
//        // Search button
//        c.gridx = 2; // Adjusted grid coordinates for the search button
//        c.gridy = 4;
//        c.gridheight = 3; // Span three rows
//        panel.add(searchButton, c);
//
//        // Search results
//        JTextArea searchResultsArea = new JTextArea(15, 60);
//        searchResultsArea.setEditable(false);
//        JScrollPane searchResultsScrollPane = new JScrollPane(searchResultsArea);
//
//        c.gridx = 0;
//        c.gridy = 3;
//        c.gridwidth = 3; // Span three columns
//        panel.add(searchResultsScrollPane, c);
//
//        JButton clearSearchButton = new JButton("Clear Fields");
//        c.gridx = 2;
//        c.gridy = 2;
//        c.gridwidth = 3;
//        panel.add(clearSearchButton, c);
//
//        searchButton.addActionListener(e -> {
//            // Implement code to search for employees and display search results
//            String firstNameQuery = searchFirstNameField.getText();
//            String lastNameQuery = searchLastNameField.getText();
//            String departmentQuery = searchDepartmentField.getText();
//
//            List<Employee> searchResults = recordManager.searchEmployees(firstNameQuery, lastNameQuery, departmentQuery);
//            if (searchResults.isEmpty()) {
//                searchResultsArea.setText("No matching employees found.");
//            } else {
//                StringBuilder resultText = new StringBuilder();
//                for (Employee employee : searchResults) {
//                    resultText.append(employee.toString()).append("\n\n");
//                }
//                searchResultsArea.setText(resultText.toString());
//            }
//        });
//
//
//        clearSearchButton.addActionListener(e -> {
//            // Implement code to clear the fields on the "Search Employees" tab
//            searchFirstNameField.setText("");
//            searchLastNameField.setText("");
//            searchDepartmentField.setText("");
//            searchResultsArea.setText("");
//        });
//        return panel;
//    }
private JPanel createSearchEmployeePanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(3, 5, 2, 3);

    JLabel welcome = new JLabel("Search Existing record");
    Font monospacedFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
    welcome.setFont(monospacedFont);

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.PAGE_START;
    panel.add(welcome, c);

    // Initialize search fields for employee search
    searchFirstNameField = new JTextField(20);
    searchLastNameField = new JTextField(20);
    searchDepartmentField = new JTextField(20);
    JButton searchButton = new JButton("Search");

    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    panel.add(new JLabel("Search by First Name"), c);
    c.gridx = 1;
    panel.add(searchFirstNameField, c);

    c.gridx = 0;
    c.gridy = 2;
    panel.add(new JLabel("Search by Last Name"), c);
    c.gridx = 1;
    panel.add(searchLastNameField, c);

    c.gridx = 0;
    c.gridy = 3;
    panel.add(new JLabel("Search by Department"), c);
    c.gridx = 1;
    panel.add(searchDepartmentField, c);

    // Search button
    c.gridx = 2;
    c.gridy = 1;
    c.gridheight = 3;
    c.anchor = GridBagConstraints.PAGE_START;
    panel.add(searchButton, c);

    // Search results
    JTextArea searchResultsArea = new JTextArea(15, 60);
    searchResultsArea.setEditable(false);
    JScrollPane searchResultsScrollPane = new JScrollPane(searchResultsArea);

    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 3;
    c.gridheight = 1;
    panel.add(searchResultsScrollPane, c);

    JButton clearSearchButton = new JButton("Clear Fields");
    c.gridx = 2;
    c.gridy = 5;
    c.gridwidth = 1;
    panel.add(clearSearchButton, c);

    searchButton.addActionListener(e -> {
        // Implement code to search for employees and display search results
        String firstNameQuery = searchFirstNameField.getText();
        String lastNameQuery = searchLastNameField.getText();
        String departmentQuery = searchDepartmentField.getText();

        List<Employee> searchResults = recordManager.searchEmployees(firstNameQuery, lastNameQuery, departmentQuery);
        if (searchResults.isEmpty()) {
            searchResultsArea.setText("No matching employees found.");
        } else {
            StringBuilder resultText = new StringBuilder();
            for (Employee employee : searchResults) {
                resultText.append(employee.toString()).append("\n\n");
            }
            searchResultsArea.setText(resultText.toString());
        }
    });

    clearSearchButton.addActionListener(e -> {
        // Implement code to clear the fields on the "Search Employees" tab
        searchFirstNameField.setText("");
        searchLastNameField.setText("");
        searchDepartmentField.setText("");
        searchResultsArea.setText("");
    });

    return panel;
}


    private JPanel createCountEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3, 5, 5, 3);
        JLabel welcome = new JLabel("Total Employee record");
        Font monospacedFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
        welcome.setFont(monospacedFont);
        welcome.setBounds(2,0,2,2);
        welcome.getBounds();
        c.gridx = 0;
        c.gridy = 0;
//        c.gridwidth = 2;
        c.anchor = GridBagConstraints.PAGE_END;
        panel.add(welcome,c);

        // Count Employee button logic
        JButton countButton = new JButton("Count Employees");

        c.gridx = 0;
        c.gridy = 3;
        panel.add(countButton, c);

        JTextArea countResultArea = new JTextArea(1, 20);
        countResultArea.setEditable(false);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(countResultArea, c);

        countButton.addActionListener(e -> {
            // Implement code to count all employees
            int employeeCount = recordManager.countEmployees();
            countResultArea.setText("Total Employees: " + employeeCount);
        });

        return panel;
    }

    private JPanel createDeleteEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        ;
        // Create the Delete Employees table with headings
        deleteTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class; // Use checkboxes for the first column
                }
                return Object.class; // Other columns
            }
        };
        deleteTableModel.addColumn("Select");
        deleteTableModel.addColumn("Employee Number");
        deleteTableModel.addColumn("First Name");
        deleteTableModel.addColumn("Last Name");
        deleteTableModel.addColumn("Age");
        deleteTableModel.addColumn("Basic Salary");
        deleteTableModel.addColumn("Department");
        deleteTableModel.addColumn("Date of Joining");
        deleteTableModel.addColumn("Address");
        deleteTableModel.addColumn("City");
        deleteTableModel.addColumn("Phone Number");

        deleteTable = new JTable(deleteTableModel);
        JScrollPane deleteTableScrollPane = new JScrollPane(deleteTable);

        panel.add(deleteTableScrollPane, BorderLayout.CENTER);

        // Create the "Delete Selected" button
        JButton deleteButton = new JButton("Delete Selected 😒");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);


        // Add checkboxes in the column header to select/deselect all
        selectAllCheckbox = new JCheckBox();
        selectAllCheckbox.addActionListener(e -> selectAllEmployees(selectAllCheckbox.isSelected()));

        JTableHeader tableHeader = deleteTable.getTableHeader();
        tableHeader.getColumnModel().getColumn(0).setHeaderRenderer(new CheckboxHeader(selectAllCheckbox));
        tableHeader.addMouseListener(new CheckboxHeaderHandler(selectAllCheckbox));

        deleteButton.addActionListener(e -> deleteSelectedEmployees());

        return panel;
    }

    // Update the "Delete Employees" table with employee records
    private void updateDeleteTable() {
        if (deleteTableModel.getRowCount() > 0) {
            deleteTableModel.setRowCount(0);
        }

        List<Employee> allEmployees = recordManager.getAllEmployees();
        for (Employee employee : allEmployees) {
            Object[] row = new Object[11];
            row[0] = false;
            row[1] = employee.getEmployeeNumber();
            row[2] = employee.getFirstName();
            row[3] = employee.getLastName();
            row[4] = employee.getAge();
            row[5] = employee.getBasicSalary();
            row[6] = employee.getDepartment();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            row[7] = dateFormat.format(employee.getDateOfJoining());
            row[8] = employee.getAddress();
            row[9] = employee.getCity();
            row[10] = employee.getPhoneNumber();
            deleteTableModel.addRow(row);
        }
    }

    // Delete selected employees
    private void deleteSelectedEmployees() {

        DefaultTableModel model = (DefaultTableModel) deleteTable.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            Boolean selected = (Boolean) model.getValueAt(i, 0);
            if (selected) {
                int employeeNumber = (int) model.getValueAt(i, 1);
                recordManager.deleteEmployeeByNumber(employeeNumber);
            }
        }
        updateDeleteTable();
    }

    // Select or deselect all employees
    private void selectAllEmployees(boolean selectAll) {
        DefaultTableModel model = (DefaultTableModel) deleteTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(selectAll, i, 0);
        }
    }

    private void updateDisplayArea() {
        List<Employee> allEmployees = recordManager.getAllEmployees();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Employee employee : allEmployees) {
            Object[] row = new Object[10]; // 10 columns based on the table structure
            row[0] = employee.getEmployeeNumber();
            row[1] = employee.getFirstName();
            row[2] = employee.getLastName();
            row[3] = employee.getAge();
            row[4] = employee.getBasicSalary();
            row[5] = employee.getDepartment();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            row[6] = dateFormat.format(employee.getDateOfJoining());
            row[7] = employee.getAddress();
            row[8] = employee.getCity();
            row[9] = employee.getPhoneNumber();
            model.addRow(row);
        }
    }

    private boolean validateInput() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageText = ageField.getText();
        String basicSalaryText = basicSalaryField.getText();
        String department = departmentField.getText();
        String dateOfJoining = dateOfJoiningField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || ageText.isEmpty() || basicSalaryText.isEmpty() || department.isEmpty() || dateOfJoining.isEmpty() || address.isEmpty() || city.isEmpty() || phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int age = Integer.parseInt(ageText);
            if (age < 0 || age > 150) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid age. Please enter a valid age (0-150).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double basicSalary = Double.parseDouble(basicSalaryText);
            if (basicSalary < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid basic salary. Please enter a valid positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateOfJoining);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date format (DD-MM-YYYY).", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Invalid phone number. Please enter a 10-digit number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearInputFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        basicSalaryField.setText("");
        departmentField.setText("");
        dateOfJoiningField.setText("");
        addressField.setText("");
        cityField.setText("");
        phoneNumberField.setText("");
    }

    private void clearDisplayTable() {
        List<Employee> allEmployees = recordManager.getAllEmployees();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Employee employee : allEmployees) {
            Object[] row = new Object[10]; // 10 columns based on the table structure
            row[0] = "";
            row[1] = "";
            row[2] = "";
            row[3] = "";
            row[4] = "";
            row[5] = "";
            row[6] = "";
            row[7] = "";
            row[8] = "";
            row[9] = "";
            model.addRow(row);
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    // CheckboxHeader class to render the checkbox in the column header
    static class CheckboxHeader extends JCheckBox implements TableCellRenderer {
        CheckboxHeader(JCheckBox checkBox) {
            this.setOpaque(true);
            this.setHorizontalAlignment(SwingConstants.CENTER);
            this.setSelected(checkBox.isSelected());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // CheckboxHeaderHandler class to handle clicks on the column header checkbox
    class CheckboxHeaderHandler extends MouseAdapter {
        final JCheckBox checkbox;

        CheckboxHeaderHandler(JCheckBox checkbox) {
            this.checkbox = checkbox;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (checkbox.isEnabled()) {
                JTable table = ((JTableHeader) e.getSource()).getTable();
                TableColumnModel columnModel = table.getColumnModel();
                int vci = columnModel.getColumnIndexAtX(e.getX());
                int mci = table.convertColumnIndexToModel(vci);
                if (mci == 0) {
                    checkbox.setSelected(!checkbox.isSelected());
                    selectAllEmployees(checkbox.isSelected());
                    ((JTableHeader) e.getSource()).repaint();
                }
            }
        }
    }

}
