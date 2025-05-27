import com.forbidden.island.ui.SplashScreen;
import com.forbidden.island.utils.Constant;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen(Constant.RESOURCES_PATH + "/TitleScreen.png");
        });
    }
}