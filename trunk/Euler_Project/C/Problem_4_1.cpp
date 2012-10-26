#include <stdio.h>

bool isPalindromic(int num)
{
    int pal = 0;
    int origin = num;

    while (num)
    {
        pal *= 10;
        pal += num % 10;
        num /= 10;
    }

    return pal == origin;
}

int main(int argc, char *argv[])
{
    int maxP = 0;
    for (int i=100;i<1000 ;i++ )
    {
        for (int j=100; j<1000;j++ )
        {
            int prod = i*j;
            if (isPalindromic(prod) && prod>maxP)
            {
                maxP=prod;
            }
        }
    }
    printf("result is %d\n",maxP);
    return 0;
}
