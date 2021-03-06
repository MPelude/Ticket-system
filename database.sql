CREATE DATABASE ticketSystem;
USE ticketSystem;
GRANT ALL PRIVILEGES ON ticketsystem. * TO 'root'@'localhost';

CREATE TABLE tickets (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`  VARCHAR(45) NOT NULL,
  title VARCHAR(45) NOT NULL,
  descript VARCHAR(100) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  resolution TIMESTAMP,
 `status` ENUM('New', 'In processing', 'Resolved') NOT NULL DEFAULT 'New'
);

CREATE TABLE statuses (
id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
ticketID INT UNSIGNED NOT NULL,
FOREIGN KEY (ticketID) REFERENCES tickets (id),
`status` ENUM('New', 'In processing', 'Resolved') NOT NULL DEFAULT 'New',
 `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  );
  
   -- insert into actions (ticketID)
		-- values ('1');
	-- select * from actions;
    
   
  
  