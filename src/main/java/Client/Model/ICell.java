<<<<<<<< HEAD:src/main/java/com/example/huskysheet/service/ICell.java
package com.example.huskysheet.service;

import com.example.huskysheet.utils.CellFormat;
import com.example.huskysheet.model.Expressions.ITerm;
import com.example.huskysheet.utils.Coordinate;
========
package Client.Model;

import Client.Model.Utils.CellFormatDetails;
import Client.Model.Utils.Coordinate;
import Client.Model.Expressions.ITerm.ResultType;
>>>>>>>> UIIntegration:src/main/java/Client/Model/ICell.java

/**
 * An ICell is a rectangular cell in a spreadsheet with a term of data
 * and formatting details
 * Jackson Magas
 */
public interface ICell extends ICellListener {
    // Set the contents of this cell to the given data
    void updateCell(String data);

    // get the coordinates of this cell
    Coordinate getCoordinate();

    /**
     * Get the value of the user input in this cell once parsed and evaluated
     * @return The value of the user input as a string
     */
    String getData();

    /**
     * Get the verbatim text the user entered into this cell
     * @return The user entered text as a string
     */
    String getPlaintext();

    // returns an object containing details about the formatting of the cell
    CellFormatDetails getFormatting();

    // returns true if the cell is empty
    boolean isEmpty();

    // Recalculates the value of the cell in response to a cell
    // this depends on changes
    void handleValueChange();

    // Register as a listener for changes to the value of this cell including changes to underlying
    // referenced cells
    void registerListener(ICellListener listener);

    // Notify all references to this cell that the cell has changed
    void notifyListeners();

    /**
     * Set the formatting of the cell to the new formatting
     * @param formatting formatting
     */
<<<<<<<< HEAD:src/main/java/com/example/huskysheet/service/ICell.java
    void setFormatting(CellFormat formatting);
========
    void setFormatting(CellFormatDetails formatting);
>>>>>>>> UIIntegration:src/main/java/Client/Model/ICell.java

    /**
     * Get the type of data held by this cell either string, number, or empty
     * @return the data type
     */
<<<<<<<< HEAD:src/main/java/com/example/huskysheet/service/ICell.java
    ITerm.ResultType dataType();
========
    ResultType dataType();
>>>>>>>> UIIntegration:src/main/java/Client/Model/ICell.java

}
