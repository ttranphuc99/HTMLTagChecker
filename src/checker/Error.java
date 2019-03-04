/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checker;

/**
 *
 * @author Thien Phuc
 */
public class Error {
    private String thisContent, missingContent;
    private int rowNumber;

    public Error(String thisContent, String missingContent, int rowNumber) {
        this.thisContent = thisContent;
        this.missingContent = missingContent;
        this.rowNumber = rowNumber;
    }
    
    public Error(String tag, int rowNumber) {
        this.thisContent = tag;
        this.rowNumber = rowNumber;
    }

    public String getThisContent() {
        return thisContent;
    }

    public void setThisContent(String thisContent) {
        this.thisContent = thisContent;
    }

    public String getMissingContent() {
        return missingContent;
    }

    public void setMissingContent(String missingContent) {
        this.missingContent = missingContent;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public String toString() {
        return "Error at line: " + rowNumber +
                "\nFound </" +thisContent+ "> without closing </" +missingContent+ ">";
    }
    
    public String toString(boolean check) {
        return "Error at line: " + rowNumber + "\nMiss closing tag: </" + thisContent + ">";
    }
}
