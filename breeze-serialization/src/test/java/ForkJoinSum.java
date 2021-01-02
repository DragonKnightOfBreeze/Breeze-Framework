// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

import java.util.concurrent.RecursiveTask;

public class ForkJoinSum extends RecursiveTask<Long> {
    private static final long serialVersionUID = 6212165578436873636L;

    private final long start;
    private final long end;

    //临界值
    private static final long THRESHOLD = 10000;

    public ForkJoinSum(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if(length <= THRESHOLD) {
            //如果未到达临界值，则进行真正的计算逻辑
            long sum = 0;
            for(long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //否则拆分成两个相同区间的任务
            long middle = (start + end) / 2;
            var left = new ForkJoinSum(start, middle);
            //加入线程队列
            left.fork();
            var right = new ForkJoinSum(middle + 1, end);
            right.fork();
            //得到整合后的结果
            return left.join() + right.join();
        }
    }

	public static void main(String[] args) {
		Long result = new ForkJoinSum(1, 1000).compute();
		System.out.println(result);
	}
}
