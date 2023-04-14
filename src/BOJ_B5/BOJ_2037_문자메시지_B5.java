package BOJ_B5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ_2037_���ڸ޽���_B5 {
	static int p, w;
	static StringTokenizer tokens;
	static long Result;
	static int[] KeyPad;
	static String allTexts;

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		tokens = new StringTokenizer(read.readLine());
		p = Integer.parseInt(tokens.nextToken());
		w = Integer.parseInt(tokens.nextToken());
		allTexts = read.readLine();
		tokens = new StringTokenizer(allTexts);
		init();
		solv();
		write.write(Result + "\n");
		write.close();
		read.close();
	}

	private static void init() {
		Result = 0;
		KeyPad = new int[] { 12, 22, 32, 13, 23, 33, 14, 24, 34, 15, 25, 35, 16, 26, 36, 17, 27, 37, 47, 18, 28, 38, 19,
				29, 39, 49 };
	}

	private static void solv() {
		addTimeCount();
	}

	private static void addTimeCount() {
		int beforeNum = -1;
		for (int i = 0; i < allTexts.length(); i++) {
			char presentText = allTexts.charAt(i);
			if (presentText == ' ') { // ������ p �߰�
				beforeNum = -1;
				Result += p;
			} else { // ���ڴ� ���� Ű�е� �� �߰�
				int presentNum = KeyPad[presentText - 'A'];
				Result += beforeNum == presentNum % 10 ? w : 0; // ������ ���� Ű�е��� w �߰�
				Result += (presentNum / 10) * p; // �ش� Ű�е带 �������ϴ� ��
				beforeNum = presentNum % 10;
			}
		}
	}

}
