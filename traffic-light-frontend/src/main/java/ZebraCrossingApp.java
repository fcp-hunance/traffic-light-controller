import controller.TrafficController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.AppConfig;
import view.MainFrame;

public class ZebraCrossingApp extends Application {
    private static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        // Initialize Spring context
        context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Launch JavaFX application
        launch(args);

        // Manually trigger initialization after context is ready
        TrafficController controller = context.getBean(TrafficController.class);
        controller.initializeAfterStartup(); // Call this instead of relying on ApplicationReadyEvent
    }

    @Override
    public void start(Stage primaryStage) {
        // Get the MainFrame bean from Spring context
        MainFrame mainFrame = context.getBean(MainFrame.class);

        // Initialize and show the main window
        mainFrame.initialize(primaryStage);

        // Properly close the Spring context when the application exits
        primaryStage.setOnCloseRequest(event -> {
            context.close();
            Platform.exit();
        });
    }
}