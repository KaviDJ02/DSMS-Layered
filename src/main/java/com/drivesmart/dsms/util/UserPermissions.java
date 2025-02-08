package com.drivesmart.dsms.util;

public class UserPermissions {

    public static boolean isAdmin() {
        if(UserSession.getInstance().getUserRole() == null) {
            AlertUtil.showSimpleAlert("User Role Not Found", "User Role Not Found", "User role not found in the session. Please login again.");
            return false;
        }

        if (!UserSession.getInstance().getUserRole().equals("admin")) {
            AlertUtil.showSimpleAlert("Permission Denied", "Permission Denied", "You do not have permission to perform this action.");
            return false;
        }

        return true;
    }
}
