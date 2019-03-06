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
    private String thisContent;
    private int rowNumber;
    private boolean isClose;

    public Error(String thisContent, int rowNumber, boolean isClose) {
        this.thisContent = thisContent;
        this.rowNumber = rowNumber;
        this.isClose = isClose;
    }

    public String getThisContent() {
        return thisContent;
    }

    public void setThisContent(String thisContent) {
        this.thisContent = thisContent;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public String toString() {
        if (isClose) {
            return "Error at row: " +rowNumber+ "\nMissing close tag of <" +thisContent+ ">";
        }
        return "Error at row: " +rowNumber+ "\nMissing open tag of </" +thisContent+ ">";
    }
}
