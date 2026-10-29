//public class InventoryManagement {
//}



import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/retail_db";
        String user = "root";
        String password = "admin";

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(
                    url, user, password
            );

            System.out.println("MySQL Connected Successfully!");

            int choice;

            do {
                System.out.println("\n--- INVENTORY MANAGEMENT ---");
                System.out.println("1. Insert 3 Products");
                System.out.println("2. Search by Category");
                System.out.println("3. Add Stock");
                System.out.println("4. Update Product Price");
                System.out.println("5. Inventory Value and Delete");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1: {
                        String sql =
                                "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";

                        try (PreparedStatement ps =
                                     con.prepareStatement(sql)) {

                            for (int i = 1; i <= 3; i++) {
                                System.out.println("Product " + i);

                                System.out.print("ID: ");
                                int id = sc.nextInt();
                                sc.nextLine();

                                System.out.print("Name: ");
                                String name = sc.nextLine();

                                System.out.print("Category: ");
                                String category = sc.nextLine();

                                System.out.print("Quantity: ");
                                int qty = sc.nextInt();

                                System.out.print("Price: ");
                                double price = sc.nextDouble();
                                sc.nextLine();

                                ps.setInt(1, id);
                                ps.setString(2, name);
                                ps.setString(3, category);
                                ps.setInt(4, qty);
                                ps.setDouble(5, price);

                                try {
                                    ps.executeUpdate();
                                    System.out.println("Product inserted!");
                                } catch (SQLIntegrityConstraintViolationException e) {
                                    System.out.println("Duplicate product ID!");
                                }
                            }
                        }
                        break;
                    }

                    case 2: {
                        System.out.print("Enter category: ");
                        String category = sc.nextLine();

                        String sql =
                                "SELECT * FROM Product " +
                                        "WHERE category = ? AND quantity < 20 " +
                                        "ORDER BY price DESC";

                        try (PreparedStatement ps =
                                     con.prepareStatement(sql)) {
                            ps.setString(1, category);

                            try (ResultSet rs = ps.executeQuery()) {
                                while (rs.next()) {
                                    System.out.println(
                                            rs.getInt("product_id") + " " +
                                                    rs.getString("product_name") + " " +
                                                    rs.getString("category") + " " +
                                                    rs.getInt("quantity") + " " +
                                                    rs.getDouble("price")
                                    );
                                }
                            }
                        }
                        break;
                    }

                    case 3: {
                        System.out.print("Enter product ID: ");
                        int id = sc.nextInt();

                        System.out.print("Quantity to add: ");
                        int qty = sc.nextInt();
                        sc.nextLine();

                        if (qty <= 0) {
                            System.out.println("Enter a positive quantity.");
                            break;
                        }

                        String sql =
                                "UPDATE Product SET quantity = quantity + ? " +
                                        "WHERE product_id = ?";

                        try (PreparedStatement ps =
                                     con.prepareStatement(sql)) {
                            ps.setInt(1, qty);
                            ps.setInt(2, id);

                            if (ps.executeUpdate() > 0) {
                                System.out.println("Stock updated!");
                            } else {
                                System.out.println("Product not found.");
                            }
                        }
                        break;
                    }

                    case 4: {
                        System.out.print("Enter product ID: ");
                        int id = sc.nextInt();

                        System.out.print("Enter new price: ");
                        double price = sc.nextDouble();
                        sc.nextLine();

                        if (price <= 0) {
                            System.out.println("Price must be greater than zero.");
                            break;
                        }

                        String find =
                                "SELECT * FROM Product WHERE product_id = ?";

                        try (PreparedStatement ps =
                                     con.prepareStatement(find)) {
                            ps.setInt(1, id);

                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    String update =
                                            "UPDATE Product SET price = ? " +
                                                    "WHERE product_id = ?";

                                    try (PreparedStatement ups =
                                                 con.prepareStatement(update)) {
                                        ups.setDouble(1, price);
                                        ups.setInt(2, id);
                                        ups.executeUpdate();
                                    }

                                    System.out.println("Updated product:");
                                    System.out.println("ID: " + id);
                                    System.out.println(
                                            "Name: " + rs.getString("product_name")
                                    );
                                    System.out.println(
                                            "Category: " + rs.getString("category")
                                    );
                                    System.out.println(
                                            "Quantity: " + rs.getInt("quantity")
                                    );
                                    System.out.println("New price: " + price);
                                } else {
                                    System.out.println("Product not found.");
                                }
                            }
                        }
                        break;
                    }

                    case 5: {
                        String total =
                                "SELECT SUM(quantity * price) AS total_value " +
                                        "FROM Product";

                        try (Statement st = con.createStatement();
                             ResultSet rs = st.executeQuery(total)) {
                            if (rs.next()) {
                                System.out.println(
                                        "Total inventory value: " +
                                                rs.getDouble("total_value")
                                );
                            }
                        }

                        System.out.print("Enter product ID to delete: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        String sql =
                                "DELETE FROM Product " +
                                        "WHERE product_id = ? AND quantity = 0";

                        try (PreparedStatement ps =
                                     con.prepareStatement(sql)) {
                            ps.setInt(1, id);

                            if (ps.executeUpdate() > 0) {
                                System.out.println("Product deleted!");
                            } else {
                                System.out.println(
                                        "Cannot delete: product not found " +
                                                "or quantity is not zero."
                                );
                            }
                        }
                        break;
                    }

                    case 6:
                        System.out.println("Program ended.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }

            } while (choice != 6);

            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
