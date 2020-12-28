import java.util.Scanner;
import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        String connectionUsername = System.getenv("MYSQL_USERNAME");
        if (connectionUsername == null) {
            connectionUsername = "maija";
        }
        String connectionPassword = System.getenv("MYSQL_PASSWORD");
        if (connectionPassword == null) {
            connectionPassword = "vija";
        }
        String connectionString = "jdbc:mysql://localhost/ticketsystem?user=" + connectionUsername + "&password=" + connectionPassword;
        Connection conn = DriverManager.getConnection(connectionString);
        Statement statement = conn.createStatement();

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Please choose from options below:");
            System.out.println();
            System.out.println("1 - Create new ticket");
            System.out.println("2 - Show all tickets");
            System.out.println("3 - Change ticket status");
            System.out.println("4 - Weekly SLA report");
            System.out.println("5 - Exit");
            int option = Integer.parseInt(scanner.nextLine(), 10);

            if (option == 1) {
                System.out.println("Please enter your name and press<enter>: ");
                String name = scanner.nextLine();

                System.out.print("Please enter ticket title and press<enter>: ");
                String title = scanner.nextLine();

                System.out.print("Please enter ticket description and press<enter>: ");
                String descript = scanner.nextLine();

                System.out.print("Please enter ticket status and press<enter>: ");
                String status = scanner.nextLine();

                System.out.println("Name: " + name);
                System.out.println("Title: " + title);
                System.out.println("Description: " + descript);
                System.out.println("Status: " + status);

                statement.executeUpdate("INSERT INTO tickets (name, title, descript, status) " + "VALUES ('" + name
                        + "', '" + title + "', '" + descript + "', '" + status + "')");

            } else if (option == 2) {

                ResultSet resultSet = statement
                        .executeQuery("SELECT id, `name`, title, descript, status" + " FROM tickets" + " ORDER BY id;");
                System.out.println();
                System.out.println("Ticket list: ");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String title = resultSet.getString("title");
                    String descript = resultSet.getString("descript");
                    String status = resultSet.getString("status");

                    System.out.println(id + " '" + name + "' - " + title + " -  " + descript + " - Status: " + status);
                }
                System.out.println();
            } else if (option == 3) {

                ResultSet resultSet1 = statement
                        .executeQuery("SELECT id, `name`, title, descript, status" + " FROM tickets" + " ORDER BY id;");
                System.out.println();
                System.out.println("Ticket list: ");
                while (resultSet1.next()) {
                    int id = resultSet1.getInt("id");
                    String name = resultSet1.getString("name");
                    String title = resultSet1.getString("title");
                    String descript = resultSet1.getString("descript");
                    String status = resultSet1.getString("status");

                    System.out.println(id + " '" + name + "' - " + title + " -  " + descript + " - Status: " + status);
                }
                System.out.println();

                System.out.print("Please enter ticket ID : ");
                int id = Integer.parseInt(scanner.nextLine(), 10);
                resultSet1 = statement
                        .executeQuery("SELECT `name`, title, descript, status" + " FROM tickets" + " WHERE id = " + id);
                System.out.println();
                System.out.println("Ticket: ");
                while (resultSet1.next()) {
                    String name = resultSet1.getString("name");
                    String title = resultSet1.getString("title");
                    String descript = resultSet1.getString("descript");
                    String status = resultSet1.getString("status");

                    System.out.println(id + " '" + name + "' - " + title + " -  " + descript + " - Status: " + status);
                }
                System.out.println();

                System.out.print("Please choose new status  : ");
                String status = scanner.nextLine();

                if (status == "Resolved") {
                    statement.executeUpdate("UPDATE tickets SET status = '" + status
                            + "', resolution = CURRENT_TIMESTAMP() WHERE id = " + id);
                } else {
                    statement.executeUpdate("UPDATE tickets SET status = '" + status + "' WHERE id = " + id);
                }

            } else if (option == 4) {
                // reolution time
                ResultSet resultSet3 = statement.executeQuery(
                        "SELECT id, (IFNULL(resolution, CURRENT_TIMESTAMP()) - created) AS resolutionTime FROM tickets;");
                    while (resultSet3.next()) {
                        int id = resultSet3.getInt("id");
                        Timestamp resolutionTime = resultSet3.getTimestamp("resolutionTime");

                System.out.println(id + " '" + resolutionTime);

                // statusi
                ResultSet resultset2 = statement.executeQuery("SELECT status, COUNT(*) FROM tickets GROUP BY status;");
                while (resultset2.next()) {
                    String status = resultset2.getString("status");
                    int count = resultset2.getInt("COUNT(*)");
                    System.out.println(status + " " + count);
                }

                ResultSet resultset4 = statement.executeQuery("SELECT COUNT(*) FROM tickets;");
                while (resultset4.next()) {
                    int count = resultset4.getInt("COUNT(*)");
                    System.out.println("Total ticket count:" + " " + count);
                }

            } else if (option == 5) {
                System.out.println("Thank you for using this ticket system!");
                System.exit(0);
            }

        }

        catch (

        Exception e) {
            System.out.println("Something went wrong.");
            System.out.println(e.getMessage());
        }

    }
}