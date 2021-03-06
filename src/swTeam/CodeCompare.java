package swTeam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CodeCompare {
	
	static private int row, col;
	static private Queue<Integer>[] results = new LinkedList[2];
	static private 	ArrayList<String>[] file = new ArrayList[2];
	
	public CodeCompare(String code1, String code2) {
		file[0] = new ArrayList<>(Arrays.asList(code1.split("\n")));
		file[1] = new ArrayList<>(Arrays.asList(code2.split("\n")));
		file[0].add("$");
		file[1].add("$");
		row = file[0].size();
		col = file[1].size();
		int[][] map = new int[row][col];
		for ( int r = row-2; r >= 0; r-- ) {
			for ( int c = col-2; c >= 0; c-- ) {
				int val = 1;
				if ( file[0].get(r).equals(file[1].get(c)) )
					val = 10000;
				map[r][c] = Integer.max( Integer.max(map[r+1][c], map[r][c+1]), map[r+1][c+1]+val);
			}
		}
		results[0] = new LinkedList<>();
		results[1] = new LinkedList<>();
		int r = 0, c = 0;
		while ( isRange(r, c) ) {
			int val = 1;
			if ( file[0].get(r).equals(file[1].get(c)) )
				val = 10000;
			if ( map[r][c] == map[r+1][c+1]+val ) {
				if ( val == 10000 ) {
					results[0].add(r);
					results[1].add(c);
				}
				r++;	c++;
			} else if ( map[r][c] == map[r+1][c] ) {
				r++;
			} else {
				c++;
			}
		}
	}
	
	public ArrayList<Integer> getDiff(int idx) {
		return misMatch(file[idx], results[idx]);
	}
	
	public String printCode(int index) {
		String r = "";
		for ( int i = 0; i < file[index].size()-1; i++ ) {
			r += "<div";
			if ( !results[index].isEmpty() && i != results[index].peek() ) {
				if ( index == 0 )
					r += " style='background-color:Tomato;'";
				else
					r += " style='background-color:MediumSeaGreen;'";
			} else
				results[index].poll();
			r += ">"+file[index].get(i)+"</div>";
		}
		return r;
	}
	
	/*
	public ArrayList<String> printCode(int index) {
		ArrayList<String> result = new ArrayList<>();
		for ( int i = 0; i < file[index].size()-1; i++ ) {
			String r = "<tr><td";
			if ( !results[index].isEmpty() && i != results[index].peek() ) {
				if ( index == 0 )
					r += " style='background-color:red'";
				else
					r += " style='background-color:green'";
			} else
				results[index].poll();
			r += ">"+file[index].get(i)+"</td></tr>";
			result.add(r);
		}
		return result;
	}
	*/
	public static ArrayList<Integer> misMatch(ArrayList<String> code, Queue<Integer> equal) {
		ArrayList<Integer> result = new ArrayList<>();
		for ( int i = 0; i < code.size()-1; i++ ) {
			if ( i != equal.peek() )
				result.add(i);
			else
				equal.poll();
		}
		return result;
	}
	
	private static boolean isRange( int r, int c ) {
		if ( r < row-1 && c < col-1 )
			return true;
		return false;
	}
}
