package com.employee;

import java.sql.*;
import java.util.Scanner;

public class EmployeeCRUD {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Insert Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Employees with Salary > 50000");
            System.out.println("6. Average Salary");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            try {
                Connection con = DBConnection.getConnection();

                switch (choice) {

                    case 1:
                        System.out.print("Emp ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Salary: ");
                        double salary = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Designation: ");
                        String desig = sc.nextLine();

                        PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO employee VALUES (?, ?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setDouble(3, salary);
                        ps.setString(4, desig);
                        ps.executeUpdate();

                        System.out.println("Employee Inserted");
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM employee");

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("emp_id") + " | " +
                                    rs.getString("emp_name") + " | " +
                                    rs.getDouble("salary") + " | " +
                                    rs.getString("designation")
                            );
                        }
                        break;

                    case 3:
                        System.out.print("Emp ID: ");
                        int uid = sc.nextInt();

                        System.out.print("New Salary: ");
                        double ns = sc.nextDouble();

                        PreparedStatement ps2 = con.prepareStatement(
                                "UPDATE employee SET salary=? WHERE emp_id=?");
                        ps2.setDouble(1, ns);
                        ps2.setInt(2, uid);
                        ps2.executeUpdate();

                        System.out.println("Salary Updated");
                        break;

                    case 4:
                        System.out.print("Emp ID: ");
                        int did = sc.nextInt();

                        PreparedStatement ps3 = con.prepareStatement(
                                "DELETE FROM employee WHERE emp_id=?");
                        ps3.setInt(1, did);
                        ps3.executeUpdate();

                        System.out.println("Employee Deleted");
                        break;

                    case 5:
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(
                                "SELECT * FROM employee WHERE salary > 50000");

                        while (rs2.next()) {
                            System.out.println(
                                    rs2.getInt(1) + " | " +
                                    rs2.getString(2) + " | " +
                                    rs2.getDouble(3) + " | " +
                                    rs2.getString(4)
                            );
                        }
                        break;

                    case 6:
                        Statement st3 = con.createStatement();
                        ResultSet rs3 = st3.executeQuery(
                                "SELECT AVG(salary) FROM employee");

                        if (rs3.next()) {
                            System.out.println("Average Salary: " + rs3.getDouble(1));
                        }
                        break;

                    case 7:
                        System.exit(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
