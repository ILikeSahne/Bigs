package bigintegers;

class Bigs {

    /*
    Bigs:
        Is able to store very large numbers, tends against infinity
        It does that by storing a number in an int array

        The numbers are stored as following:
            Example: 123
                [3, 2, 1]
            Example: 54321
                [1, 2, 3, 4, 5]
            Wrong Example:
                [3, 2, 1, 0, 0, 0] - no trailing zeroes allowed, the number 0 alone is allowed
            Wrong Example:
                [30, 20, 10] - every index is only allowed to store numbers from 0-9, negative ones also aren't allowed

        Instead of int array, I will always say Big to avoid confusion
     */

    public static int[] add(int[] a, int [] b) { // Adds 2 Bigs
        // Algorithm example 900 + 123:
        //   0 + 3 = 3 that's the first digit
        //   0 + 2 = 2 that's the second digit
        //   9 + 1 = 10, 10 is too large, so we take mod 10 of it, which is 0, that's the third digit
        //   The carry number is 1 so we increase the Big by 1 place and put 1 as the last digit
        //   Result: [3, 2, 0, 1] -> Output: 1023

        int lenA = a.length;
        int lenB = b.length;
        int max = Math.max(lenA, lenB); // Get the max Length of the 2 Bigs
        int[] n = new int[max]; // Create a new Big of the max size
        // If the numbers are something like 500 + 500 tho, we need to increase the size
        // 500 + 500 = 1000
        // Len 3 + Len 3 != Len 3
        int carry = 0;
        for(int i = 0; i < max; i++) { // Loop through the digits of the 2 Bigs
            int sum = carry; // Take the leftovers from the last iteration
            if(i < lenA) // We need to check if the index isn't out of range, for example when we have 0 and 100 as input
                sum += a[i]; // If it is still in range, add the current digit to the sum number
            if(i < lenB) // Same out of range check again
                sum += b[i]; // Same here just for b now
            n[i] = sum % 10; // Only take the last num of the sum numbers
            // Example 9 + 9 = 18: only put 8 in the spot, the 1 will be added in the next place as the "carry"
            // (because the 1 represents 10 and we don't allow numbers bigger than 9 to be stored, so we need to add it to the next place)
            carry = sum / 10; // Take the leftover and add it to the next place, is 1 in the example above (can either be 0 or 1)
        }
        if(carry != 0) { // If plus is not 0, that means we still have a leftover. That's the case I mentioned above 500 + 500 = 1000
            int[] newN = new int[max + 1]; // Create an array that is 1 larger
            for(int i = 0; i < max; i++) { // Copy the digits from the first array to the lager array
                newN[i] = n[i];
            }
            newN[max] = carry; // At the last index we put the carry
            // we could also write newN[max] = 1 cause the carry can either be 0 or 1, not more, since we know that carry isn't 0, it has to be 1
            return newN; // Return the new larger array
        }
        return n; // Since we don't have a carry, we just return the smaller array because it is already big enough
    }

    public static String asString(int[] n) { // Converts a Big to a String
        StringBuilder sb = new StringBuilder(); // Makes life easier
        // We have to loop in reverse when adding, because something like 123 is stored like this: [3, 2, 1], so basically in reverse
        for(int i = n.length - 1; i >= 0; i--) {
            sb.append(n[i]); // Add the current digit to the String
        }
        return sb.toString(); // Return the Big-String
    }

    public static void print(int[] n) { // Prints a Big to the Console
        System.out.println(asString(n));
    }

    public static int[] digit(int d) { // Creates a new Big with 1 digit, anything above 9 is not allowed that's why we use % 10 to get rid of larger numbers
        return new int[] { d % 10 };
    }

    public static int[] Null() { // Creates a new Big with the value 0
        return new int[] { 0 };
    }

    public static int[] One() { // Creates a new Big with the value 1
        return new int[] { 1 };
    }

    public static int[] fromInt(int n) { // Creates a new Big from an integer value
        // Algorithm example 123:
        //   len is 3
        //   n is 123
        //   123 % 10 = 3, first digit
        //   123 / 10 = 12
        //   12  % 10 = 2, second digit
        //   12  / 10 = 1
        //   1   % 10 = 1, third digit
        //   Result: [3, 2, 1] -> Output: 123
        int len = (int) Math.log10(n) + 1; // Find the len of the int, example 123: len is 3
        int[] newN = new int[len]; // Create a new Big with the len of the int as digits
        for(int i = 0; i < len; i++) { // Loop through all the digits
            newN[i] = n % 10; // Add the current first digit at the current pos digit
            n /= 10; // Divide by 10 so the next current second digit will become the first digit
        }
        return newN;
    }

    public static int mod10(int[] n) { // Gives back the Big modulo 10, which is always just the first digit
        return n[0];
    }

