import java.util.Scanner;
import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        String connectionString = "jdbc:mysql://localhost/ticketsystem?user=maija&password=vija";
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
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;

            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }
}
