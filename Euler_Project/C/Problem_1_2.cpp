#include <stdio.h>

//求一个数num中，指定数factor的所有倍数(不包括num本身)的和；
int multiplesSum(int num,int factor)
{
    int quotient = (num-1)/factor;
    return (1+quotient)*quotient/2 * factor;
}
int main(int argc, char *argv[])
{
    int maxNum = 1000;
    printf("result is %d" , multiplesSum(maxNum,3)+multiplesSum(maxNum,5)-multiplesSum(maxNum,15));
    return 0;
}
