package com.calgenix;

import java.util.LinkedList;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

public class TextCompare {

	public static void main(String[] args) {

		diff_match_patch difference = new diff_match_patch();
		LinkedList<Diff> deltas = difference.diff_main("Good dog cat het", "Bad dog rat");

		for (Diff d : deltas) {
			System.out.println("----" + d.text);
		}
		// Reconstruct texts from the deltas
		// text1 = all deletion (-1) and equality (0).
		// text2 = all insertion (1) and equality (0).
		String text1 = "";
		String text2 = "";
		for (Diff d : deltas) {
			if (d.operation == Operation.DELETE)
				text1 += d.text;
			else if (d.operation == Operation.INSERT)
				text2 += d.text;
			else {
				text1 += d.text;
				text2 += d.text;
			}
		}
		System.out.println(text1);
		System.out.println(text2);
	}
}
