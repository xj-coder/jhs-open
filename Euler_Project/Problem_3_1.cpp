#include <stdio.h>
#include <math.h>

//build vc6
bool isPrime(long num)
{
    int _sqrt = (int)sqrt(num);

    if (num == 2 || num == 3)
    {
        return true;
    }

    for (int i = 2; i < _sqrt; i++ )
    {
        if (num % i == 0)
        {
            return false;
        }
    }
    return true;
}

int main(int argc, char *argv[])
{
    long maxNum = 600851475143;
    int _sqrt = (int)sqrt(maxNum);
    long quot = maxNum;

    while (!isPrime(quot))
    {
        for (int i = 2; i < _sqrt; i++)
        {
            if (quot % i ==0)
            {
                quot = quot / i;
                printf("find Prime : %d \n",i);
                break;
            }
        }
    }

    printf("find Prime : %d \n",(int)quot);

    return 0;
}
