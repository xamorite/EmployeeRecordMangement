import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRecordManager {

    private final List<Employee> employees;
    private final String dataFilePath = "employees.txt"; // Path to the data file

    public EmployeeRecordManager() {
        employees = new ArrayList<>();
        loadEmployeesFromFile(); // Load employees from the file when the manager is initialized
    }

    public void addEmployee(Employee employee) {
        if (isEmployeeNumberUnique(employee.getEmployeeNumber())) {
            employees.add(employee);
            saveEmployeesToFile(); // Save employees to the file after adding
        }
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee searchEmployee(String query) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equalsIgnoreCase(query) ||
                    employee.getLastName().equalsIgnoreCase(query) ||
                    employee.getDepartment().equalsIgnoreCase(query)) {
                return employee;
            }

            try {
                int employeeNumber = Integer.parseInt(query);
                if (employee.getEmployeeNumber() == employeeNumber) {
                    return employee;
                }
            } catch (NumberFormatException e) {
                // Ignore the exception for non-numeric queries
            }
        }
        return null;
    }

    // Method for Searching by First Name
    public Employee searchEmployeeByFirstName(String firstNameQuery) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equalsIgnoreCase(firstNameQuery)) {
                return employee;
            }
        }
        return null;
    }

    // Method for searching by Last Name
    public Employee searchEmployeeByLastName(String lastNameQuery) {
        for (Employee employee : employees) {
            if (employee.getLastName().equalsIgnoreCase(lastNameQuery)) {
                return employee;
            }
        }
        return null;
    }


    // Method for searching by Department
    public Employee searchEmployeeByDepartment(String departmentQuery) {
        for (Employee employee : employees) {
            if (employee.getDepartment().equalsIgnoreCase(departmentQuery)) {
                return employee;
            }
        }
        return null;
    }


    public List<Employee> searchEmployees(String firstName, String lastName, String department) {
        List<Employee> searchResults = new ArrayList<>();
        for (Employee employee : employees) {
            if ((firstName.isEmpty() || employee.getFirstName().equalsIgnoreCase(firstName))
                    && (lastName.isEmpty() || employee.getLastName().equalsIgnoreCase(lastName))
                    && (department.isEmpty() || employee.getDepartment().equalsIgnoreCase(department))) {
                searchResults.add(employee);
            }
        }
        return searchResults;
    }

    public int countEmployees() {
        return employees.size();
    }

    public boolean modifyEmployee(String firstNameQuery, String lastNameQuery, String departmentQuery, Employee modifiedEmployee) {

        Employee employee = searchEmployeeByFirstName(firstNameQuery);

        if (employee == null) {
            employee = searchEmployeeByLastName(lastNameQuery);
        }

        if (employee == null) {
            employee = searchEmployeeByDepartment(departmentQuery);
        }

        if (employee != null) {
            employee.setFirstName(modifiedEmployee.getFirstName());
            employee.setLastName(modifiedEmployee.getLastName());
            employee.setAge(modifiedEmployee.getAge());
            employee.setBasicSalary(modifiedEmployee.getBasicSalary());
            employee.setDepartment(modifiedEmployee.getDepartment());
            employee.setDateOfJoining(modifiedEmployee.getDateOfJoining());
            employee.setAddressCity(modifiedEmployee.getAddress(), modifiedEmployee.getCity());
            employee.setPhoneNumber(modifiedEmployee.getPhoneNumber());
            saveEmployeesToFile(); // Save employees to the file after modification
            return true;
        }
        return false;
    }

    public void deleteEmployeeByNumber(int employeeNumber) {
        Employee employee = searchEmployee(String.valueOf(employeeNumber));
        if (employee != null) {
            employees.remove(employee);
            saveEmployeesToFile(); // Save employees to the file after deletion
        }
    }

    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employee employee = Employee.deserialize(line);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        } catch (FileNotFoundException e) {
            // The file does not exist, so no data is loaded (initial run).
            System.out.println("an error occurred and couldn't load file try again");
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Employee employee : employees) {
                writer.write(employee.serialize());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmployeeNumberUnique(int employeeNumber) {
        for (Employee employee : employees) {
            if (employee.getEmployeeNumber() == employeeNumber) {
                return false; // Employee number is not unique
            }
        }
        return true; // Employee number is unique
    }
}
