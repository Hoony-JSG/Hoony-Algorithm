package BOJ_G5;

import java.util.*;
import java.io.*;

public class BOJ_1756_���ڱ���_G5 {

	static int D, N;
	static Stack<Integer> Braizer;
	static Queue<Integer> Doughs;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		D = Integer.parseInt(tokens.nextToken());
		N = Integer.parseInt(tokens.nextToken());
		init();
		tokens = new StringTokenizer(read.readLine());
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < D; i++) {
			int diameter = Integer.parseInt(tokens.nextToken());
			if (min > diameter) {
				min = diameter;
			}
			Braizer.add(min);
		}
		tokens = new StringTokenizer(read.readLine());
		for (int i = 0; i < N; i++) {
			Doughs.add(Integer.parseInt(tokens.nextToken()));
		}
		write.write(solv() + "\n");
		write.close();
		read.close();

	}

	private static void init() {
		Braizer = new Stack<>();
		Doughs = new LinkedList<>();
	}

	private static int solv() {
		int result = 0;
		putDoughs();
		if (Braizer.isEmpty()) { // ȭ���� �� ä����
			if (Doughs.isEmpty()) {
				result = 1; // ���ڵ� �� ��
			} else
				result = 0;
		} else // ȭ���� �� ä����
			result = Braizer.size();
		return result;
	}

	private static void putDoughs() { // false : �Ա����� �� ���ְ� ���� / true : �ϴ� ��
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				Map[i][j] = Map[j][N-1-i];
			}
		}
		
		while (!Doughs.isEmpty()) {
			if (Braizer.isEmpty())
				return;
			int dough = Doughs.poll();
			while (!Braizer.isEmpty()) {
				int diameter = Braizer.peek();
				if (dough > diameter) { // ��ġ �Ұ���
					Braizer.pop();
					continue;
				}
				break;
			}
		}
		return;
	}

}
