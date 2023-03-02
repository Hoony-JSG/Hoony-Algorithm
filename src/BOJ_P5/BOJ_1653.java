package BOJ_P5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BOJ_1653 {
	static int N;
	static int K;
	static int[] Weights;
	static int DefaultVis;
	static ArrayList<Integer>[] SortedSet;
	static TreeSet<Integer> AbleSum;
	static PriorityQueue<Long> Results;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		N = Integer.parseInt(read.readLine());
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		init();
		for (int i = 1; i <= N; i++) {
			int w = Integer.parseInt(tokens.nextToken());
			Weights[i] = w;
			DefaultVis |= 1 << w;
		}
		K = Integer.parseInt(read.readLine());
		write.write(solv() + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		Weights = new int[11];
		DefaultVis = 0;
		SortedSet = new ArrayList[500000];
		AbleSum = new TreeSet<>(); // ������ ���� ���
		Results = new PriorityQueue<>();
		Results.add(0L);
	}

	private static long solv() {
		setSortByWeights();
		getAbleCase();
		K = Math.min(K, Results.size() - 1);
		while (K-- > 0) {
			Results.poll();
		}
		return Results.peek();
	}

	private static void setSortByWeights() {
		for (int num = 1; num <= 98765; num++) {
			int tempsum = checkAbleSet(num);
			if (tempsum != -1) {
				if (SortedSet[tempsum] == null) {
					SortedSet[tempsum] = new ArrayList<>();
				}
				AbleSum.add(tempsum);
				SortedSet[tempsum].add(num);
			}
		}
	}

	private static int checkAbleSet(int num) { // �ش� ������ ���� �����ִ� �߷� ǥ�� �����Ѱ�
		int vis = 0;
		int pow = 0;
		int temptotal = 0;
		for (int i = 1; i <= 10000; i *= 10) {
			int tempnum = (num / i) % 10; // ���� ��
			pow++;
			if (tempnum == 0)
				continue;
			temptotal += tempnum * pow;
			if ((vis & 1 << tempnum) != 0)
				return -1; // �ߺ� ��� ��
			boolean has = false;
			for (int j = 0; j <= N; j++) { // �Է� �߿� ���Ե� ���ΰ�
				if (Weights[j] == tempnum) { // �ֳ�?
					has = true;
					vis |= 1 << tempnum;
				}
			}
			if (!has)
				return -1;
		}
		if (vis == DefaultVis) // ������ �߸� ��� �� �� ����� false
			return -1;
		return temptotal; // �ش� ���ÿ����� �� ����
	}

	private static void getAbleCase() {
		for (int totalweight : AbleSum) { // ��ϵ� ���Ե� �߿�����
			getComb(totalweight);
		}
	}

	private static void getComb(int totalweight) { // �ΰ��� �̾Ƽ� Ȯ��
		int size = SortedSet[totalweight].size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) { // �� ���� �̾Ƽ�
				int setA = SortedSet[totalweight].get(i);
				int setB = SortedSet[totalweight].get(j);
				if (checkAble(setA, setB)) { // �ߺ� �� ���� ���� �����Ѱ�?
					addResult(setA, setB); // ��ü �����ؼ� �߰�
				}
			}
		}
	}

	private static boolean checkAble(int setA, int setB) { // �ߺ� �� üũ
		int vis = 0;
		for (int i = 1; i <= 10000; i *= 10) {
			int tempA = (setA / i) % 10;
			int tempB = (setB / i) % 10;
			if (tempA != 0) {
				if ((vis & 1 << tempA) != 0)
					return false;
				vis |= 1 << tempA;
			}
			if (tempB != 0) {
				if ((vis & 1 << tempB) != 0)
					return false;
				vis |= 1 << tempB;
			}
		}
		return true;
	}

	private static void addResult(int A, int B) { // ���￡ ����
		long resultA = A;
		long resultB = B;
		for (int i = 1; i <= 10000; i *= 10) {
			resultA *= 10;
			resultA += (B / i) % 10;
			resultB *= 10;
			resultB += (A / i) % 10;
		}
		Results.add(resultA);
		Results.add(resultB);
	}

}
