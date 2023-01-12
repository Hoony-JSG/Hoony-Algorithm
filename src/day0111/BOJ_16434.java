package day0111;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ_16434 {

	public static void main(String[] args) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer tokens = new StringTokenizer(read.readLine());
		int N = Integer.parseInt(tokens.nextToken());
		long Hatk = Integer.parseInt(tokens.nextToken());
		long Hhp = 0;
		long Htemp = 0;
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(read.readLine());
			int t = Integer.parseInt(tokens.nextToken());
			int a = Integer.parseInt(tokens.nextToken());
			int h = Integer.parseInt(tokens.nextToken());
			if (t == 1) {
				Htemp += a * (h / Hatk); // ��ŭ ��������
				Htemp -= h % Hatk != 0 ? 0 : a; // �� �� �� �������ϳ�?
				Hhp = Math.max(Hhp, Htemp); // ���� ü������ ������ �Ǵ��� / �� �� �÷����ϴ���
			} else {
				Hatk += a;
				Htemp = Math.max(0, Htemp - h); // ���߿� �������� ���� ��ŭ �̸� ������(��������)
			}

		}
		write.write((Hhp + 1) + "\n");
		write.close();
		read.close();
	}
}
