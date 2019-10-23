package ui;

import db.DBException;

import java.io.IOException;

/**
 * Abstract class for console menu
 *
 * @author Baturo Valery
 * @version 1.0
 */
public abstract class MenuEntry {

    /**
     * Menu item title.
     */
    private String title;

    /**
     * MenuEntry constructor.
     *
     * @param title menu item title.
     */
    public MenuEntry(String title) {
        this.title = title;
    }

    /**
     * Return title.
     *
     * @return menu item title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     *
     * @param title set menu item title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Execution when selecting a menu item.
     *
     * @throws DBException error with database
     * @throws IOException Output input error
     */
    public abstract void run() throws DBException, IOException;
}
