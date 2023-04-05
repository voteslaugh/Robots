package robot.windows.gui;

import java.util.ResourceBundle;

public interface LocalizedDialogSupport {
    void addListener(ResourceBundle bundle);
    void removeListeners();
    default void changeLocale(ResourceBundle bundle) {
        removeListeners();
        addListener(bundle);
    }
}
