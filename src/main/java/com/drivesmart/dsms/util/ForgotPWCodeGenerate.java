package com.drivesmart.dsms.util;

import java.util.Random;

public class ForgotPWCodeGenerate {

    Random random = new Random();

    public String generateCode() {
        int code = random.nextInt(999999);
        return String.format("%06d", code);
    }

}
