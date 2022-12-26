package day1109;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class test {

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		String s = input.readLine();
		String n = s.toUpperCase();
		HashMap<Character, Integer> map = new HashMap<>();

		// map �ʱ�
		for (int i = 0; i < n.length(); i++) {
			char key = n.charAt(i);
			if (map.containsKey(key)) {
				map.replace(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}

		// ���ĺ��� ���� ���� & ���� ū value�� ã��

		// �ִ��� �ߺ��Ǵ��� Ȯ��
		List<Integer> list = new ArrayList<>(map.values());
		Collections.sort(list, Collections.reverseOrder()); // �������� ����� �տ��� �ΰ��� �ߺ��Ǹ� ����ǥ ���, �ƴϸ� �� �� value�� �ش��ϴ� Ű�� ��

		if (list.size() == 1) { // 1���� �װ� ��
			char key = getKey(map, list.get(0)); // map�� value�� �־��ָ� key�� ������
			System.out.println(key);
		} else if (list.size() > 1) {
			if ((int)list.get(0) == (int)list.get(1)) {
				System.out.println("?");
			} else {
				char key = getKey(map, list.get(0));
				System.out.println(key);
			}
		}

		// System.out.println(map);

	}

	private static char getKey(HashMap<Character, Integer> map, int intnum) {
		char result = '#';
		for(char tempkey : map.keySet()) {
			if(map.get(tempkey) == intnum) {
				result = tempkey;
				break;
			}
		}
		return result;
//		for (char key : map.keySet()) {
//			if (integer.equals(map.get(key))) {
//				return key;
//			}
//		}
//		return 'A'; // �ǹ̾��� ��..
	}

}
