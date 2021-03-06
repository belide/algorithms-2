package test.alg.math;

import alg.math.MathUtils;
import alg.math.finance.StockUtils;
import alg.misc.MiscUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/2/15
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class MathTest {

    public static void main (String [] args) {

        int n = 1000;
        int ctr = 0;
        // Hmm supposedly this is O(n^2) as opposed to O(n^3)... not really clear why
//        for (int i = 0; i < n - 2; ++i) {
//            for (int j = i + 1; j < n - 1; ++j) {
//                for (int k = j + 1; k < n; ++k) {
//                    ctr++;
//                }
//            }
//        }

        ctr = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {

                    ctr++;

            }
        }

        System.out.println("Ctr: " + ctr + " N^2: " + (n*n) + " N^3: " + (n*n*n));

        int compare = MiscUtils.compareVersions("1", "1.1");
        compare = MiscUtils.compareVersions("2", "1");
        compare = MiscUtils.compareVersions("2.3", "1.4");
        compare = MiscUtils.compareVersions("0.1", "1.2");
        compare = MiscUtils.compareVersions("1.5", "1.5");
        compare = MiscUtils.compareVersions("1.0.5", "1.1.5");

        int val = MathUtils.atoi("-14");
        int val2 = MathUtils.atoi("14555");
        int val3 = MathUtils.atoi("6");
        int val4 = MathUtils.atoi("-2147483648");

        int peak1 = StockUtils.findPeakElementIdx(new int[]{1, 2});
        int peak2 = StockUtils.findPeakElementIdx(new int[]{2, 1});
        int peak3 = StockUtils.findPeakElementIdx(new int[]{1, 2, 0});
        int peak4 = StockUtils.findPeakElementIdx(new int[]{1, 1, 0});
        int peak5 = StockUtils.findPeakElementIdx(new int[]{1});
        int peak6 = StockUtils.findPeakElementIdx(new int[]{});
        int peak7 = StockUtils.findPeakElementIdx(new int[]{1, 1, 2, 3});


        List<List<Integer>> pascals = MathUtils.pascalsTriangle(5);

        long value = MathUtils.nthCatalan(1);
        System.out.println(value);
        value = MathUtils.nthCatalan(3);
        System.out.println(value);
        value = MathUtils.nthCatalan(5);
        System.out.println(value);
        value = MathUtils.nthCatalan(10);
        System.out.println(value);


    }
}
