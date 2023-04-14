package BOJ_G5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_21610_��������ͺ�ٶ��_G5 {
	static int N, M;
	static int[][] Map;
	static int[][] deltas;
	static Queue<int[]> MoveQ;
	static ArrayList<int[]> Clouds;
	static long[] Visited;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		init();
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(read.readLine());
			for (int j = 0; j < N; j++) {
				Map[i][j] = Integer.parseInt(tokens.nextToken());
			}
		}
		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(read.readLine());
			MoveQ.add(new int[] { Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()) });
		}
		write.write(solv() + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		Map = new int[N][N];
		MoveQ = new LinkedList<>();
		Clouds = new ArrayList<>();
		Visited = new long[N];
		deltas = new int[][] { {}, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 },
				{ 1, -1 } };
		Clouds.add(new int[] { N - 1, 0 });
		Clouds.add(new int[] { N - 1, 1 });
		Clouds.add(new int[] { N - 2, 0 });
		Clouds.add(new int[] { N - 2, 1 });
	}

	private static int solv() {
		while (!MoveQ.isEmpty()) {
			doMoveClouds(MoveQ.poll());
			doCopyWater();
			doMakeClouds();
		}
		return getTotalWater();
	}

	private static void doMoveClouds(int[] move) { // ���� �̵��ϱ�
		int size = Clouds.size();
		for (int i = 0; i < size; i++) {
			int nextR = (N + Clouds.get(i)[0] + (deltas[move[0]][0] * (move[1] % N))) % N; // �̵��� R
			int nextC = (N + Clouds.get(i)[1] + (deltas[move[0]][1] * (move[1] % N))) % N; // �̵��� C
			Map[nextR][nextC]++; // �� +1
			Visited[nextR] |= 1L << nextC; // ���� ������ ���
			Clouds.set(i, new int[] { nextR, nextC }); // �̵��� ������ ����
		}
	}

	private static void doCopyWater() { // �̵��� ������ �밢�� Ž��
		for (int[] cloud : Clouds) {
			for (int index = 2; index <= 8; index += 2) {
				int diagR = cloud[0] + deltas[index][0];
				int diagC = cloud[1] + deltas[index][1];
				if (isIn(diagR, diagC) && Map[diagR][diagC] != 0) {
					Map[cloud[0]][cloud[1]]++;
				}
			}
		}
		Clouds.clear();
	}

	private static void doMakeClouds() { // 2 �̻��� �������� ���� ����
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (Map[r][c] >= 2 && (Visited[r] & (1L << c)) == 0) { // ���� ���� ������ ����
					Map[r][c] -= 2;
					Clouds.add(new int[] { r, c });
				}
			}
		}
		Visited = new long[N];
	}

	private static int getTotalWater() {
		int count = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				count += Map[r][c];
			}
		}
		return count;
	}

	private static boolean isIn(int r, int c) {
		return r >= 0 && c >= 0 && r < N && c < N;
	}

}
