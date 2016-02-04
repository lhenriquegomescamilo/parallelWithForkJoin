package com.multithreading.main;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import com.multithreading.interfaces.RunBenchmarkk;
import com.multithreading.model.QuickSortParallel;

public class TestQuickSortParallel {
	public static final int ARRAY_SIZE = 30_000_000;
	public static int[] array;

	public static void main(String[] args) {
		array = makeRandonArray();
		parallel();
		
		array = makeRandonArray();
		sequential();

	}

	private static void parallel() {
		benchmark(new RunBenchmarkk() {

			@Override
			public void action() {
				runWithForkJoin();
			}

			@Override
			public void showTime(long timeAvarage) {
				showResults(timeAvarage, "parallel");
			}

		});
	}

	private static void sequential() {
		benchmark(new RunBenchmarkk() {

			@Override
			public void action() {
				Arrays.sort(getArray());

			}

			@Override
			public void showTime(long timeAvarage) {
				showResults(timeAvarage, "sequencial");

			}
		});
	}

	private static void benchmark(RunBenchmarkk run) {
		long timeBegin = System.currentTimeMillis();
		run.action();
		long timeEnd = System.currentTimeMillis();
		long timeAvarage = timeEnd - timeBegin;
		run.showTime(timeAvarage);
	}

	private static void showResults(long timeAvarage, String type) {
		System.out.println("Tempo de processamento " + timeAvarage + " ms");
		System.out.println("Tipo de processamento " + type + "\n");

	}

	private static void runWithForkJoin() {
		QuickSortParallel rootTask = new QuickSortParallel(array, 0, array.length - 1);
		ForkJoinPool poolThreads = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		poolThreads.invoke(rootTask);
	}

	private static int[] makeRandonArray() {
		Random ran = new Random();
		array = new int[ARRAY_SIZE];
		for (int i = 0; i < array.length; i++) {
			array[i] = ran.nextInt();
		}
		return array;
	}

	public static int[] getArray() {
		return array;
	}

}
