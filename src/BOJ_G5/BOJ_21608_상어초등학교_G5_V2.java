package BOJ_G5;

import java.util.*;
import java.io.*;

public class BOJ_21608_����ʵ��б�_G5_V2 {
	static int N;
	static Queue<Integer> StudentOrders;
	static int[][] StudentsInfos;
	static int[][][] Map; // [0] : ���õ� ��� , [1] : ī��Ʈ ����Ʈ
	static int[][] Deltas;
	static long Result;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		N = Integer.parseInt(read.readLine());
		init();
		for (int i = 0; i < N * N; i++) {
			StringTokenizer tokens = new StringTokenizer(read.readLine());
			int num = Integer.parseInt(tokens.nextToken());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			int d = Integer.parseInt(tokens.nextToken());
			StudentOrders.add(num);
			StudentsInfos[num] = new int[] { a, b, c, d, -1 };
		}
		solv();
		write.write(Result + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		StudentsInfos = new int[N * N + 1][5];
		Map = new int[N][N][2];
		StudentOrders = new LinkedList<>();
		Deltas = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		Result = 0;
	}

	private static void solv() {
		while (!StudentOrders.isEmpty()) {
			int st = StudentOrders.poll();
			addSideLikes(st);
			getMaxCountPointLoc(st);
		}
		getTotalCount();
	}

	// st�� ��ȣ�ϴ� �л����� ��ġ�� ���� �� ���� �� ���� �л��� ���� ���� ���� ����ϱ� (Ư�� ������ ���� ��ȣ �л� �� ���ϱ�)
	private static void addSideLikes(int st) {
		for (int i = 0; i < 4; i++) {
			int like = StudentsInfos[st][i];
			if (StudentsInfos[like][4] != -1) { // ��ȣ �л� �� �̹� ��ġ�� �л��̶��
				int likesLoc = StudentsInfos[like][4]; // �ش� �л��� ��ġ (intȭ)
				for (int dir = 0; dir < 4; dir++) {
					int nextR = likesLoc / 100 + Deltas[dir][0];
					int nextC = likesLoc % 100 + Deltas[dir][1];
					if (isIn(nextR, nextC) && Map[nextR][nextC][0] == 0) { // �ش� �л� ���� ������ �� �ڸ����
						Map[nextR][nextC][1]++;
					}
				}
			}
		} // ��� ��ȣ �л��� ���� ������ ���� �� ���� �� ���
	}

	// ��� �ʿ��� ���ϴ� ������ �ڸ� ã��
	private static void getMaxCountPointLoc(int st) {
		int[] maxCount = new int[] { -1, -1 };
		int loc = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (Map[i][j][0] == 0) { // ���� �� �ִ� �� ��
					if (Map[i][j][1] >= maxCount[0]) { // �ش� �ڸ��� ���� ���� ��ȣ �л� ��
						int emptyCount = 0; // �ֺ� �� ���� emptycount
						for (int dir = 0; dir < 4; dir++) {
							int nextI = i + Deltas[dir][0];
							int nextJ = j + Deltas[dir][1];
							if (isIn(nextI, nextJ) && Map[nextI][nextJ][0] == 0) {
								emptyCount++;
							}
						}
						if (Map[i][j][1] > maxCount[0]) { // ��ȣ �л� ���� ���� ���� �����̶��
							maxCount = new int[] { Map[i][j][1], emptyCount };
							loc = i * 100 + j;
						} else if (Map[i][j][1] == maxCount[0]) { // ���� �����
							if (emptyCount > maxCount[1]) { // ������� ���� ����
								maxCount = new int[] { Map[i][j][1], emptyCount };
								loc = i * 100 + j;
							}
						}
					}
					if (Map[i][j][1] != 0) { // �� ��ȣ �л� �� �ʱ�ȭ
						Map[i][j][1] = 0;
					}
				}
			}
		}
		Map[loc / 100][loc % 100][0] = st;
		StudentsInfos[st][4] = loc;
	}

	// ���ǿ� �´� ���� ī��Ʈ
	private static void getTotalCount() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int st = Map[i][j][0];
				int countLikes = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nextI = i + Deltas[dir][0];
					int nextJ = j + Deltas[dir][1];
					if (isIn(nextI, nextJ)) {
						for (int like = 0; like < 4; like++) {
							if (Map[nextI][nextJ][0] == StudentsInfos[st][like]) {
								countLikes++;
							}
						}
					}
				}
				if (countLikes != 0)
					Result += Math.pow(10, countLikes - 1);
			}
		}
	}

	private static boolean isIn(int r, int c) {
		return r >= 0 && c >= 0 && r < N && c < N;
	}
}
