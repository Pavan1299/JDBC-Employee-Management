package com.jdbc;

import java.sql.*;
import java.util.Scanner;

public class EmployeeOperation {
        // Step 1: Create Connection Method
        public static Connection getConnection() throws Exception {
                Class.forName("org.h2.Driver");
                return DriverManager.getConnection("jdbc:h2:mem:testbd", // URL
                                "sa", // Username
                                "" // Password
                );
        }
        // Step 2: Create Table Method
        public static void createTable(Connection con) throws Exception {
                Statement st = con.createStatement();
                st.executeUpdate("CREATE TABLE EMPLOYEE(EMPLOYEE_ID VARCHAR(20) PRIMARY KEY,EMPLOYEE_NAME VARCHAR(50))");
                System.out.println("Table created successfully!");
        }
        // Step 3: Add Employee Method
        public static void addEmployee(Connection con, Scanner sc) throws Exception {
                System.out.println("Enter Employee ID:");
                String id = sc.next();
                sc.nextLine();
                System.out.println("Enter Employee Name:");
                String name = sc.nextLine();
                PreparedStatement ps = con.prepareStatement("INSERT INTO EMPLOYEE VALUES(?,?)");
                ps.setString(1, id);
                ps.setString(2, name);
                int result = ps.executeUpdate();
                System.out.println(result >= 1 ? "Employee added!" : "Failed to add!");
        }
        // Step 4: Show Employees Method
        public static void showEmployees(Connection con) throws Exception {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM EMPLOYEE");
                System.out.println("ID | NAME");
                System.out.println("-----------");
                boolean found = false;
                while (rs.next()) {
                        found = true;
                        System.out.println(rs.getString("EMPLOYEE_ID") + "|" + rs.getString("EMPLOYEE_NAME"));
                }
                if (!found) {
                        System.out.println("No records found!");
                }
        }
        // Step 5: Remove Employee Method
        public static void removeEmployee(Connection con, Scanner sc) throws Exception {
                System.out.println("Enter Employee ID to remove:");
                String id = sc.next();
                PreparedStatement ps = con.prepareStatement("DELETE EMPLOYEE WHERE EMPLOYEE_ID=?");
                ps.setString(1, id);
                int result = ps.executeUpdate();
                System.out.println(result >= 1? "Employee removed!": "No record found!");
        }
        // Step 6: Update Employee Method
        public static void updateEmployee(Connection con, Scanner sc) throws Exception {
                System.out.println("Enter Employee ID to update:");
                String id = sc.next();
                sc.nextLine();
                System.out.println("Enter New Employee Name:");
                String name = sc.nextLine();
                PreparedStatement ps = con.prepareStatement("UPDATE EMPLOYEE SET EMPLOYEE_NAME=? WHERE EMPLOYEE_ID=?");
                ps.setString(1, name);
                ps.setString(2, id);
                int result = ps.executeUpdate();
                System.out.println(result >= 1? "Employee updated!": "No record found!");
        }
        // Step 7: Main Method
        public static void main(String[] args) {
                try {
                        Connection con = getConnection();
                        createTable(con);
                        Scanner sc = new Scanner(System.in);
                        while (true) {
                                System.out.println("--- Employee App ---");
                                System.out.println("1. Add Employee");
                                System.out.println("2. Show Employees");
                                System.out.println("3. Remove Employee");
                                System.out.println("4. Update Employee");
                                System.out.println("5. Exit");
                                int choice = sc.nextInt();
                                switch (choice) {
                                        case 1:
                                                addEmployee(con, sc);
                                                break;
                                        case 2:
                                                showEmployees(con);
                                                break;
                                        case 3:
                                                removeEmployee(con, sc);
                                                break;
                                        case 4:
                                                updateEmployee(con, sc);
                                                break;
                                        case 5:
                                                System.out.println("Thanks for using the APPLICATION!");
                                                con.close();
                                                return;
                                        default:
                                                System.out.println("Invalid choice!");
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}