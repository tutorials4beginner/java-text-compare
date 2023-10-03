package com.calgenix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

public class FileCompare {
	public static void main(String[] args) {
		List<String> original = fileToLines("originalFile.txt");
		List<String> revised = fileToLines("revisedFile.txt");

		// Compute diff. Get the Patch object. Patch is the container for
		// computed deltas.
		Patch patch = DiffUtils.diff(original, revised);

		for (Delta delta : patch.getDeltas()) {
			System.out.println("Change  :" + delta);
			System.out.println("Original:" + delta.getOriginal().toString().substring(
					delta.getOriginal().toString().lastIndexOf("[") + 1, delta.getOriginal().toString().length() - 2));
			System.out.println("Revised :" + delta.getRevised().toString().substring(
					delta.getRevised().toString().lastIndexOf("[") + 1, delta.getRevised().toString().length() - 2));

			diff_match_patch difference = new diff_match_patch();
			LinkedList<Diff> deltas = difference.diff_main(
					delta.getOriginal().toString().substring(delta.getOriginal().toString().lastIndexOf("[") + 1,
							delta.getOriginal().toString().length() - 2),
					delta.getRevised().toString().substring(delta.getRevised().toString().lastIndexOf("[") + 1,
							delta.getRevised().toString().length() - 2));

			System.out.println("===================================");
			for (Diff d : deltas) {
				System.out.println(d.operation + ":" + d.text);
			}
			// Reconstruct texts from the deltas
			// text1 = all deletion (-1) and equality (0).
			// text2 = all insertion (1) and equality (0).
			// String text1 = "";
			// String text2 = "";
			// for (Diff d : deltas) {
			// if (d.operation == Operation.DELETE)
			// text1 += d.text;
			// else if (d.operation == Operation.INSERT)
			// text2 += d.text;
			// else {
			// text1 += d.text;
			// text2 += d.text;
			// }
			// }
			// System.out.println(text1);
			// System.out.println(text2);
			System.out.println();
		}
	}

	private static List<String> fileToLines(String filename) {
		List<String> lines = new LinkedList<String>();
		String line = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
