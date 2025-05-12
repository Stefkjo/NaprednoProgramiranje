package Kolokvium_II_Ispit;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

abstract class Employee {
    String ID;
    String level;
    double salary;

    public Employee(String ID, String level) {
        this.ID = ID;
        this.level = level;
    }

    public String getID() {
        return ID;
    }

    public String getLevel() {
        return level;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f", ID, level, salary);
    }

    abstract public void setSalary(double salary);
}

class FreelanceEmployee extends Employee {
    private List<Integer> ticketPoints;

    public FreelanceEmployee(String ID, String level, List<Integer> ticketPoints) {
        super(ID, level);
        this.ticketPoints = ticketPoints;
    }

    public int getTotalTicketPoints() {
        return ticketPoints.stream().mapToInt(i -> i).sum();
    }

    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Ticket points: %d\n",
                ID, level, salary, ticketPoints.size(), ticketPoints.stream().mapToInt(i -> i).sum());
    }

}

class HourlyEmployee extends Employee {
    private double hoursWorked;

    public HourlyEmployee(String ID, String level, double hoursWorked) {
        super(ID, level);
        this.hoursWorked = hoursWorked;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        if (hoursWorked > 40.00) {
            return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f\n",
                    ID, level, salary, 40.00, hoursWorked - 40.00);
        } else {
            return String.format("Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f\n",
                    ID, level, salary, hoursWorked, 0.00);
        }
    }
}

class PayrollSystem {
    private List<Employee> employees;
    private Map<String, Double> hoursRateByLevel;
    private Map<String, Double> ticketRateByLevel;

    public PayrollSystem(Map<String, Double> hourlyRateByLevel, Map<String, Double> ticketRateByLevel) {
        this.hoursRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
        employees = new ArrayList<>();
    }

    public void readEmployees(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .forEach(line -> {
                    String[] parts = line.split(";");
                    String id = parts[1];
                    String level = parts[2];
                    if (parts[0].equals("H")) {
                        double hoursWorked = Double.parseDouble(parts[3]);
                        Employee e = new HourlyEmployee(id, level, hoursWorked);
                    }
                    if (parts[0].equals("F")) {
                        List<Integer> tickets = new ArrayList<>();
                        for (int i = 3; i < parts.length; i++) {
                            tickets.add(Integer.parseInt(parts[i]));
                        }
                        Employee e = new FreelanceEmployee(id, level, tickets);
                    }
                });
    }

    public void setSalaryToEmployees() {
        employees.forEach(employee -> {
            double salary;
            if (employee instanceof HourlyEmployee) {
                double hourRate = hoursRateByLevel.get(employee.getLevel());
                double overtime = hoursRateByLevel.get(employee.getLevel()) * 1.5;
                if (((HourlyEmployee) employee).getHoursWorked() > 40.00) {
                    salary = 40.00 * hourRate + (((HourlyEmployee) employee).getHoursWorked() - 40.00) * overtime;
                } else {
                    salary = ((HourlyEmployee) employee).getHoursWorked() * hourRate;
                }
                employee.setSalary(salary);
            }
            if (employee instanceof FreelanceEmployee) {
                double ticketValue = ticketRateByLevel.get(employee.getLevel());
                salary = ticketValue * ((FreelanceEmployee) employee).getTotalTicketPoints();
                employee.setSalary(salary);
            }
        });
    }

    public Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
        setSalaryToEmployees();

        Map<String, Set<Employee>> grouped = employees.stream()
                .filter(employee -> levels.contains(employee.getLevel()))
                .collect(Collectors.groupingBy(
                        Employee::getLevel,
                        TreeMap::new,
                        Collectors.toCollection(LinkedHashSet::new)
                ));

        PrintWriter pw = new PrintWriter(os);
        grouped.forEach((level, employees) -> {
            pw.write("LEVEL: " + level + "\n");
            pw.write("Employees: \n");
            employees.forEach(pw::print);
        });

        return grouped;
    }
}

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i = 5; i <= 10; i++) {
            levels.add("level" + i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: " + level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
        });


    }
}