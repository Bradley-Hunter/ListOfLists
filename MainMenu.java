public class MainMenu {
    private String[] menu = {"\n", "Main Menu", "--------------", "1. Add List", "2. View List", "3. Delete List", "4. Quit/Save"};

    public MainMenu(){

    }

    public void DisplayMenu(String[] menu){
        for (String string : menu) {
            System.out.println(string);
        }
    }

    public String[] GetMenu(){
        return menu;
    }
}
