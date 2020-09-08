package com.xuao.alipay;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = br.readLine()) != null) {
            if (str.equals("")) continue;
            int n = Integer.parseInt(str);
            int[] heights = new int[n];
            int[] counts = new int[n];
            String[] str_heights = br.readLine().split(" ");
            // 当仅有一个人时，其自己组成一个合唱队，出列0人
            if (n <= 1) System.out.println(0);
            else {
            	for (int i = 0; i < str_heights.length; i++) {
					heights[i] = Integer.parseInt(str_heights[i]);
				}
            	
            	for (int i = 0; i < heights.length; i++) {
            		int count = 1;
            		int height = heights[i];
					for (int j = i+1; j < heights.length; j++) {
						if (heights[j] >= height) {
							height = heights[j];
							count++;
						}
						
					}
					counts[i] = count;
				}
            	Arrays.sort(counts);
            	System.out.println(n-counts[counts.length-1]);
            	
            }
        }
    }
}