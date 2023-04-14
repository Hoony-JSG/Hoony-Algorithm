package BOJ_G5;

import java.util.*;
import java.io.*;

public class BOJ_21608_����ʵ��б�_G5 {
	static int N;
	static ArrayList<Student> StudentList;
	static HashMap<Integer, Integer> StudentLocs; // �л����� ��ġ
	static int[][] StudentMap; // ��ġ�� �л� ��ȣ
	static int[][] Deltas;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		N = Integer.parseInt(tokens.nextToken());
		init();
		for (int i = 0; i < N * N; i++) {
			tokens = new StringTokenizer(read.readLine());
			StudentList.add(new Student(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
					Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
					Integer.parseInt(tokens.nextToken())));
		}
		write.write(solv() + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		StudentList = new ArrayList<>();
		StudentLocs = new HashMap<>();
		StudentMap = new int[N][N];
		Deltas = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
	}

	private static long solv() {
		getSettableLoc();
		return getCountPoints();
	}

	private static void getSettableLoc() {
		for (Student st : StudentList) {
			HashMap<Integer, Integer> tempSettableLocs = new HashMap<>(); // ��ġ�� ���� like ��
			System.out.println("--------------" + st.num + "--------------");
			for (int like : st.likes) {
				if (StudentLocs.containsKey(like)) {
					int loc = StudentLocs.get(like);
					for (int dir = 0; dir < 4; dir++) {
						int nextR = loc / 100 + Deltas[dir][0];
						int nextC = loc % 100 + Deltas[dir][1];
						int nextRC = nextR * 100 + nextC;
						// �����ϴ� �л� ������ �����
						if (isIn(nextR, nextC) && !StudentLocs.containsValue(nextRC)) {
							if (tempSettableLocs.containsKey(nextRC)) { // �̹� �߰��� �ĺ���
								tempSettableLocs.replace(nextRC, tempSettableLocs.get(nextRC));
							} else {
								tempSettableLocs.put(nextRC, 1);
							}
						}
					}
				}
			}
			int settedLoc = 0;
			if (tempSettableLocs.isEmpty()) { // �ĺ����� ����
				System.out.println("�ĺ��� ����");
				int max = 0;
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (StudentMap[i][j] == 0) { // ����� �߰�
							int empty = 0;
							// �� ������ �����?
							for (int dir = 0; dir < 4; dir++) {
								int nextI = i + Deltas[dir][0];
								int nextJ = j + Deltas[dir][1];
								if (isIn(nextI, nextJ) && StudentMap[nextI][nextJ] == 0)
									empty++;
							}
							if (max < empty) {
								settedLoc = i * 100 * j;
								max = empty;
							}
						}
					}
				}
			} else { // �ĺ��� ����
				System.out.println("�ĺ��� OO");
				int maxCount = 0;
				for (int loc : tempSettableLocs.keySet()) {
					int count = tempSettableLocs.get(loc);
					if (count > maxCount) { // �ִ� ī��Ʈ ��ġ ã��
						settedLoc = loc;
						maxCount = count;
					}else if(count == maxCount) {
						
					}
				}
			}
			StudentLocs.put(st.num, settedLoc);
			StudentMap[settedLoc / 100][settedLoc % 100] = st.num;
			printMap();
		}
	}

	private static void printMap() {
		System.out.println("MAP +===================");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(StudentMap[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static long getCountPoints() {
		long result = 0;
		for (Student st : StudentList) {
			int loc = StudentLocs.get(st.num);
			int r = loc / 100;
			int c = loc % 100;
			int count = 0;
			for (int dir = 0; dir < 4; dir++) {
				int nextR = r + Deltas[dir][0];
				int nextC = c + Deltas[dir][1];
				if (isIn(nextR, nextC)) {
					int sideStudent = StudentMap[nextR][nextC];
					for (int like : st.likes) {
						count += sideStudent == like ? 1 : 0;
					}
					result += Math.pow(10, count);
				}
			}
		}
		return result;
	}

	private static boolean isIn(int r, int c) {
		return r >= 0 && c >= 0 && r < N && c < N;
	}

	private static class Student {
		int num;
		int[] likes;

		public Student(int num, int a, int b, int c, int d) {
			this.num = num;
			likes = new int[4];
			likes[0] = a;
			likes[1] = b;
			likes[2] = c;
			likes[3] = d;
		}
	}
}
