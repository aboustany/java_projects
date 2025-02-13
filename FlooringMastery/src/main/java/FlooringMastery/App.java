package FlooringMastery;

import FlooringMastery.controller.FlooringController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class App implements CommandLineRunner {
    private final FlooringController controller;

    @Autowired
    public App(FlooringController controller) {
        this.controller = controller;
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        controller.run();
    }
}
