package com.drivesmart.dsms.util;

public class MailTemplate {
    public static String getPWrestMailTemplate(String code) {

        String template = "<!DOCTYPE html>\n" +
                "           <html lang=\"en\">\n" +
                "           <head>\n" +
                "               <meta charset=\"UTF-8\">\n" +
                "               <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "               <title>Password Reset Code - DriveSmart DSMS</title>\n" +
                "           </head>\n" +
                "           <body style=\"background-color: #f3f4f6; color: #333333; font-family: Arial, sans-serif; margin: 0; padding: 20px;\">\n" +
                "               <div style=\"background-color: #ffffff; border-radius: 8px; padding: 30px; max-width: 450px; margin: 0 auto; box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);\">\n" +
                "                   <div style=\"text-align: center; margin-bottom: 20px;\">\n" +
                "                       <a href=\"https://imgbb.com/\">\n" +
                "                           <img src=\"https://i.ibb.co/qrRrFXB/icon.png\" alt=\"DriveSmart DSMS Logo\" style=\"width: 120px;\" />\n" +
                "                       </a>\n" +
                "                   </div>\n" +
                "                   <h1 style=\"color: #0066cc; font-size: 26px; font-weight: bold; margin: 0 0 10px;\">Password Reset Request</h1>\n" +
                "                   <p style=\"color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 20px;\">\n" +
                "                       Hello,\n" +
                "                   </p>\n" +
                "                   <p style=\"color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 20px;\">\n" +
                "                       We received a request to reset your password for your DriveSmart DSMS account. Use the code below to proceed. If you did not make this request, please ignore this email.\n" +
                "                   </p>\n" +
                "                   <div style=\"display: inline-block; padding: 12px 25px; margin-top: 20px; background-color: #0066cc; color: #ffffff; font-size: 22px; font-weight: bold; letter-spacing: 2px; border-radius: 6px;\">\n" +
                "                        "+ code +"\n" +
                "                   </div>\n" +
                "                   <p style=\"color: #777777; font-size: 14px; line-height: 1.5; margin: 30px 0 0;\">\n" +
                "                       This code is valid for 30 minutes. If you continue to have trouble, please contact our support team.\n" +
                "                   </p>\n" +
                "                   <div style=\"text-align: center; margin-top: 40px;\">\n" +
                "                       <a href=\"https://drivesmart.com/contact\" style=\"display: inline-block; padding: 10px 20px; background-color: #333333; color: #ffffff; font-size: 14px; font-weight: bold; text-decoration: none; border-radius: 4px;\">\n" +
                "                           Contact Support\n" +
                "                       </a>\n" +
                "                   </div>\n" +
                "                   <div style=\"border-top: 1px solid #e1e1e1; margin-top: 30px; padding-top: 10px; text-align: center;\">\n" +
                "                       <p style=\"color: #aaaaaa; font-size: 12px;\">\n" +
                "                           &copy; 2024 DriveSmart DSMS. All rights reserved.\n" +
                "                       </p>\n" +
                "                   </div>\n" +
                "               </div>\n" +
                "           </body>\n" +
                "           </html>";

        return template;
    }

    public static String getWelcomeTemplate(String name){

        String welcomeTemplate = "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Welcome to DriveSmart DSMS</title>\n" +
                "</head>\n" +
                "<body style=\"background-color: #f3f4f6; color: #333333; font-family: Arial, sans-serif; margin: 0; padding: 20px;\">\n" +
                "    <div style=\"background-color: #ffffff; border-radius: 8px; padding: 30px; max-width: 600px; margin: 0 auto; box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);\">\n" +
                "        <div style=\"text-align: center; margin-bottom: 20px;\">\n" +
                "            <a href=\"https://drivesmart.com/\">\n" +
                "                <img src=\"https://i.ibb.co/qrRrFXB/icon.png\" alt=\"DriveSmart DSMS Logo\" style=\"width: 120px;\" />\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <h1 style=\"color: #0066cc; font-size: 26px; font-weight: bold; margin: 0 0 10px;\">Welcome to DriveSmart DSMS!</h1>\n" +
                "        <p style=\"color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 20px;\">\n" +
                "            Dear {userName},\n" +
                "        </p>\n" +
                "        <p style=\"color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 20px;\">\n" +
                "            We're thrilled to welcome you to the DriveSmart DSMS family! Your account has been successfully created, and you're now ready to start taking advantage of our services.\n" +
                "        </p>\n" +
                "        <p style=\"color: #555555; font-size: 16px; line-height: 1.6; margin: 0 0 20px;\">\n" +
                "            Whether you're looking to learn to drive or need refresher courses, we're here to support you every step of the way. To get started, simply log in to your account and explore the available courses.\n" +
                "        </p>\n" +
                "        <div style=\"text-align: center; margin-top: 40px;\">\n" +
                "            <a href=\"https://drivesmart.com/login\" style=\"display: inline-block; padding: 10px 20px; background-color: #0066cc; color: #ffffff; font-size: 14px; font-weight: bold; text-decoration: none; border-radius: 4px;\">\n" +
                "                Visit Our website\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <p style=\"color: #777777; font-size: 14px; line-height: 1.5; margin-top: 40px;\">\n" +
                "            If you have any questions or need assistance, feel free to contact our support team at any time.\n" +
                "        </p>\n" +
                "        <div style=\"border-top: 1px solid #e1e1e1; margin-top: 30px; padding-top: 10px; text-align: center;\">\n" +
                "            <p style=\"color: #aaaaaa; font-size: 12px;\">\n" +
                "                &copy; 2024 DriveSmart DSMS. All rights reserved.\n" +
                "            </p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

        return welcomeTemplate.replace("{userName}", name);
    }

}
