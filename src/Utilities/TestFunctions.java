/*
 * Copyright (c) 2020. This project is created by Antonio Torres using MIT Open Source licence. You can use it just for educational purposes.
 *
 */

package Utilities;

import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public interface TestFunctions {
    static double sphereFun(Vector<Double> v) {
        double x, y;
        x = v.elementAt(0);
        y = v.elementAt(1);
        return x * x + y * y;
    }

    static double goldseinFun(Vector<Float> v) {
        double p, p1, x = v.elementAt(0), y = v.elementAt(1);
        p = (1 + Math.pow(x + y + 1, 2) * (19 - 14 * x + 3 * Math.pow(x, 2) - 14 * y + 6 * x * y + 3 * Math.pow(y, 2)));
        p1 = (30 + Math.pow(2 * x - 3 * y, 2) * (18 - 32 * x + 12 * Math.pow(x, 2) + 48 * y - 36 * x * y + 27 * Math.pow(y, 2)));
        return p * p1;
    }

    static double generateRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    static Vector<Double> componentWiseMult(Vector<Double> x, Vector<Double> y) {
        Vector<Double> z = new Vector<>();
        if (x.size() == y.size()) {
            for (int i = 0; i < x.size(); i++) z.add(x.elementAt(i) * y.elementAt(i));
        }
        return z;
    }

    static Vector<Double> vecSum(Vector<Double> x, Vector<Double> y) {
        Vector<Double> z = new Vector<>();
        if (x.size() == y.size()) {
            for (int i = 0; i < x.size(); i++) z.add(x.elementAt(i) + y.elementAt(i));
        }
        return z;
    }

    static Vector<Double> vecSubstract(Vector<Double> x, Vector<Double> y) {
        Vector<Double> z = new Vector<>();
        if (x.size() == y.size()) {
            for (int i = 0; i < x.size(); i++) z.add(x.elementAt(i) - y.elementAt(i));
        }
        return z;
    }

    static Vector<Double> U(float inicio, float fin, int D) {
        Vector<Double> w = new Vector<>();
        for (int i = 0; i < D; i++) w.add(generateRandomDouble(inicio, fin));
        return w;
    }
}