    public static int[] div10(int[] n) { // Divides a Big by 10
        if(n.length == 1) // If the length is 1, that means the Big only has 1 digit, so 0-9, that divided by 10 is always 0
            return Null(); // Return Zero
        int[] newN = new int[n.length - 1]; // Create a new Big that has 1 less digit
        // Shift the digits of the input Big by 1 place to the right, that divides by 10
        // Algorithm example: 123
        //   123 / 10 -> 12
        for(int i = 0; i < n.length - 1; i++) { // Shift to the right
            newN[i] = n[i + 1];
        }
        return newN;
    }

    public static int[] copy(int[] n) { // Creates a new Big from another Big
        int[] newN = new int[n.length]; // Create a Big with the same digit count
        for(int i = 0; i < n.length; i++) { // Copy the digits from the input Big to the output Big
            newN[i] = n[i];
        }
        return newN;
    }

    public static int[] times10(int[] n) { // Multiplies a Big by 10
        // Is the opposite of dividing by 10, this time we just shift it to the left
        int[] newN = new int[n.length + 1]; // Creates a new Big with 1 more digit than the input Big
        newN[0] = 0; // Set the first digit to 0
        // Algorithm example: 123
        // 123 * 10 -> 1230
        for(int i = 0; i < n.length; i++) { // Shift to the left
            newN[i + 1] = n[i];
        }
        return newN;
    }

    public static int[] times(int[] n, int d) { // Multiplies a Big by an int
        int[] newN = Null(); // Creates a new Big with the value Zero
        // Multiplying is basically repeated addition. So we just add the input Big to zero d times
        // Algorithm example 123 * 3:
        //   123 + 123 + 123 = 369
        for(int i = 0; i < d; i++) {
            newN = add(newN, n);
        }
        return newN;
    }

    public static int[] times(int[] a, int[] b) { // Multiplies a Big by another Big
        int[] newN = Null(); // Create a new Big with the value zero
        // We multiply the Bigs by splitting the second big into it's digits and then shifting the result to the left
        // Algorithm example 123 * 456 = 56088:
        //   sum = 0
        //   123 * 4 = 492
        //   sum = sum + 492 = 0 + 492 = 492
        //   sum = sum * 10 = 4920
        //   123 * 5 = 615
        //   sum = sum + 615 = 4920 + 615 = 5535
        //   sum = sum * 10 = 55350
        //   123 * 6 = 738
        //   sum = sum + 738 = 55350 + 738 = 56088
        for (int i = b.length - 1; i >= 0; i--) { // Loop in reverse because we need to start with the highest digit
            newN = times10(newN); // Shifts num to the left (times 10), because we start with num = 0, we can put that here without a check
            int[] add = times(a, b[i]); // Creates a new, temporary Big that stores the first Big multiplied by the current digit of the second Big
            newN = add(newN, add); // Adds the temporary to the total
        }
        return newN;
    }

    public static int[] square(int[] a) { // Squares a Big
        return times(a, a); // Is just Big * Big
    }

    public static int[] cubic(int[] a) { // Raises a Big to the power of 3 (cubic)
        return times(a, square(a)); // Is just Big * Big^2 -> Big * Big * Big
    }

    public static boolean less(int[] a, int[] b) { // Checks is Big a is less than Big b
        // Algorithm example 100 < 101:
        //   First Compare lengths, both are 3
        //   Compare highest digit, they are the same (1 == 1), we go to the next one
        //   Compare second highest digit, they are the same (0 == 0), we go to the next one
        //   Compare the third highest digit, 0 is smaller than 1, we return true
        int lenA = a.length;
        int lenB = b.length;
        if (lenA < lenB) // If the len of Big a is smaller than the len of Big b, we know that a is smaller than b
            return true;
        else if (lenA > lenB) // If the len of Big a is bigger than the len of Big b, we know that a is greater than b
            return false;
        // Lengths match
        for (int i = lenA - 1; i >= 0; i--) { // Loop in reverse to start at the highest digit
            if (a[i] < b[i]) // If the current digit of a is smaller than the current digit of b, we know that a is smaller than b
                return true;
            else if (a[i] > b[i]) // If the current digit of a is greater than the current digit of b, we know that a is greater than b
                return false;
        }
        return false; // If the 2 Bigs are the same, we return false
    }

    public static boolean equal(int[] a, int[] b) { // Checks if 2 Bigs are the same
        int lenA = a.length;
        int lenB = b.length;
        if(lenA != lenB) // Compare the length of the 2 Bigs, if they don't match, we know that they are not equal
            return false;
        for(int i = 0; i < lenA; i++) { // Loop through all the digits, we don't need to loop reverse because we just want to check if they are equal of not
            if(a[i] != b[i])
                return false; // We found a miss match, we return false
        }
        return true; // No miss match was found, return false
    }

    public static boolean ok(int[] n) { // Checks if the format of a Big is ok, look at the rules at the start of the file
        if(n.length == 0) // Empty array, not ok
            return false;
        if(n.length == 1) // Only 1 entry
            return (n[0] >= 0 && n[0] <= 9); // If the one digit is in the range from 0 to 9, it is ok, else not ok
        if(n[n.length - 1] == 0) // If the Big has trailing zeroes, it is not ok
            return false;
        for(int i = 0; i < n.length; i++) { // Loop through all the digits
            if(n[i] < 0 || n[i] > 9) // If the current digit is not in the range of 0 to 9, the Big is not ok
                return false;
        }
        return true; // No formatting issues found, Big is ok
    }

