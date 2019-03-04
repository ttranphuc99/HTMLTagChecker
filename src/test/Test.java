/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import checker.Checker;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Thien Phuc
 */
public class Test {

    public static void main(String[] args) {
        boolean loop = true;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("====HTML tag checker program====");
            System.out.println("Input the file name with the extension is .html");
            boolean isWrongExtension = true;
            String filename;
            do {
                filename = sc.nextLine();
                int startExtension = filename.lastIndexOf('.');
                String extension = filename.substring(startExtension + 1);

                if (!extension.equalsIgnoreCase("html")) {
                    System.out.println("Wrong file extension. Try again!");
                    isWrongExtension = true;
                } else {
                    isWrongExtension = false;
                }
            } while (isWrongExtension);

            Checker c = new Checker(filename);

            ArrayList<checker.Error> error = c.check();
            if (error != null) {
                if (error.isEmpty()) {
                    System.out.println("No error found!");
                } else {
                    System.out.println("Found " + error.size() + " error(s) in your file.\n");
                    for (checker.Error error1 : error) {
                        System.out.println(error1.getMissingContent() != null ? error1.toString() : error1.toString(true));
                        System.out.println("");
                    }
                }
            }

            System.out.println("Do you want to continue? [Y/N]");
            loop = sc.nextLine().toUpperCase().charAt(0) == 'Y';
        } while (loop);
    }
}
