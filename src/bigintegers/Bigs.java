package bigintegers;

import java.math.BigInteger;
import java.util.HashMap;

class Bigs {

    public static int[] add(int[] a, int [] b) {
        int lenA = a.length;
        int lenB = b.length;
        int max = Math.max(lenA, lenB);
        int[] n = new int[max];
        int plus = 0;
        for(int i = 0; i < max; i++) {
            int ges = plus;
            if(i < lenA)
                ges += a[i];
            if(i < lenB)
                ges += b[i];
            n[i] = ges % 10;
            plus = ges / 10;
        }
        if(plus != 0) {
            int[] newN = new int[max + 1];
            for(int i = 0; i < max; i++) {
                newN[i] = n[i];
            }
            newN[max] = plus;
            return newN;
        }
        return n;
    }

    public static void print(int[] n) {
        StringBuilder sb = new StringBuilder();
        for(int i = n.length - 1; i >= 0; i--) {
            sb.append(n[i]);
        }
        System.out.println(sb.toString());
    }

    public static int[] digit(int d) {
        return new int[] { d % 10 };
    }

    public static int[] Null() {
        return new int[] { 0 };
    }

    public static int[] One() {
        return new int[] { 1 };
    }

    public static int mod10(int[] n) {
        return n[0];
    }

    public static int[] div10(int[] n) {
        if(n.length == 1)
            return Null();
        int[] newN = new int[n.length - 1];
        for(int i = 0; i < n.length - 1; i++) {
            newN[i] = n[i + 1];
        }
        return newN;
    }

    public static int[] fromInt(int n) {
        int len = (int) Math.log10(n) + 1;
        int[] newN = new int[len];
        for(int i = 0; i < len; i++) {
            newN[i] = n % 10;
            n /= 10;
        }
        return newN;
    }

    public static int[] copy(int[] n) {
        int[] newN = new int[n.length];
        for(int i = 0; i < n.length; i++) {
            newN[i] = n[i];
        }
        return newN;
    }

    public static int[] times(int[] n, int d) {
        int[] newN = copy(n);
        for(int i = 0; i < d - 1; i++) {
            newN = add(newN, n);
        }
        return newN;
    }

    public static int[] times10(int[] n) {
        int[] newN = new int[n.length + 1];
        newN[0] = 0;
        for(int i = 0; i < n.length; i++) {
            newN[i + 1] = n[i];
        }
        return newN;
    }

    public static int[] times(int[] a, int[] b) {
        int[] newN = Null();
        for (int i = 0; i < b.length; i++) {
            int[] add = times(a, b[i]);
            for (int j = 0; j < i; j++) {
                add = times10(add);
            }
            newN = add(newN, add);
        }
        return newN;
    }

    public static int[] square(int[] a) {
        return times(a, a);
    }

    public static int[] cubic(int[] a) {
        int len = a.length;
        return times(a, square(a));
    }

    public static boolean less(int[] a, int[] b) {
        int lenA = a.length;
        int lenB = b.length;
        if (lenA < lenB)
            return true;
        else if (lenA > lenB)
            return false;
        for (int i = lenA - 1; i >= 0; i--) {
            if (a[i] < b[i])
                return true;
            else if (a[i] > b[i])
                return false;
        }
        return false;
    }

    public static boolean equal(int[] a, int[] b) {
        int lenA = a.length;
        int lenB = b.length;
        if(lenA != lenB)
            return false;
        for(int i = 0; i < lenA; i++) {
            if(a[i] != b[i])
                return false;
        }
        return true;
    }

    public static boolean ok(int[] n) {
        if(n.length == 0)
            return false;
        if(n.length == 1)
            return (n[0] >= 0 && n[0] <= 9);
        if(n[n.length - 1] == 0)
            return false;
        for(int i = 0; i < n.length; i++) {
            if(n[i] == 0)
                return false;
            if(n[i] < 0 || n[i] > 9)
                return false;
        }
        return true;
    }

    public static void maxDigit(int[] n) {
        HashMap<Integer, Integer> digits = new HashMap<>();
        for(int i = 0; i < n.length; i++) {
            int d = n[i];
            if(!digits.containsKey(d))
                digits.put(d, 1);
            else
                digits.put(d, digits.get(d) + 1);
        }
        int max = 0;
        int digit = 0;
        for(int key : digits.keySet()) {
            int amount = digits.get(key);
            if(amount > max) {
                max = amount;
                digit = key;
            }
        }
        System.out.println(digit);
    }

    public static void main(String[] s) {
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
}