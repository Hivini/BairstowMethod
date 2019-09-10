package com.hiviniquintero.java.helpers;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    /** Maps each exponent with it's coefficient.
     *
     * @param polynomial The one variable and correctly written polynomial expression. E.g. x^2-x^1+2
     * @return A map that has as key the exponents and the values coefficients.
     */
    public static HashMap<Integer, Float> formatPolynomial(String polynomial) {
        String[] elements = polynomial.split(" ");
        HashMap<Integer, Float> expressions = new HashMap<>();
        for (String element : elements) {
            System.out.println(element);
            int[] processedElement = processElement(element);
            expressions.put(processedElement[1], (float)processedElement[0]);
        }
        return expressions;
    }

    /** Process the exponent and coefficient of a polynomial element.
     *
     * @param element The polynomial element. E.g. -x^2
     * @return An array of two elements. The first defines de coefficient, and the second the exponent.
     */
    private static int[] processElement(String element) {
        int[] result = new int[2];
        if (!element.contains("^")) {
            result[0] = Integer.parseInt(element);
            // By default the arrays initialize as 0, so the exponent is zero.
            return result;
        }
        boolean isNegative = false;
        int i = 0;
        if (element.charAt(i) == '+') {
            i++;
        } else if (element.charAt(i) == '-') {
            i++;
            isNegative = true;
        }
        StringBuilder sb = new StringBuilder();
        char currentChar = element.charAt(i);
        while(Character.isDigit(currentChar)) {
            sb.append(currentChar);
            i++;
            currentChar = element.charAt(i);
        }
        if (sb.length() == 0) {
            sb.append(1);
        }
        // Avoid the variable character and the ^ character.
        i += 2;
        // Add to the result the coefficient.
        result[0] = Integer.parseInt(sb.toString()) * (isNegative ? -1 : 1);
        // Add to the result the exponent.
        result[1] = Integer.parseInt(element.substring(i));
        return result;
    }
}
