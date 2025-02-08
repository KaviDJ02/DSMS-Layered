package com.drivesmart.dsms.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmailTemplateUtil {

    public static String getPasswordResetEmailContent(String resetCode) {
        String templatePath = "src/main/resources/templates/Password Reset.html"; // Adjust path as needed
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(templatePath)));

            // Insert the reset code dynamically
            content = content.replace("{code}", resetCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public List<String> getTemplates() {
        List<String> templateNames = new ArrayList<>();
        String folderPath = "src/main/resources/templates";
        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null && files.length > 0) {
                for (File file : files) {
                    templateNames.add(file.getName());
                }
            } else {
                System.out.println("The folder is empty.");
            }
        } else {
            System.out.println("The specified path is not a directory.");
        }

        return templateNames;
    }

    public String getTemplateContent(String templateName) {
        String templatePath = "src/main/resources/templates/" + templateName; // Adjust path as needed
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(templatePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    public static void main(String[] args) {
        EmailTemplateUtil emailTemplateUtil = new EmailTemplateUtil();
        emailTemplateUtil.getTemplates();
    }
}
