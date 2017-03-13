package com.willmorris;

import java.io.PrintWriter;

public class PPM_Image {

    int m = 500;
    int n = 500;

    int[][] count = new int[m][n];
    double[][] r = new double[m][n];
    double[][] g = new double[m][n];
    double[][] b = new double[m][n];

    int i, j, k;
    int count_max = 2000;

    double x_max = 1.24;
    double x_min = -2.25;
    double y_max = 1.75;
    double y_min = -1.75;

    public PPM_Image() {

        // for each pixel indexed by i,j we need to find the
        // corresponding x, y position, then calculate the
        // value of count[ i ][ j ]
        for(i = 0; i < m; i++){
            System.out.println("Column " + i);

            for(j=0; j < n; j++) {
                System.out.println("Row " + j);

                /*
                for the i,j px, we calculate the corresponding x,y position.
                Set the count to 0
                 */
                double x = ((j - 1) * x_max + (n - j) * x_min) / (n - 1);
                /*
                (((j - 1) * x_max)
                0 - 1 = -1;
                -1 * 1.24 = -1.24;
                -1 * -1.24 = 1.24;

                (+ (n - j) * x_min) / (n - 1));
                500 - 0 = 500;
                500 * -2.25 = -1125;

                1.24 + 1125;
                1126.24;
                 */
                double y = ((i - 1) * y_max + (m - i) * y_min) / (m - 1);

                count[i][j] = 0;

                double x1 = x;
                double y1 = y;

                for (k = 1; k <= count_max; k = k + 1) {
                    double x2 = x1 * x1 - y1 * y1 + x;
                    double y2 = 2 * x1 * y1 + y;

                    if ((x2 < -2) || (2 < x2) || (y2 < -2) || (2 < y2)) {
                        count[i][j] = k;
                        break;
                    }
                    x1 = x2;
                    y1 = y2;
                }//for (k) loop

                if ( (count[i][j]%2 ) == 1 ){
                    r[i][j] = 255;
                    g[i][j] = 255;
                    b[i][j] = 255;
                } else {
                    int count_rel = count[i][j]/count_max;
                    double c = 255 * count_rel;
                    r[i][j] = 3 * c;
                    g[i][j] = 3 * c;
                    b[i][j] = c;
                }
            }
        }
    }

    public void saveToFile (String filename) {

        try {
            PrintWriter outfile = new PrintWriter(filename+".ppm");
            System.out.println("Writing out to file: " + filename+".ppm");

//            outfile.println("# File type is P3");
            outfile.println("P3");
//            outfile.println("# Image width: " + m + "and height: " + n);
            outfile.println(m + " " + n);
//            outfile.println("# maximum pixel value: " + count_max);
            outfile.println("255");

            for (int p=0; p<n; p++) {
                for(int c=0; c<m; c++) {
                    outfile.print((int)r[c][p]);
                    outfile.print(" ");
                    outfile.print((int)g[c][p]);
                    outfile.print(" ");
                    outfile.print((int)b[c][p]);
                    outfile.print(" ");
                }
                outfile.println();
            }
            outfile.close();
        }
        catch (Exception e) {
            System.out.println(e.toString() + " caught in createPPM.");
            e.printStackTrace();
        }
    }


}