    public static void maxDigit(int[] n) { // Prints the most appearing, smallest digit
        int[] digits = new int[10]; // Create a new array that stores the amount of appearances of the digits from 0 to 9
        for(int i = 0; i < digits.length; i++) // Initialise all the amounts to 0
            digits[i] = 0;
        for(int i = 0; i < n.length; i++) { // Loop over the digits of the Big
            int d = n[i]; // Current digit
            digits[d]++; // Increases the amount counter of the current digit by 1
        }
        int max = 0;
        int digit = 0;
        for(int i = 0; i < digits.length; i++) { // Loops over the amount of digits
            int amount = digits[i]; // Current amount of digits
            if(amount > max) { // If the current amount of digits is bigger than the previously biggest amount, we set a new max amount and store the digit
                // If we put >=, we will instead find the most appearing, biggest digit
                max = amount;
                digit = i;
            }
        }
        System.out.println(digit); // Print the most appearing digit
    }

    public static void main(String[] s) {
        test();
        System.out.println("-----------------------------------------------------");
        myTest();
    }

    public static void test() {
        int[] a = One();

        for (int i=0; i<33222; ++i) {
            a = times(a, 2);
        }

        System.out.println("2^33222 hat " + a.length + " Stellen");
        print(a);
        System.out.println();

        int[] b = fromInt(13);
        int[] c = copy(b);

        for (int i=1; i<8978; ++i) {
            c=times(c, b);
        }

        System.out.println("13^8978 hat " + c.length + " Stellen");
        print(c);
        System.out.println();

        System.out.println(less(a, c)); // beantwortet die Frage aus der Aufgabenueberschrift

        maxDigit(a);
        maxDigit(c);
    }

    public static void myTest() {
        int[] Null = Null();
        int[] one = One();
        int[] nine = digit(9);
        int[] hundred = fromInt(100);

        System.out.println("Should be 0, is: " + asString(Null));
        System.out.println("Should be 1, is: " + asString(one));
        System.out.println("Should be 9, is: " + asString(nine));
        System.out.println("Should be 100, is: " + asString(hundred));

        int[] hundredPlusNine = add(hundred, nine);
        System.out.println("100 + 9 should be 109, is: " + asString(hundredPlusNine));

        int hundredNineMod10 = mod10(hundredPlusNine);
        System.out.println("109 % 10 should be 9, is: " + hundredNineMod10);

        int[] hundredNineDividedBy10 = div10(hundredPlusNine);
        System.out.println("109 / 10 should be 10, is: " + asString(hundredNineDividedBy10));

        int[] hundredPlusNineCopy = copy(hundredPlusNine);
        System.out.println("Copy of 109 should be 109, is: " + asString(hundredPlusNineCopy));

        int[] hundredPlusNineTimes10 = times10(hundredPlusNine);
        System.out.println("109 * 10 should be 1090, is: " + asString(hundredPlusNineTimes10));

        int[] hundredPlusNineTimes23 = times(hundredPlusNine, 23);
        System.out.println("109 * 23 should be 2507, is: " + asString(hundredPlusNineTimes23));

        int[] hundredPlusNineTimes2507 = times(hundredPlusNine, hundredPlusNineTimes23);
        System.out.println("109 * 2507 should be 273263, is: " + asString(hundredPlusNineTimes2507));

        int[] hundredNineSquared = square(hundredPlusNine);
        System.out.println("109^2 should be 11881, is: " + asString(hundredNineSquared));

        int[] hundredNineCubic = cubic(hundredPlusNine);
        System.out.println("109^3 should be 1295029, is: " + asString(hundredNineCubic));

        boolean nineLessThan100 = less(nine, hundred);
        System.out.println("9 < 100 should be true, is: " + nineLessThan100);

        boolean nineEqualsNine = equal(nine, nine);
        System.out.println("9 == 9 should be true, is: " + nineEqualsNine);

        boolean nineEquals100 = equal(nine, hundred);
        System.out.println("9 == 100 should be false, is: " + nineEquals100);

        boolean hundredPlusNineTimes2507Okay = ok(hundredPlusNineTimes2507);
        System.out.println("273263 should be ok(true), is: " + hundredPlusNineTimes2507Okay);

        int[] notOk = { 100, 200 };
        boolean notOkBool = ok(notOk);
        System.out.println("[100, 200] shouldn't be ok(false), is: " + notOkBool);

        int[] notOk2 = { 1, 0 };
        boolean notOkBool2 = ok(notOk2);
        System.out.println("01 shouldn't be ok(false), is: " + notOkBool2);

        int[] maxDigit = { 1, 2, 3, 4, 5, 5, 6, 6, 7, 8, 9};
        System.out.print("98766554321 max digit should be 5, is ");
        maxDigit(maxDigit);
    }
}