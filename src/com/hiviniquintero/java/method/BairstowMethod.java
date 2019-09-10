package com.hiviniquintero.java.method;

import com.hiviniquintero.java.helpers.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BairstowMethod {
    private ArrayList<Float> roots;

    private ArrayList<Complex> complexRoots;

    private int maxIter;

    public BairstowMethod(int maxIter) {
        this.maxIter = maxIter;
        roots = new ArrayList<>();
        complexRoots = new ArrayList<>();
    }

    public void findRoots(String polynomial, float r, float s, float deltaR, float deltaS) {
        HashMap<Integer, Float> map = Utils.formatPolynomial(polynomial);
        ArrayList<Integer> exponents = new ArrayList<>(map.keySet());
        Collections.sort(exponents);
        int grade = exponents.get(exponents.size() - 1);
        // Not the best practice but quick and dirty enough.
        int temp = this.maxIter;
        findRoots(map, this.roots, this.complexRoots, grade, r, s, deltaR, deltaS);
        this.maxIter = temp;
    }

    private void findRoots(HashMap<Integer, Float> map, ArrayList<Float> roots, ArrayList<Complex> complexRoots,
                                  int grade, float r, float s, float deltaR, float deltaS) {
        if (this.maxIter == 0) {
            return;
        }
        maxIter--;
        if (grade < 1) {
            return;
        }
        if (grade == 1 && getCoefficient(map, 1) != 0) {
            roots.add(((-getCoefficient(map, 0)) / (getCoefficient(map, 1))));
            return;
        }
        if (grade == 2) {
            float d = (float) ((getCoefficient(map, 1) * getCoefficient(map, 1)) - ((4.0)
                    * (getCoefficient(map, 2))) * (getCoefficient(map, 0)));
            if (d < 0) {
                complexRoots.add(new Complex(-getCoefficient(map, 1), "-", (Math.sqrt(Math.abs(d))) / (2.0 * getCoefficient(map, 2))));
                complexRoots.add(new Complex(-getCoefficient(map, 1), "+", (Math.sqrt(Math.abs(d))) / (2.0 * getCoefficient(map, 2))));
            } else {
                float x1 = (float) ((-getCoefficient(map, 1) - Math.sqrt(d)) / (2.0 * getCoefficient(map, 2)));
                float x2 = (float) ((-getCoefficient(map, 1) + Math.sqrt(d)) / (2.0 * getCoefficient(map, 2)));
                roots.add(x1);
                roots.add(x2);
            }
            return;
        }
        int n = grade + 1;
        float[] bs = new float[grade + 1];
        float[] cs = new float[grade + 1];
        bs[n - 1] = getCoefficient(map, grade);
        bs[n - 2] = getCoefficient(map, grade - 1) + r * bs[n-1];
        int i = n - 3;
        while (i >= 0) {
            bs[i] = getCoefficient(map, i) + r * bs[i + 1] + s * bs[i + 2];
            i--;
        }
        cs[n - 1] = bs[n - 1];
        cs[n - 2] = bs[n - 2] + r * cs[n - 1];
        i = n - 3;
        while (i >= 0) {
            cs[i] = bs[i] + r * cs[i + 1] + s * cs[i + 2];
            i--;
        }
        float din = 1.0f / ((cs[2] * cs[2]) - (cs[3] * cs[1]));
        float pastR = r;
        float pastS = s;
        r = r + din * (cs[2] * (-bs[1]) + ((-cs[3]) * (-bs[0])));
        s = s + din * ((-cs[1]) * (-bs[1]) + (cs[2] * (-bs[0])));
        if (Math.abs(r / (pastR+r)) * 100 < deltaR || Math.abs(s / (pastS+s)) * 100 < deltaS) {
            return;
        }
        if (Math.abs(bs[0]) > 0.001 || Math.abs(bs[1]) > 0.001) {
            findRoots(map, roots, complexRoots, grade, r, s, deltaR, deltaS);
            return;
        }
        float dis = (float) ((r * r) - ((4.0) * (-s)));
        if (dis < 0) {
            complexRoots.add(new Complex(r/2.0f, "-", Math.sqrt(Math.abs(dis)) / 2.0));
            complexRoots.add(new Complex(r/2.0f, "+", Math.sqrt(Math.abs(dis)) / 2.0));
        } else {
            float x1 = (float) ((r - Math.sqrt(dis)) / 2.0);
            float x2 = (float) ((r + Math.sqrt(dis)) / 2.0);
            roots.add(x1);
            roots.add(x2);
        }
        map.clear();
        for (int y = 0; y < bs.length - 2; y++) {
            map.put(y, bs[y + 2]);
        }
        findRoots(map, roots, complexRoots, grade - 2, r, s, deltaR, deltaS);
    }

    /**
     * Gets the value of the coefficient given the exponent.
     *
     * @param map      A map that has as key the value of the exponent, and value the coefficient.
     * @param exponent The key whose value of coefficient is searched for.
     * @return The value of the exponent if the key exists, else 0.
     */
    private float getCoefficient(HashMap<Integer, Float> map, int exponent) {
        return map.getOrDefault(exponent, 0f);
    }

    public ArrayList<String> getRoots() {
        ArrayList<String> roots = new ArrayList<>();
        for (float root : this.roots) {
            roots.add(root + "");
        }
        return roots;
    }

    public ArrayList<String> getComplexRoots() {
        ArrayList<String> complexRoots = new ArrayList<>();
        for (Complex root : this.complexRoots) {
            complexRoots.add(root.toString());
        }
        return complexRoots;
    }
}

class Complex {
    private float real;

    private String operation;

    private String imaginary;

    Complex(float real, String operation, double imaginary) {
        this.real = real;
        this.operation = operation;
        this.imaginary = imaginary + "i";
    }

    @Override
    public String toString() {
        return this.real + this.operation + this.imaginary;
    }
}
