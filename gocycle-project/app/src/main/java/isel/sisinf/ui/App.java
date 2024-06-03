/*
MIT License

Copyright (c) 2024, Nuno Datia, Matilde Pato, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.ui;

import isel.sisinf.model.*;
import isel.sisinf.model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.HashMap;

interface DbWorker
{
    void doWork();
}
class UI
{
    private enum Option
    {
        // DO NOT CHANGE ANYTHING!
        Unknown,
        Exit,
        createCostumer,
        listExistingBikes,
        checkBikeAvailability,
        obtainBookings,
        makeBooking,
        cancelBooking,
        about
    }
    private static UI __instance = null;
  
    private HashMap<Option,DbWorker> __dbMethods;

    private UI()
    {
        // DO NOT CHANGE ANYTHING!
        __dbMethods = new HashMap<Option,DbWorker>();
        __dbMethods.put(Option.createCostumer, () -> UI.this.createCostumer());
        __dbMethods.put(Option.listExistingBikes, () -> UI.this.listExistingBikes()); 
        __dbMethods.put(Option.checkBikeAvailability, () -> UI.this.checkBikeAvailability());
        __dbMethods.put(Option.obtainBookings, new DbWorker() {public void doWork() {UI.this.obtainBookings();}});
        __dbMethods.put(Option.makeBooking, new DbWorker() {public void doWork() {UI.this.makeBooking();}});
        __dbMethods.put(Option.cancelBooking, new DbWorker() {public void doWork() {UI.this.cancelBooking();}});
        __dbMethods.put(Option.about, new DbWorker() {public void doWork() {UI.this.about();}});

    }

    public static UI getInstance()
    {
        // DO NOT CHANGE ANYTHING!
        if(__instance == null)
        {
            __instance = new UI();
        }
        return __instance;
    }

    private Option DisplayMenu()
    {
        Option option = Option.Unknown;
        Scanner s = new Scanner(System.in); //Scanner closes System.in if you call close(). Don't do it
        try
        {
            // DO NOT CHANGE ANYTHING!
            System.out.println("Bicycle reservation");
            System.out.println();
            System.out.println("1. Exit");
            System.out.println("2. Create Costumer");
            System.out.println("3. List Existing Bikes");
            System.out.println("4. Check Bike Availability");
            System.out.println("5. Current Bookings");
            System.out.println("6. Make a booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. About");
            System.out.print(">");
            int result = s.nextInt();
            option = Option.values()[result];
        }
        catch(RuntimeException ex)
        {
            //nothing to do.
        }
        
        return option;

    }
    private static void clearConsole() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        for (int y = 0; y < 25; y++) //console is 80 columns and 25 lines
            System.out.println("\n");
    }

    public void Run() throws Exception
    {
        // DO NOT CHANGE ANYTHING!
        Option userInput;
        do
        {
            clearConsole();
            userInput = DisplayMenu();
            clearConsole();
            try
            {
                __dbMethods.get(userInput).doWork();
                System.in.read();
            }
            catch(NullPointerException ex)
            {
                //Nothing to do. The option was not a valid one. Read another.
            }

        }while(userInput!=Option.Exit);
    }

    /**
    To implement from this point forward. Do not need to change the code above.
    -------------------------------------------------------------------------------     
        IMPORTANT:
    --- DO NOT MOVE IN THE CODE ABOVE. JUST HAVE TO IMPLEMENT THE METHODS BELOW ---
    --- Other Methods and properties can be added to support implementation -------
    -------------------------------------------------------------------------------
    
    */

    private static final int TAB_SIZE = 24;

    private void createCostumer() {
        // TODO
        JPAContext ctx = new JPAContext();
        ctx.beginTransaction();
        //System.out.println(        ctx.getAllClients());
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the name of the client:");
        String name = s.nextLine();
        System.out.println("Enter the address of the client:");
        String address = s.nextLine();
        System.out.println("Enter the email of the client:");
        String email = s.nextLine();
        System.out.println("Enter the phone number of the client:");
        String phone = s.nextLine();
        System.out.println("Enter the identification number of the client:");
        String noIdent = s.nextLine();
        System.out.println("Enter the nationality of the client:");
        String nationality = s.nextLine();
        System.out.println("Enter the discount attribute of the client:");
        //Character atrDisc = s.next().charAt(0);
        Character atrDisc = s.nextLine().charAt(0);
        Client newClient = new Client();
        newClient.setClientName(name);
        newClient.setAddress(address);
        newClient.setEmail(email);
        newClient.setPhone(phone);
        newClient.setNoIdent(noIdent);
        newClient.SetNationality(nationality);
        newClient.setAtrDisc(atrDisc);
        System.out.println(ctx.createClient(newClient));
        //ctx.createClient(newClient);
    }
  
    private void listExistingBikes()
    {
            System.out.println("listExistingBikes()");
            JPAContext ctx = new JPAContext();
            ctx.beginTransaction();
            Collection<Bike> allBikes = ctx.getAllBikes();
            ctx.commit();
            for(Bike b: allBikes){
                System.out.println(b.toString());
            }
    }

    private void checkBikeAvailability()
    {
        JPAContext ctx = new JPAContext();
        Scanner s = new Scanner(System.in);
        System.out.println("enter de Id of the bike you want to check the availability of:");
        String bikeId =  s.nextLine();
        Integer bikeIdInt = Integer.parseInt(bikeId);
        System.out.println("Enter the date you want to check the availability of the bike in the format yyyy-MM-dd HH:mm:");
        String date = s.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateDate = null;
        try {
            dateDate = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("The entered date is not in the correct format. Please use yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.println(ctx.checkAvailability(bikeIdInt, dateDate));

    }

    private void obtainBookings() {
            System.out.println("obtainBookings()");
            JPAContext ctx = new JPAContext();
            ctx.beginTransaction();
            Collection<Reservation> all = ctx.getAllReservations();
            ctx.commit();
            for(Reservation res: all){
                System.out.println(res.toString());
            }
    }

    private void makeBooking()
    {
        try {
            System.out.println("makeBooking()");
            JPAContext ctx = new JPAContext();
            ctx.beginTransaction();
            Reservation r = new Reservation();
            //get a store
            Integer noStore = getStore(ctx);
            Store store = ctx.getStore(Long.parseLong(noStore.toString()));
            r.setStore(store);
            //get customer
            Integer CustomerId = getCustomer(ctx);
            Client c = ctx.getClient(Long.parseLong(CustomerId.toString()));
            r.setClient(c);
            //get bike
            Integer BikeId = getFreeBike(ctx);
            Bike b = ctx.getBike(Long.parseLong(BikeId.toString()));
            r.setBike(b);
            //get date
            Timestamp date = getDateFromUser();
            if(date == null){
                System.out.println("Invalid date format. Reservation canceled.");
                ctx.close();
                return;
            }
            r.setStartDate(date);
            //check Reservation Integrity
            if(ctx.checkReservationIntegrity(CustomerId, BikeId, date)){
                ctx.createReservation(r);
            }
            else {
                System.out.println("Reservation Check Failed!!");
                ctx.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void cancelBooking()
    {

        System.out.println("cancelBooking");
        
            System.out.println("cancelBooking");
            JPAContext ctx = new JPAContext();
            ctx.beginTransaction();
            //Get the Reservation
            Reservation r = ctx.getReservation(getAReservation(ctx));
            //Delete the reservation
            ctx.deleteReservation(r);
            ctx.commit();
            System.out.println("Reservation with Number " + r.getNoReservation() + " was Cancelled!!");
    }
    private void about()
    {
        // TODO: Add your Group ID & member names
        System.out.println("DAL version:"+ isel.sisinf.jpa.Dal.version());
        System.out.println("Core version:"+ isel.sisinf.model.Core.version());
        
    }

    private Integer getCustomer(JPAContext ctx){
        System.out.println("Choose a Customer ID for the Booking");
        Collection<Client> allClients = ctx.getAllClients();
        for(Client c: allClients){
            System.out.println(c.toString());
        }
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    private Integer getStore(JPAContext ctx){
        System.out.println("Choose a Store for the booking");
        Collection<Store> allStores = ctx.getAllStores();
        for(Store s: allStores){
            System.out.println(s.toString());
        }
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    private Integer getFreeBike(JPAContext ctx){
        System.out.println("Choose a Bike ID for the Booking");
        Collection<Bike> allFreeBikes = ctx.getAllFreeBikes();
        for(Bike b: allFreeBikes){
            System.out.println(b.toString());
        }
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    private Long getBike(JPAContext context){
        System.out.println("Choose a Bike ID");
        Collection<Bike> all = context.getAllBikes();
        for(Bike b: all){
            System.out.println(b.toString());
        }
        Scanner s = new Scanner(System.in);
        return s.nextLong();
    }

    private Long getAReservation(JPAContext context){
        System.out.println("Choose a Reservation Id to Cancel:");
        //Display the Existent Reservations
        Collection<Reservation> allReservations = context.getAllReservations();
        for (Reservation r : allReservations){
            System.out.println(r.toString());
        }
        //Get the wanted reservation
        Scanner s = new Scanner(System.in);
        return s.nextLong();
    }

    private Timestamp getDateFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter reservation start date and time (format: dd/MM/yyyy HH:mm:ss):");
        String dateString = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            java.util.Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class App{
    public static void main(String[] args) throws Exception{
        UI.getInstance().Run();
    }
}