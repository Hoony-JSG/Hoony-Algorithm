package BOJ_P5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BOJ_2967_�׸����θ�����_Imple { // ��Ʈ����ŷȰ���غ���
	static int R, C;
	static Queue<int[]> Edges;
	static int[][] deltas = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	static long[][] Map;
	static int[] ResultA, ResultB;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		init();
		for (int i = 0; i < R; i++) {
			String templine = read.readLine();
			for (int j = 0; j < C; j++) {
				if (templine.charAt(j) == 'x') {
					if (j <= 50)
						Map[i][0] |= (1L << j);
					else
						Map[i][1] |= (1L << (j - 50));
				}
			}
		}
		solv();
		write.write((ResultA[0] + 1) + " " + (ResultA[1] + 1) + " " + ResultA[2] + "\n");
		write.write((ResultB[0] + 1) + " " + (ResultB[1] + 1) + " " + ResultB[2] + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		Map = new long[R][2];
		Edges = new LinkedList<>();
		ResultA = new int[] { -1, -1, 0 };
		ResultB = new int[] { -1, -1, 0 };
	}

	private static void solv() {
		getEdges();
		int size = Edges.size();
		while (size-- > 0) {
			getLen(Edges.poll());
		}
		// ���̸� ���� ������ ��ǥ���� ����
		getTopLeftEdge();
		// ���� ���� �� �������鸸 ����
		getResults();
		if (ResultB[0] == -1)
			ResultB = ResultA.clone();
	}

	private static void getEdges() { // �ܰ� �������� ������
		for (int i = 0; i < R; i++) {
			boolean before = false;
			boolean now = false;
			for (int j = 0; j < C; j++) {
				if (j <= 50) {
					if ((Map[i][0] & 1L << j) != 0)
						now = true;
					else
						now = false;
				} else {
					if ((Map[i][1] & 1L << (j - 50)) != 0)
						now = true;
					else
						now = false;
				}
				if ((now && (j == 0 || !before)) || (!now && before) || (now && j == C - 1)) {
					// (ù ���ۺκ� true / ���ο��� ù true) / (����� false ���� ���� true) / (���� �� ������ true)
					int tempi = i;
					int tempj = j;
					if (!now) {
						tempj--;
					}
					int emptycount = 0;
					for (int dir = 0; dir < 4; dir++) {
						int nextx = tempi + deltas[dir][0];
						int nexty = tempj + deltas[dir][1];
						if (nexty <= 50) {
							if (!isIn(nextx, nexty) || (Map[nextx][0] & 1L << nexty) == 0) {
								emptycount++;
							}
						} else {
							if (!isIn(nextx, nexty) || (Map[nextx][1] & 1L << (nexty - 50)) == 0) {
								emptycount++;
							}
						}
					}
					if (emptycount > 1)
						Edges.add(new int[] { tempi, tempj });
				}
				before = now;
			}
		}
	}

	private static boolean isIn(int x, int y) {
		return x >= 0 && y >= 0 && x < R && y < C;
	}

	private static void getLen(int[] edge) { // ���� �� ���������� ���簢�� �̷� �� �ִ� ����� ���� ���ϱ�
		int MinLen = Integer.MAX_VALUE;
		int NoLenCount = 0;
		for (int dir = 0; dir < 4; dir++) {
			int x = edge[0];
			int y = edge[1];
			int TempLen = 0;
			while (true) {
				if (isIn(x, y)) {
					if (y <= 50) {
						if ((Map[x][0] & 1L << y) != 0) {
							x += deltas[dir][0];
							y += deltas[dir][1];
						} else { // ���� ���� �ƴϸ� break
							break;
						}
					} else {
						if ((Map[x][1] & 1L << (y - 50)) != 0) {
							x += deltas[dir][0];
							y += deltas[dir][1];
						} else { // ���� ���� �ƴϸ� break
							break;
						}
					}
				} // ���� ���̰� ���� ������ ������ ����
				else {
					break;
				} // ���� ���̸� break
				TempLen++;
			}
			if (TempLen == 1) {
				NoLenCount++;
				continue;
			}
			MinLen = Math.min(MinLen, TempLen);
		}
		if (NoLenCount > 2) { // ���̰� 1�� ���� 3���� �̻� => 1¥�� ���簢��
			MinLen = 1;
		}
		Edges.add(new int[] { edge[0], edge[1], MinLen });
	}

	private static void getTopLeftEdge() { // ���� �� �������� �����ؼ� ������
		int size = Edges.size();
		while (size-- > 0) {
			boolean[] dircheck = new boolean[4];
			int[] temp = Edges.poll();
			int len = temp[2] - 1;
			for (int dir = 0; dir < 4; dir++) {
				int x = temp[0] + deltas[dir][0] * len;
				int y = temp[1] + deltas[dir][1] * len;
				if (isIn(x, y)) {
					if (y <= 50) {
						if ((Map[x][0] & 1L << y) != 0) {
							dircheck[dir] = true;
						}
					} else {
						if ((Map[x][1] & 1L << (y - 50)) != 0) {
							dircheck[dir] = true;
						}
					}
				}
				if (dircheck[0] && dircheck[3]) { // ���� ���� ã�� ����
					Edges.add(new int[] { temp[0] - len, temp[1] - len, len + 1 });
				} else if (dircheck[1] && dircheck[2]) { // �ϰ� �� ����
					Edges.add(new int[] { temp[0], temp[1], len + 1 });
				}
			}

		}
	}

	private static void getResults() { // �� ������ �и��ϱ�
		while (!Edges.isEmpty()) {
			int[] temp = Edges.poll();
			if (ResultA[0] == -1) {
				ResultA = temp.clone();
			} else {
				if (ResultA[0] != temp[0] || ResultA[1] != temp[1]) {
					ResultB = temp.clone();
					return;
				}
			}
		}
	}

}
