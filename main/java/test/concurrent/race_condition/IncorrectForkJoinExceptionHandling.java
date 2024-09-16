package test.concurrent.race_condition;

import java.util.concurrent.RecursiveTask;

/**
 * 4. Incorrect handling of exceptions in ForkJoinTask:
 *
 * Exceptions in ForkJoinTasks are not propagated normally.
 * Use completeExceptionally() to properly handle exceptions.
 */
public class IncorrectForkJoinExceptionHandling extends RecursiveTask<Integer> {
    private int[] array;
    private int start, end;

    public IncorrectForkJoinExceptionHandling(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 10) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                if (array[i] < 0) throw new IllegalArgumentException("Negative number found");
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            IncorrectForkJoinExceptionHandling left = new IncorrectForkJoinExceptionHandling(array, start, mid);
            IncorrectForkJoinExceptionHandling right = new IncorrectForkJoinExceptionHandling(array, mid, end);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}