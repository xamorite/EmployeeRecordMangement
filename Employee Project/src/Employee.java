import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee implements Serializable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static int nextEmployeeNumber = 1;
    private int employeeNumber;
    private String firstName;
    private String lastName;
    private int age;
    private double basicSalary;
    private String department;
    private Date dateOfJoining;
    private String address;
    private String city;
    private String phoneNumber;

    public Employee(String firstName, String lastName, int age, double basicSalary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.basicSalary = basicSalary;
        this.department = department;
        this.employeeNumber = nextEmployeeNumber++;
    }

    public static Employee deserialize(String data) {
        String[] parts = data.split(";");
        if (parts.length == 10) {
            try {
                int employeeNumber = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                int age = Integer.parseInt(parts[3]);
                double basicSalary = Double.parseDouble(parts[4]);
                String department = parts[5];
                Date dateOfJoining = DATE_FORMAT.parse(parts[6]);
                String address = parts[7];
                String city = parts[8];
                String phoneNumber = parts[9];

                Employee employee = new Employee(firstName, lastName, age, basicSalary, department);
                employee.employeeNumber = employeeNumber;
                employee.setDateOfJoining(dateOfJoining);
                employee.setAddressCity(address, city);
                employee.setPhoneNumber(phoneNumber);

                return employee;
            } catch (NumberFormatException | ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddressCity(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public String serialize() {
        return employeeNumber + ";" + firstName + ";" + lastName + ";" + age + ";" + basicSalary + ";" +
                department + ";" + DATE_FORMAT.format(dateOfJoining) + ";" + address + ";" + city + ";" + phoneNumber;
    }

    @Override
    public String toString() {
        return "Employee Number: " + employeeNumber +
                "\nFirst Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nAge: " + age +
                "\nBasic Salary: " + basicSalary +
                "\nDepartment: " + department +
                "\nDate of Joining: " + DATE_FORMAT.format(dateOfJoining) +
                "\nAddress: " + address +
                "\nCity: " + city +
                "\nPhone Number: " + phoneNumber;
    }
}
