package com.weather.advisory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableFeignClients
public class AgriculturalWeatherBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgriculturalWeatherBackendApplication.class, args);
    }
}

@Component
class FrontendLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void startFrontend() {
        new Thread(() -> {
            try {
                String frontendPath = new File("../fronted/farm-weather-guide").getAbsolutePath();
                File frontendDir = new File(frontendPath);

                if (!frontendDir.exists()) {
                    System.err.println("[ERROR] Frontend directory not found at: " + frontendPath);
                    return;
                }

                System.out.println("[INFO] Starting frontend server at: " + frontendPath);

                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(frontendDir);
                pb.inheritIO();

                // Try different package managers in order
                String[] packageManagers = {"npm", "bun", "yarn", "pnpm"};
                String selectedPM = null;

                for (String pm : packageManagers) {
                    try {
                        Process checkProcess = new ProcessBuilder(pm, "--version")
                            .redirectErrorStream(true)
                            .start();
                        if (checkProcess.waitFor() == 0) {
                            selectedPM = pm;
                            System.out.println("[INFO] Found package manager: " + pm);
                            break;
                        }
                    } catch (Exception ignored) {
                    }
                }

                if (selectedPM == null) {
                    System.err.println("[ERROR] No package manager found (npm, bun, yarn, or pnpm)");
                    System.err.println("[INFO] Please start frontend manually: cd " + frontendPath + " && npm run dev");
                    return;
                }

                // Start the dev server
                pb.command(selectedPM, "run", "dev");
                Process process = pb.start();

                System.out.println("[INFO] Frontend server started successfully with " + selectedPM);
                System.out.println("[INFO] Frontend: http://localhost:8081 (or check console)");
                System.out.println("[INFO] Backend:  http://localhost:8080");

                // Keep the process alive
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.out.println("[INFO] Shutting down frontend server...");
                    process.destroy();
                }));

            } catch (Exception e) {
                System.err.println("[ERROR] Failed to start frontend: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}

