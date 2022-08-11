package day0810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class BJ1010 {
	static int N;
	static int M;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(read.readLine());
		for(int test = 1; test <= t; test++) {
			StringTokenizer tokens = new StringTokenizer(read.readLine());
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			if(N!=M) {
				N = Math.min(N, M-N);
			}
			
			if(N == 0) {
				System.out.println(0);
			}else {
				long temp = 1;
				for(int i = 0; i < N ; i++) {
					temp *= 1.0*(M-i)/(i+1);
				}
				BigInteger Bigone = new BigInteger(Long.toString(temp));
				System.out.println(Bigone);
			}
		}	
	}
}