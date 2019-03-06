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
    private Stack<String> stack;
    private ArrayList<Error> result;

    public Checker(String fileName) {
        this.fileName = fileName;
        this.stack = new Stack<>();
        result = new ArrayList<>();
    }
    
    private void tranverseStack(Stack<String> s1, Stack<String> s2) {
        while (!s1.isEmpty()) {
            s2.push(s1.pop());
        }
    }

    public ArrayList<Error> check() {

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
            String popContent = "";
            int line = 0;
            while ((str = br.readLine()) != null) {
                System.out.println("Line" + (line + 1) + ": " + str);
                line++;
                m = p.matcher(str);

                while (m.find()) {
                    tagContent = m.group(1).trim().split(" ")[0].toLowerCase();
                    if (tagContent.charAt(0) == '/') {
                        tagContent = tagContent.substring(1);
                        if (!stack.isEmpty()) {
                            popContent = stack.pop();
                            //check
                            if (!popContent.equals(tagContent)) {
                                Stack<String> tStack = new Stack<>();
                                tStack.push(popContent);
                                while (!stack.isEmpty()) {
                                    String tPopContent = stack.pop();
                                    tStack.push(tPopContent);
                                    if (tPopContent.equals(tagContent)) {
                                        tStack.pop();
                                        Stack<String> tStack2 = new Stack<>();
                                        tranverseStack(tStack, tStack2);
                                        while (!tStack2.isEmpty()) {
                                            String pop = tStack2.pop();
                                            Error e = new Error(pop, line, true);
                                            result.add(e);
                                        }
                                        break;
                                    }
                                }
                                if (stack.isEmpty()) {
                                    Error e = new Error(tagContent, line, false);
                                    result.add(e);
                                    while (!tStack.isEmpty()) {
                                        stack.push(tStack.pop());
                                    }
                                }
                            }
                        } else {
                            Error e = new Error(tagContent, line, false);
                            result.add(e);
                        }

                    } else {
                        stack.push(tagContent);
                    }
                }
            }
            if (!stack.isEmpty()) {
                do {
                    String tag = stack.pop();
                    Error e = new Error(tag, line, true);
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
