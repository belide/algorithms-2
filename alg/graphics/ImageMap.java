package alg.graphics;

import alg.misc.InterestingAlgorithm;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImageMap {

    private int maxColors;
    private int rows;
    private int cols;
    private int [][] data;

    public ImageMap (int rows, int cols, int maxColors) {
        this.maxColors = maxColors;
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols]; // TODO  more efficient storage based on maxColors
    }

    public ImageMap (int [][] data) {
        this.data = data;
        if (data != null && data.length > 0) {
            this.rows = data.length;
            this.cols = data [0].length;
        }
    }

    public int getColorAt (int row, int col) {
        return (data [row][col]);
    }

    public void setColorAt (int row, int col, int color) {
        if (color == TMP_SET_COLOR)
            throw new RuntimeException("Cannot use color: " + TMP_SET_COLOR);
        data [row][col] = color;
    }

    public String toString () {
        StringBuilder buf = new StringBuilder("[");
        for (int i = 0; i < data.length; i++) {
            buf.append("[");
            for (int j = 0; j < data [i].length; j++) {
                buf.append(data[i][j]);
                if (j < data[i].length - 1)
                    buf.append(", ");
            }
            buf.append("]\n");
        }
        buf.append("]");
        return (buf.toString());
    }

    public int getMaxColors() {
        return maxColors;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static final int TMP_SET_COLOR = -1;

    @InterestingAlgorithm
    public  ImageMap erode () {
        // TODO: need to rewrite this to support multiple colors beyond B&W!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == 0) {
                    if (i > 0 && data [i - 1][j] != 0)
                        data [i - 1][j] = TMP_SET_COLOR;
                    if (j > 0 && data [i][j - 1] != 0)
                        data [i][j - 1] = TMP_SET_COLOR;
                    if (i + 1 < rows && data [i + 1][j] != 0)
                        data [i + 1][j] = TMP_SET_COLOR;
                    if (j + 1 < cols && data [i][j + 1] != 0)
                        data [i][j + 1] = TMP_SET_COLOR;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == TMP_SET_COLOR) {
                    data[i][j] = 0;
                }
            }
        }
        return (this);
    }

    @InterestingAlgorithm
    public  ImageMap dilate () {

        // TODO: need to rewrite this to support multiple colors beyond B&W!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value != 0 && value != TMP_SET_COLOR) {
                    if (i > 0 && data [i - 1][j] == 0)
                        data [i - 1][j] = TMP_SET_COLOR;
                    if (j > 0 && data [i][j - 1] == 0)
                        data [i][j - 1] = TMP_SET_COLOR;
                    if (i + 1 < rows && data [i + 1][j] == 0)
                        data [i + 1][j] = TMP_SET_COLOR;
                    if (j + 1 < cols && data [i][j + 1] == 0)
                        data [i][j + 1] = TMP_SET_COLOR;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == TMP_SET_COLOR) {
                     data[i][j] = 1;
                }
            }
        }
        return (this);
    }

    @InterestingAlgorithm
    public ImageMap dilate (int k) {
        convertToManhattanDistance();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (data [i][j] <= k ? 1 : 0);
            }
        }
        return (this);
    }

    @InterestingAlgorithm
    public ImageMap erode (int k) {

        // TODO: remove this inefficiency
        for (int i = 0; i < k; i++)
            erode();

        return (this);
    }

    public ImageMap  clone () {
        ImageMap copy = new ImageMap(rows, cols, maxColors);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy.setColorAt(i, j, data [i][j]);
            }
        }
        return (copy);
    }

    @InterestingAlgorithm
    public ImageMap  convertToManhattanDistance () {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (getColorAt(i, j) != 0) {
                    setColorAt(i, j, 0);
                }
                else {
                    setColorAt(i, j, rows + cols);
                    if (i > 0)
                        setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i - 1, j) + 1));
                    if (j > 0)
                        setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i, j - 1) + 1));
                }
            }
        }

        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                if (i + 1 < rows)
                    setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i + 1, j) + 1));
                if (j + 1 < cols)
                    setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i, j + 1) + 1));
            }
        }
        return (this);
    }

    public ImageMap threshold (int threshhold) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (data[i][j] > threshhold)
                    data[i][j] = threshhold;
            }
        }

        return (this);
    }

    @InterestingAlgorithm
    public int      getAverageColorAround (int i, int j) {

        if (rows == 1 && cols == 1)
            return (0);
        else if (i >= rows || j >= cols)
            throw new RuntimeException("out of range call to getAverageColorAround: " + i + ", " + j);

        int total = 0;
        int ctr = 0;
        if (i - 1 > 0) {
            if (j - 1 > 0) {
                if (data [i - 1][j - 1] > 0) {
                    total += data [i - 1][j - 1];
                    ctr++;
                }
            }
            if (data [i - 1][j] > 0) {
                total += data [i - 1][j];
                ctr++;
            }

            if (j + 1 < cols) {
                if (data [i - 1][j + 1] > 0) {
                    total += data [i - 1][j + 1];
                    ctr++;
                }
            }
        }
        if (j - 1 > 0) {
            if (data [i][j - 1] > 0) {
                total += data [i][j - 1];
                ctr++;
            }
        }
        if (j + 1 < cols) {
            if (data [i][j + 1] > 0) {
                total += data [i][j + 1];
                ctr++;
            }
        }
        if (i + 1 < rows) {
            if (j - 1 > 0) {
                if (data [i + 1][j - 1] > 0) {
                    total += data [i + 1][j - 1];
                    ctr++;
                }
            }
            if (data [i + 1][j] > 0) {
                total += data [i + 1][j];
                ctr++;
            }

            if (j + 1 < cols) {
                if (data [i + 1][j + 1] > 0) {
                    total += data [i + 1][j + 1];
                    ctr++;
                }
            }
        }

        return (total / ctr);
    }

}
