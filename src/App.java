import java.util.Scanner;
import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        String connectionString = "jdbc:mysql://localhost/ticketsystem?user=root&password=ashanti9999";
        Connection conn = DriverManager.getConnection(connectionString);
        Statement statement = conn.createStatement();
        boolean keepLooping = true;
        
        try (Scanner scanner = new Scanner(System.in)) {
            
            
            while(keepLooping) {
                System.out.println("Please choose from options below:");
                System.out.println();
                System.out.println("1 - Create new ticket");
                System.out.println("2 - Show all tickets");
                System.out.println("3 - Change ticket status");
                System.out.println("4 - Weekly SLA report");
                System.out.println("5 - Exit");
                int option = Integer.parseInt(scanner.nextLine(), 10);
    
                if (option == 5) keepLooping = false;
                
            
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
                        // ticket lst
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
                        // ticket update
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
        
                        if (status.equals("Resolved")) {
                            statement.executeUpdate("UPDATE tickets SET status = '" + status
                                    + "', resolution = CURRENT_TIMESTAMP() WHERE id = " + id);
                        } else {
                            statement.executeUpdate("UPDATE tickets SET status = '" + status + "' WHERE id = " + id);
                        }
        
                    } else if (option == 4) {
                        // total ticket count
                        ResultSet resultset4 = statement.executeQuery("SELECT COUNT(*) FROM tickets;");
                        while (resultset4.next()) {
                            int countOfTickets = resultset4.getInt("COUNT(*)");
                            System.out.println();
                            System.out.println("Total ticket count:" + " " + countOfTickets);
                        }
                        // count by status
                        ResultSet resultset2 = statement.executeQuery("SELECT status, COUNT(*) FROM tickets GROUP BY status;");
                        int resolvedcount = 0;
                        while (resultset2.next()) {
                            String status = resultset2.getString("status");
                            int countOfStatus = resultset2.getInt("COUNT(*)");
                            System.out.println("Total tickets " + "'" + status + "'" + " " + countOfStatus);
                            if (resultset2.getString("status").equals("Resolved")) {
                                resolvedcount++;
                            }
                        }
                        // resolution time
                        ResultSet resultSet3 = statement.executeQuery(
                                "SELECT id, TIMESTAMPDIFF (MINUTE, created, IFNULL(resolution, CURRENT_TIMESTAMP())) AS resolutionTime FROM tickets WHERE resolution IS NOT NULL;");
                        // ResultSet results = statement
                        // .executeQuery("SELECT COUNT(*) FROM tickets WHERE status LIKE '%Resolved%'");
        
                        System.out.println();
                        System.out.println("Resolution time for resolved tickets:");
                        int total = 0;
                        while (resultSet3.next()) {
                            int id = resultSet3.getInt("id");
                            Long resolutionTime = resultSet3.getLong("resolutionTime");
                            total = (int) (total + resolutionTime);
                            System.out.println("- Ticket # " + id + " resolved in " + resolutionTime + " minutes");
                        }
        
                        int average = total / resolvedcount;
                        System.out.println("Average ticket resolving time:" + average);
                        // ResultSet resultSet4 = statement.executeQuery(
                        // "SELECT TIME_FORMAT(avg(cast(resolution as time)),'%h:%i:%s %p') as
                        // resolutionTime from tickets where resolution IS NOT NULL");
                        // while (resultSet4.next()) {
                        // var resolutionTime = resultSet4.getObject("resolutionTime");
                        // System.out.println(resolutionTime);
                        // }
                    } else if (option == 5) {
                        System.out.println("Thank you for using this ticket system!");
                        System.exit(0);
                    }
                    
                }
            }
        
                catch (
        
                Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
        
            }
        }