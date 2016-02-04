package com.multithreading.model;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class QuickSortParallel extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int LIMIT_SEQUENTIAL = 100_000;

	private final int i;
	private final int j;

	private int[] array;

	public QuickSortParallel(int[] array, int i, int j) {
		this.i = i;
		this.j = j;
		this.array = array;
	}

	@Override
	protected void compute() {
		if (j - i < LIMIT_SEQUENTIAL) {
			Arrays.sort(array, i, j + 1);
		} else {
			int indexPivot = partitionArrowndPivod(i, j);
			QuickSortParallel taskOne = null;
			QuickSortParallel taskTwo = null;

			if (i < indexPivot)
				taskOne = new QuickSortParallel(array, i, indexPivot);
			if (indexPivot + 1 < j)
				taskTwo = new QuickSortParallel(array, indexPivot + 1, j);
			invokeAll(taskOne, taskTwo);
		}
	}

	private int partitionArrowndPivod(int indexLeft, int indeRight) {
		// Choice a pivot half of array. Could anything element
		int pivotValue = array[indexLeft + (indeRight - indexLeft) / 2];
		--indexLeft;
		++indeRight;
		while (true) {
			do {
				++indexLeft;
			} while (array[indexLeft] < pivotValue);
			do {
				--indeRight;
			} while (array[indeRight] > pivotValue);

			if (indexLeft < indeRight) {
				change(indexLeft, indeRight);
			} else {
				return indeRight;
			}
		}
	}

	private void change(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

}
