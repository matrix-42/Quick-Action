package garin.artemiy.quickaction.library;

/**
 * Author: Artemiy Garin
 * Date: 21.04.14
 */
public class QuickActionItem {

    private int id;
    private String title;

    public QuickActionItem(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
