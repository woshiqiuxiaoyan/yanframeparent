package user.controller;

import java.util.Random;

/**
 * <p>Title:Test </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/16
 * Time: 18:19
 */


import java.text.DecimalFormat;
import java.util.Random;

public class Test {

 

    static Random random = new Random();
    static {
        random.setSeed(System.currentTimeMillis());
    }



    public static void main(String[] args) {

        int max = 200;//最大红包
        int min = 100;//最小红包

        int total = 78;//总金额
        int count  = 80 ;//个数



        int[] result = Test.generate(total, count, max, min);

        if(result==null){
            return ;
        }

        int checktotal = 0;

        //检查生成的红包的总额是否正确
        for (int i = 0; i < result.length; i++) {

            checktotal  +=result[i];

            System.out.println("" + i + "  " + result[i]+"      "+ checktotal );

        }



        //统计每个钱数的红包数量，检查是否接近正态分布
    /*    int count[] = new int[(int) max + 1];
        for (int i = 0; i < result.length; i++) {
            count[(int) result[i]] += 1;
        }
*/
     /*   for (int i = 0; i < count.length; i++) {
            System.out.println("" + i + "  " + count[i]);
        }*/
    }


    static int xRandom(int min, int max) {
        return sqrt(nextLong(sqr(max - min)));
    }


    /**
     *
     * @param total 总金额
     * @param count
     * @param max
     * @param min
     * @return
     */
    public static int[] generate(int total, int count, int max, int min) {

        if(count*max<total){
            System.out.println("总金额不足");
            return null;
        }
        if(count*min>total){
            System.out.println("总金额过多");
            return null;
        }


        //生成 count 个
        int[] result = new int[count];


        int average = total / count;

        int a = average - min;
        int b = max - min;

        //
        //这样的随机数的概率实际改变了，产生大数的可能性要比产生小数的概率要小。
        //这样就实现了大部分红包的值在平均数附近。大红包和小红包比较少。
        int range1 = sqr(average - min);
        int range2 = sqr(max - average);

        for (int i = 0; i < result.length; i++) {
            //因为小红包的数量通常是要比大红包的数量要多的，因为这里的概率要调换过来。
            //当随机数>平均值，则产生小红包
            //当随机数<平均值，则产生大红包
            int temp = 0;
            if (nextLong(min, max) > average) {
                // 在平均线上减钱
                if(min==average){
                    temp=min;
                }else{
                    temp = min+xRandom(min, average);
                }
                result[i] = temp;
                total -=result[i];
            } else {
                // 在平均线上加钱
                if(max==average){
                    temp= max;
                }else{
                    temp = max - xRandom(average, max);
                }

                result[i] = temp;
                total -=result[i];
            }
        }
        // 如果还有余钱，则尝试加到小红包里，如果加不进去，则尝试下一个。
        while (total > 0) {
            for (int i = 0; i < result.length; i++) {
                if (total > 0 && result[i] < max) {
                    result[i] +=1 ;
                    total -= 1;
                }
            }
        }
        // 如果钱是负数了，还得从已生成的小红包中抽取回来
        while (total < 0) {
            for (int i = 0; i < result.length; i++) {
                if (total < 0 && result[i] > min) {
                    result[i] -=1;
                    total+=1;
                }
            }
        }
        return result;
    }

    static int sqrt(int n) {
        // 改进为查表？
        return (int) Math.sqrt(n);
    }

    static int sqr(int n) {
        return n * n;
    }

    static int nextLong(int n) {
        return random.nextInt(n);
    }

    static int nextLong(int min, int max) {
        return random.nextInt(max - min+1) + min;
    }
}

