package com.gmail.pkr4mer.boole.parser;


import com.gmail.pkr4mer.boole.exception.BooleException;
import com.gmail.pkr4mer.boole.exception.InvalidBooleanLogicException;
import com.gmail.pkr4mer.boole.plugin.session.Clipboard;
import com.gmail.pkr4mer.boole.region.BooleRegion;
import com.gmail.pkr4mer.boole.region.DifferenceWrapper;
import com.gmail.pkr4mer.boole.region.IntersectionWrapper;
import com.gmail.pkr4mer.boole.region.UnionWrapper;

/**
 * Created by Peter on 19-Nov-16.
 */
public class BooleParser {
    private BooleParser() {

    }

    /**
     * Parses boolean logic into a BooleRegion.
     * @param logic the boolean logic to work with.
     * @param context the {@link Clipboard} which IDs in the logic refer to.
     * @return a {@link BooleRegion} construct.
     * @throws BooleException: These are meant to transfer errors out of the parse algorithm back to the user.
     * DO NOT CATCH SILENTLY.
     */
    public synchronized static BooleRegion parse(String logic, Clipboard context) {
        BooleRegion region;
        // Strip all spaces. Spaces are bad, mkay?
        logic = logic.replace(" ", "");
        // Explode that shit
        char[] chars = logic.toCharArray();
        // Find the operator
        int operatorPosition = findOperator(chars);
        // If none was found, check if the entire thing exists as an ID in this context.
        if (operatorPosition < 0) {
            region = context.getRegion(logic);
        } else if (operatorPosition < 1) {
            // An operator position of 0 is obviously invalid boolean logic.
            throw new InvalidBooleanLogicException(logic);
        } else {
            // All is well in the land of boolean logic. Split into two parts.
            char[] partA = new char[operatorPosition];
            System.arraycopy(chars, 0, partA, 0, partA.length);
            char[] partB = new char[chars.length - operatorPosition - 1];
            System.arraycopy(chars, operatorPosition + 1, partB, 0, partB.length);
            String a = new String(partA);
            String b = new String(partB);
            System.out.println("ID'd a: '" + a + "'");
            System.out.println("ID'd b: '" + b + "'");
            // Strip redundant brackets.
            while (a.charAt(0) == '(' && a.charAt(a.length() - 1) == ')') {
                a = a.substring(1, a.length() - 1);
            }
            while (b.charAt(0) == '(' && b.charAt(b.length() - 1) == ')') {
                b = b.substring(1, b.length() - 1);
            }
            // The operator is found in between both parts.
            char operator = chars[operatorPosition];
            // Send both sides down to another parser, then apply the operation.
            BooleRegion aRegion = parse(a, context);
            BooleRegion bRegion = parse(b, context);
            region = applyOperation(aRegion, operator, bRegion);
        }
        // We're alive, return the construct.
        return region;
    }
    
    /**
     * Applies one of three boolean operations to the given {@link BooleRegion}s.
     * @param regionA the first region
     * @param operation the operator. Either '*', '+' or '\'.
     * @param regionB the second region
     * @return the resulting BooleRegion, or null if the operator is unknown.
     */
    private static BooleRegion applyOperation(BooleRegion regionA, char operation, BooleRegion regionB) {
        BooleRegion region = null;
        switch (operation) {
            case '*': {
                region = new IntersectionWrapper(regionA, regionB);
                break;
            }
            case '+': {
                region = new UnionWrapper(regionA, regionB);
                break;
            }
            case '\\': {
                region = new DifferenceWrapper(regionA, regionB);
                break;
            }
        }
        return region;
    }
    
    /**
     * Finds the top-level boolean operator in a sequence of characters.
     * @param chars the Array of characters to work with
     * @return the index of the top-level operator, or -1 if none found.
     */
    private static int findOperator(char[] chars) {
        int level = 0;
        if (chars[0] == '(') {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '(') {
                    level++;
                } else if (chars[i] == ')') {
                    level--;
                }
                if (level == 0) {
                    switch (chars[i]) {
                        case '*':
                            return i;
                        case '\\':
                            return i;
                        case '+':
                            return i;
                    }
                }
            }
        } else {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '*' || chars[i] == '\\' || chars[i] == '+') {
                    return i;
                }
            }
        }
        return -1;
    }
}
