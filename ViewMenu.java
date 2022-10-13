public class ViewMenu extends MainMenu{
    private String[] menu = {"\n","List Options", "--------------", "1. Add Item", "2. Remove Item", "3. Quit"};

    public ViewMenu(){
        
    }

    public String[] GetMenu(){
        return menu;
    }
}
