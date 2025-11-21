package com.example.visitorlogging;

import com.example.visitorlogging.dao.VisitorDAO;
import com.example.visitorlogging.entity.Visitor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static SessionFactory sessionFactory;
    private static VisitorDAO visitorDAO;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            visitorDAO = new VisitorDAO(sessionFactory);
            runMenu();
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
            scanner.close();
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\nVisitor Logging System");
            System.out.println("1. Add Visitor");
            System.out.println("2. List All Visitors");
            System.out.println("3. Exit");
            System.out.print("Choose an option: " );
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addVisitor();
                    break;
                case "2":
                    listVisitors();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addVisitor() {
        try {
            System.out.print("Enter name: " );
            String name = scanner.nextLine().trim();
            System.out.print("Enter email: " );
            String email = scanner.nextLine().trim();
            System.out.print("Enter visit date (YYYY-MM-DD): " );
            String visitDateStr = scanner.nextLine().trim();
            LocalDate visitDate = LocalDate.parse(visitDateStr);
            System.out.print("Enter purpose: " );
            String purpose = scanner.nextLine().trim();

            Visitor visitor = new Visitor();
            visitor.setName(name);
            visitor.setEmail(email);
            visitor.setVisitDate(visitDate);
            visitor.setPurpose(purpose);

            visitorDAO.addVisitor(visitor);
            System.out.println("Visitor added successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Error adding visitor: " + e.getMessage());
        }
    }

    private static void listVisitors() {
        List<Visitor> visitors = visitorDAO.getAllVisitors();
        if (visitors.isEmpty()) {
            System.out.println("No visitors found.");
        } else {
            System.out.println("Visitors:");
            visitors.forEach(System.out::println);
        }
    }
}
