import java.util.*;
import java.io.*;

public class Main {
    private MainMenu mainMenu;
    private AddMenu addMenu;
    private ViewMenu viewMenu;
    private static Main m;
    static Scanner scan = new Scanner(System.in);
    private static HashMap<String, ArrayList<String>> listOfLists;
    
    public static void main(String[] args) throws ClassNotFoundException{
        m = new Main();
        m.mainMenu = new MainMenu();
        m.viewMenu = new ViewMenu();
        m.addMenu = new AddMenu();
        boolean done = false;
        m.GetListFromFile(m.GetFilename());
        while (!done)
        {
            m.mainMenu.DisplayMenu(m.mainMenu.GetMenu());
            String option = GetCurrentOption();
            // System.out.println(option);
            done = RunMainMenu(option);
        }
        m.SaveListToFile(m.GetFilename());
    }
    
    private void SaveListToFile(String filename){
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(listOfLists);
            objOut.close();
            fileOut.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace();
        }
    }
    private String GetFilename(){
        System.out.print("What file do you want to open from/save to? ");
        return scan.nextLine();
    }
    
    private void GetListFromFile(String filename) throws ClassNotFoundException{
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            listOfLists = (HashMap<String, ArrayList<String>>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            listOfLists = new HashMap<String, ArrayList<String>>();
            System.out.println("File not found. Starting a new set of lists");
            // e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace();
        }

    }

    private static String GetCurrentOption(){
        System.out.print("\nWhat is your choice? ");
        String option = scan.nextLine();
        // System.out.println("What is your choice> ");
        return option;
    }

    private static boolean RunMainMenu(String option){
        if (option.equals("1")){
            AddList();
        }
        else if (option.equals("2")){
            ViewList();
        }
        else if (option.equals("3")){
            DeleteList();
        }
        else if (option.equals("4")){
        //     SaveList();
            return true;
        }
        return false;
    }

    private static void DeleteList(){
        DisplayListofLists();
        System.out.println("Which list should be deleted?");
        String option = GetCurrentOption();
        System.out.println("\nAre you sure you want to delete the list? ");
        String option2 = scan.nextLine();
        // System.out.println("Are you sure you want to delete the list> ");
        if (option2.toLowerCase().equals("yes")){
            listOfLists.remove(option);
        }
    }

    private static void DisplayListofLists(){
        System.out.println("\nList of Lists:");
        int count = 1;
        for (String key : listOfLists.keySet()) {
            System.out.println(count + ". " + key);
            count++;
        }
    }

    private static void ViewList(){
        DisplayListofLists();
        int quitSpot = listOfLists.size() + 1;
        System.out.println(quitSpot + ". Quit");
        boolean correctOption = false;
        String name = GetCurrentOption();
        boolean firstRun = true;
        while (!correctOption){
            if (!firstRun)
            {
                name = GetCurrentOption();
            }
            if (listOfLists.containsKey(name))
            {
                DisplayCurrentList(name);
                correctOption = true;
            }
            else if (name.toLowerCase().equals("quit"))
            {
                return;
            }
            else
            {
                System.out.println("\nInvalid option.\n");
            }
            firstRun = false;
        }
        firstRun = true;
        boolean done = false;
        while (!done){
            if (!firstRun)
            {
                DisplayCurrentList(name);
            }
            else{
                firstRun = false;
            }
            m.viewMenu.DisplayMenu(m.viewMenu.GetMenu());
            String option = GetCurrentOption();
            done = RunViewMenu(name, option);
        }
    }


    private static boolean RunViewMenu(String name, String option){
        if (option.equals("1"))
        {
            AddItem(name);
        }
        else if (option.equals("2"))
        {
            RemoveItem(name);
        }
        else
        {
            return true;
        }
        return false;
    }

    private static void RemoveItem(String name){
        System.out.println("Which item should be removed?");
        String item = GetCurrentOption();
        listOfLists.get(name).remove(item);
        System.out.println("\nRemoved " + item + " from " + name + ".");
    }

    private static void AddList(){
        System.out.print("\nWhat is the list name? ");
        String name = scan.nextLine();
        if (listOfLists.containsKey(name)){
            System.out.println("List already exists");
            return;
        }
        listOfLists.put(name, new ArrayList<String>());
        boolean done = false;
        while (!done)
        {
            DisplayCurrentList(name);
            m.addMenu.DisplayMenu(m.addMenu.GetMenu());
            String option = GetCurrentOption();
            done = RunAddMenu(name, option);
        }
    }


    private static void DisplayCurrentList(String name){
        System.out.println("\nCurrent contents of " + name + ":");
        int count = 1;
        for (String string : listOfLists.get(name)){ 
            System.out.println(count + ". " + string);
            count++;
        }
    }

    private static boolean RunAddMenu(String name, String option){
        if (option.equals("1"))
        {   
            AddItem(name);
        }
        else if (option.equals("2"))
        {
            return true;
        }
        return false;
    }

    private static void AddItem(String name){
        System.out.print("\nWhat do you want to add to " + name + "?");
        String item = scan.nextLine();
        listOfLists.get(name).add(item);
    }
}
