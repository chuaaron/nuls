package io.nuls.kernel.utils;

import io.nuls.kernel.model.Na;

import java.util.Scanner;

/**
 * @Desription:
 * @Author: PierreLuo
 * @Date:
 */
public class CommandHelper {

    public static boolean checkArgsIsNull(String... args) {
        for (String arg : args) {
            if (arg == null || arg.trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

    public static void confirmPwd(String newPwd) {
        System.out.print("Please enter your password again:");
        Scanner scanner = new Scanner(System.in);
        String confirmed = scanner.nextLine();
        while (!newPwd.equals(confirmed)) {
            System.out.print("The password you entered did not match.\nPlease confirm the new password:");
            confirmed = scanner.nextLine();
        }
    }

    public static String getPwd(){
        System.out.print("Please enter the password, if the account has no password directly return.\npassword:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Long getLongAmount(String arg) {
        Na na = null;
        try {
            na = Na.parseNuls(arg);
            return na.getValue();
        } catch (Exception e) {
            return null;
        }
    }

    public static Double naToNuls(Object object){
        if(null == object){
            return null;
        }
        Long na = null;
        if(object instanceof Long){
            na = (Long)object;
        }else if(object instanceof Integer){
            na = ((Integer)object).longValue();
        }else{
            return null;
        }
        return Na.valueOf(na).toDouble();
    }

}