#include <stdio.h>

int main(int argc, char *argv[])
{
	int sum = 0;
    int index = 1;

    while (index < 1000)
    {
        if (index % 3 == 0 || index % 5 == 0)
        {
            sum += index;
        }
        index++;
    }
	printf("result is %d",sum);
    return 0;
}