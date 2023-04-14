package BOJ_G2;

import java.util.*;
import java.io.*;

public class BOJ_21609_������б�_G2 {
	static int N, M;
	static int[][] Map;
	static int[][] deltas;
	static int[] Visited;
	static PriorityQueue<Group> AllGroups;
	static long Result;

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
		solv();
		write.write(Result + "\n");
		write.close();
		read.close();
	}

	// �ʱ�ȭ
	private static void init() {
		Map = new int[N][N];
		deltas = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
		Visited = new int[N];
		AllGroups = new PriorityQueue<>();
		Result = 0;
	}

	// Ǯ��
	private static void solv() {
		while (true) {
			doSearchBiggestGroup(); // �׷�ȭ �� ���ǿ� �´� �׷� ã��
			if (AllGroups.isEmpty()) // ���̻� �׷��� ���� �� ����
				return;
			doRemoveGroup();
			addGravity();
			doRotateSquare();
			addGravity();
		}
	}

	// �׷�ȭ & ���ǿ� �°� �׷� ����
	private static void doSearchBiggestGroup() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (Map[i][j] > 0 && (Visited[i] & (1 << j)) == 0) { // �Ϲ� ��� �ϳ� �߰�
					doGatherGroup(i, j);
				}
			}
		}
	}

	// �Ϲ� ��� �ϳ��� �߽����� BFS Ž��
	private static void doGatherGroup(int i, int j) {
		Queue<int[]> BFSQ = new LinkedList<>();
		BFSQ.add(new int[] { i, j });
		int color = Map[i][j];
		Group G = new Group(i, j);

		// �Ϲ� ����� globally �ߺ� �Ұ�, ������ ����� globally �ߺ� ���� & locally �ߺ� �Ұ�
		Visited[i] |= 1 << j; // �Ϲ� ��� �湮(global)
		int[] visitedRainbow = new int[N]; // ������ ��� �湮(local)

		while (!BFSQ.isEmpty()) {
			int[] loc = BFSQ.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nextI = loc[0] + deltas[dir][0];
				int nextJ = loc[1] + deltas[dir][1];

				// ���� ���̰ų� / �� ����̰ų� / ���� �׷쿡�� �̹� �湮�� ������ ����̰ų� / �̹� �湮�� �Ϲ� ����̶��
				if (!isIn(nextI, nextJ) || Map[nextI][nextJ] <= -1 || (visitedRainbow[nextI] & (1 << nextJ)) != 0
						|| (Visited[nextI] & (1 << nextJ)) != 0)
					continue;

				// �߽� ��ϰ� �ٸ� �� ����̶��
				if (Map[nextI][nextJ] > 0 && Map[nextI][nextJ] != color)
					continue;

				// �湮ó�� ����
				if (Map[nextI][nextJ] == 0)
					visitedRainbow[nextI] |= 1 << nextJ;
				else
					Visited[nextI] |= 1 << nextJ;

				// BFS �߰� �� �׷�ȭ
				int[] nextLoc = new int[] { nextI, nextJ };
				BFSQ.add(nextLoc);
				G.blocks.add(nextLoc);

				// �׷��� ������ ��� �� ����
				if (Map[nextI][nextJ] == 0)
					G.rainbowCount++;

				// �׷� �� ��� ��
				G.size++;
			}
		}

		// ��� ���� 2�� �̻��� ���� �׷�ȭ
		if (G.size >= 2) {
			AllGroups.add(G);
		}
	}

	// ���ǿ� �´� �׷� �ϳ� ����
	private static void doRemoveGroup() {
		Group A = AllGroups.poll();

		// ���� ȹ��
		Result += Math.pow(A.size, 2);

		while (!A.blocks.isEmpty()) {
			int[] loc = A.blocks.poll();
			Map[loc[0]][loc[1]] = -2;
		}
		AllGroups.clear();

		// �湮 �ʱ�ȭ
		Visited = new int[N];
	}

	// �߷� �߰�
	private static void addGravity() {
		for (int j = 0; j < N; j++) {
			for (int i = N - 1; i >= 0; i--) {

				// �����(-2)�߽߰�
				if (Map[i][j] == -2) {

					// �ش� ��ġ�������� ���� Ž��
					for (int nextI = i - 1; nextI >= 0; nextI--) {

						// ������̸� ����
						if (Map[nextI][j] == -1) {
							i = nextI;
							break;
						}

						// �Ϲ� / ������ ��� �߽߰� ������
						else if (Map[nextI][j] >= 0) {
							Map[i][j] = Map[nextI][j];
							Map[nextI][j] = -2;
							break;
						}
					}
				}
			}
		}
	}

	// ȸ����Ű��
	private static void doRotateSquare() {
		int[][] tempMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tempMap[N - 1 - j][i] = Map[i][j];
			}
		}
		for (int i = 0; i < N; i++) {
			Map[i] = tempMap[i];
		}
	}

	private static class Group implements Comparable<Group> {
		int baseLoc;
		int size;
		int rainbowCount;
		Queue<int[]> blocks;

		public Group(int baseI, int baseJ) {
			this.baseLoc = baseI * 100 + baseJ;
			size = 1;
			rainbowCount = 0;
			this.blocks = new LinkedList<>();
			this.blocks.add(new int[] { baseI, baseJ });
		}

		// ���ǿ� �°� ����
		@Override
		public int compareTo(Group arg0) {
			if (this.size == arg0.size)
				if (this.rainbowCount == arg0.rainbowCount)
					return Integer.compare(arg0.baseLoc, this.baseLoc);
				else
					return Integer.compare(arg0.rainbowCount, this.rainbowCount);
			return Integer.compare(arg0.size, this.size);
		}

	}

	private static boolean isIn(int i, int j) {
		return i >= 0 && j >= 0 && i < N && j < N;
	}

}
