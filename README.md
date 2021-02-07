# Bus-Booking-Portal
Designing of Bus Booking Portal using JAVA Swing. User can enter the details regarding the journey and can select the seats according to his/her preference. User can also cancel the booked seats. The Details of journey are stored in files on user computer. Implementation of FIrebase Realtime Database will replace file management and can improve project coverage from local to global scope.


NOTE: Please add the given .jar files to the library of the project in the given IDE. 

If user is using VS-Code as IDE for java, then he can run the program by clicking run button appearing in the main method which is present at the last of the code or else can directly run the program if uses Net beans / Eclipse as IDE. 

The first page appears after running the program would be login page. If the user is new then he/she can click on register button to register oneself or if already registered can fill the credentials asked and login into the page. 

After login, one can see the menu bar which has several options that user can select for oneself. The options include... 

		1. Personal information => to get personal information 
		2. Search Buses => to search buses according to the need 
		3. Ticket Info => to get information about the ticket using PNR 
		4. Ticket cancellation => to cancel the ticket using PNR 
		5. Contact us => shows dialog box regarding contact details 

1) Personal Information: This option allows user to get personal information which were entered during the time of registration. It also shows the username and password along with the information. 

2) Search Buses: This options allows users to search buses. 

After clicking the search buses one can see the drop down boxes for source, destination, type of bus, journey date, journey time. 
The list of buses will appear along with their details if there is any bus available for that route. Select any of the bus. 
After selecting bus, the seat layout will appear. Click on the seats on unreserved seats to reserve them. The foreground will change to red. If user wants to unreserve it then click again it will unreserved it. The booked seats will already be red coloured. 
After selecting the seat(s), click on “CONTINUE” button which will take you to fill the form which will be necessary to generate ticket information. After filling the information, click on “continue” button to get the ticket information regarding the booking. The ticket information includes 
		1. PNR 
		2. Name 
		3. Source 
		4. Destination 
		5. Journey Date 
		6. Boarding Time 
		7. Fare 
		8. Bus Name 
		9. Bus Type 
		10. Seats 
		11. Status 

Once booked close all the windows and remember the PNR number and again log-in. 

3) Ticket Information: After booking the ticket, click on “ticket info” option to get the details of the ticket. It asks user about the “PNR” number of the ticket and gives all the information about the ticket if the PNR number exists. 

4) Ticket Cancellation: If user wants to cancel the ticket, then a page will appear which will cancel the ticket if username is exactly as used to book the ticket ago. It will ask the PNR of the ticket. If it exists than it will be cancelled and the seat(s) will be freed. 

5) Contact Us : It will open the dialog box which gives information about the contacting details of the same. 
