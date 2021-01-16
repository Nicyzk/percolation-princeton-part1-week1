/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;

public class HelloWorld {
    public static void main(String[] args) {
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            System.out.println("row" + i);
            System.out.println("col" + j);
        }
    }
}
