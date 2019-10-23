package ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import db.DBException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for console menu
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class Menu {
    /**
     * Menu pattern (format menu) {@value #MENU_PATTERN}.
     */
    private static final String MENU_PATTERN = "%s - %s\n";
    /**
     * Event Logger for class Menu.
     */
    private static final Logger log = LogManager.getLogger(Menu.class.getName());
    /**
     * Menu list.
     */
    private List<MenuEntry> entries = new ArrayList<>();
    /**
     * Exit flag.
     */
    private boolean isExit = false;

    /**
     * Menu constructor.
     * Automatically create an "Exit" menu item and add it to the end of the list
     */
    public Menu() {
        entries.add(new MenuEntry("Exit") {
            @Override
            public void run() {
                isExit = true;
            }
        });
    }

    /**
     * Start an infinite loop until the exit is pressed.
     */
    public void run() {
        while (!isExit) {
            printMenu();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            InputCheck inputCheck = new InputCheck();

            try {
                String line = reader.readLine();
                if (inputCheck.isInt(line)) {
                    int choice = Integer.parseInt(line);
                    if ((choice > 0) & (choice <= entries.size())) {
                        MenuEntry entry = entries.get(choice - 1);
                        entry.run();
                    } else {
                        System.out.println("Incorrect menu item!");
                    }
                }
            } catch (IOException | DBException e) {
                log.error(e.toString());
            }
        }
    }

    /**
     * Adding an item to the menu.
     *
     * @param entry - an item of menu.
     * @return return menu.
     */
    public Menu addEntry(MenuEntry entry) {
        int index = entries.size() - 1;
        entries.add(index, entry);
        return this;
    }

    /**
     * Display menu
     */
    private void printMenu() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\nMenu:\n");
        for (int i = 0; i < entries.size(); i++) {
            MenuEntry entry = entries.get(i);
            String entryFormatted = String.format(MENU_PATTERN, (i + 1), entry.getTitle());
            buffer.append(entryFormatted);
        }
        System.out.print(buffer.toString());
    }
}

