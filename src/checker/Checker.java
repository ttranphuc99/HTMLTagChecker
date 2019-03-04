/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Thien Phuc
 */
public class Checker {

    private String fileName;

    public Checker(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Error> check() {
        ArrayList<Error> result = null;

        //use a stack to check
        Stack<String> stack = new Stack<>();

        //open file and read a line until end of file
        File f = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            f = new File(fileName);
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            result = new ArrayList<>();
            
            //use regex to get content of a tag, and put it in a stack
            String regex = "\\<(.*?)\\>";
            Pattern p = Pattern.compile(regex);
            Matcher m = null;

            String str;
            String tagContent = "";
            String popContent;
            int line = 0;
            while ((str = br.readLine()) != null) {
                System.out.println("Line" + (line+1) + ": " + str);
                line++;
                m = p.matcher(str);

                while (m.find()) {
                    tagContent = m.group(1).trim().split(" ")[0].toLowerCase();
                    if (tagContent.charAt(0) == '/') {
                        tagContent = tagContent.substring(1);
                        popContent = stack.pop();
//                            System.out.println("Push: " + popContent);

                        //check
                        if (!popContent.equals(tagContent)) {
                            Error error = new Error(tagContent, popContent, line);
                            result.add(error);
//                            stack.push(popContent);
                        }
                    } else {
//                            System.out.println("Pop: " + tagContent);
                        stack.push(tagContent);
                    }
                }
            }
            if (!stack.isEmpty()) {
                do {
                    String tag = stack.pop();
                    Error e = new Error(tag, line);
                    result.add(e);
                } while (!stack.isEmpty());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't not found file: " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
