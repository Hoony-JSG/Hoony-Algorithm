package day0825;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PRIMtest_PQ {
	
	static class Node{
		int vertex, weight;
		Node next;
		public Node(int vertex, int weight, Node next) {
			super();
			this.vertex = vertex;
			this.weight = weight;
			this.next = next;
		}
		
		
		
	}
	
	static class Vertex{
		int no, weight;

		public Vertex(int no, int weight) {
			super();
			this.no = no;
			this.weight = weight;
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {

		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(read.readLine()," ");
		int V = Integer.parseInt(tokens.nextToken()); 
		int E = Integer.parseInt(tokens.nextToken());
		
		Node[] adjList = new Node[V]; //각 정점별 인접 리스트
		
		for(int i = 0; i < E;i++) {
			tokens = new StringTokenizer(read.readLine());
			int from  = Integer.parseInt(tokens.nextToken());
			int to  = Integer.parseInt(tokens.nextToken());
			int weight  = Integer.parseInt(tokens.nextToken());
			
			adjList[from] = new Node(to, weight, adjList[from]);
			adjList[to] = new Node(from, weight, adjList[to]); //무향그래프임
			
		}
		//프림 알고리즘에 필요한 자료구조
		int [] minEdge = new int [V];	//각 정점이 신장트리에 포함된 정점과의 간선비용 중 최소 비용
		boolean[] visited = new boolean[V]; //신장트리에 포함되어있는지 여부
		
		Arrays.fill(minEdge, Integer.MAX_VALUE); //최소값 관리하기 위해 maxv
		
		//1. 일단 시장트리의 시작점 임의 선택
		minEdge[0] = 0;	//시작점의 자기까지 거리 = 0
		//돌려서 얘를 맨 처음에 뽑아야하니까 일단 방문처리 X
		int result = 0;
		
		////////////////////////PQ추가///////////////////////
		PriorityQueue<Vertex> PQ = new PriorityQueue<>((v1,v2) -> v1.weight - v2.weight);
		PQ.offer(new Vertex(0, minEdge[0]));
		
		int cnt = 0;//신장트리에 추가될 정점 수
		while(true) { //PQ에 계속 추가될 수 있어서 언제 끝날지 모르니 while true
			
			//step1. 신장트리의 구성에 포함되지 않은 정점 중 최소비용 정점 선택
			Vertex minVertex = PQ.poll();	//가장 작은 값 빼기(처음에는 시작점)
			
			
			
			/////////////////////변경///////////////////
			if(visited[minVertex.no]) continue;	//지금 정점은 앞에서 이미 최소값으로 처리한 정점임
			//step2. 신장 트리에 추가
			visited[minVertex.no] = true;
			result += minVertex.weight;
			if(++cnt == V) break;
			
			
			//step3. 신장트리에 새롭게 추가되는 정점과 신장트리에 포함되지 않은 정점들의 기존 최소 비용과 비교해서 갱신
			//	 신장트리에 새롭게 추가되는 정점에 연결된 모든 정점을 들여다보며 처리하면 됨
			for(Node temp = adjList[minVertex.no] ; temp != null; temp = temp.next) {
				if(!visited[temp.vertex] && minEdge[temp.vertex] > temp.weight) {
					//현재의 정점 이동값보다 새로 찾은 게 더 작다!
					minEdge[temp.vertex] = temp.weight;
					PQ.offer(new Vertex(temp.vertex, minEdge[temp.vertex]));
					//현재 정점에서 최소 비용 간선을 PQ에 추가
				}
			}
			
		}

		
		System.out.println(result);
		
	}

}
