#include <stdio.h>

int main(int argc, char *argv[])
{
    int fb1 = 1;
    int fb2 = 2;
    int sum = 0;

	int maxNum = 4000000;
    while (fb1<maxNum)
    {
		printf("%d \n",fb1);
		if(fb1 % 2==0)
			sum += fb1;

        fb2 = fb1+fb2;
		fb1 = fb2-fb1;
    }
	printf("result is %d",sum);
    return 0;
}