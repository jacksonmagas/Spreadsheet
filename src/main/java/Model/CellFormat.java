package Model;

/**
 * Class representing a basic cell in a spreadsheet with only size information.
 */
public class CellFormat {
    private int width; // Width of the cell in pixels
    private int height; // Height of the cell in pixels

    public CellFormat(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}