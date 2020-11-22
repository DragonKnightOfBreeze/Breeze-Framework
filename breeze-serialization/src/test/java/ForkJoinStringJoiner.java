// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinStringJoiner extends RecursiveTask<String> {
    List<String> strings;


    @Override
    protected String compute() {
        //long length = end - start;
		//
        //if(length <= THRESHOLD) {
        //    //如果未到达临界值，则进行真正的计算逻辑
        //    long sum = 0;
        //    for(long i = start; i < end; i++) {
        //        sum += i;
        //    }
        //    return sum;
        //} else {
        //    //否则拆分成两个相同区间的任务
        //    long middle = (start + end) / 2;
        //    var left = new ForkJoinStringJoiner(start, middle);
        //    //加入线程队列
        //    left.fork();
        //    var right = new ForkJoinStringJoiner(middle + 1, end);
        //    right.fork();
        //    //得到整合后的结果
        //    return left.join() + right.join();
        //}
	    return "";
    }
}
