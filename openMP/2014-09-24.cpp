#include <omp.h>
#include <climits>

int main(int args, char* argv[]){

  const int NUM = 100;
  int A[NUM];
  float B[NUM];

  int i, max=INT_MIN;
  float MaxA=FLT_MIN;

  #pragma parallel private(i) firstprivate(max) shared(MaxA){
    #pragma omp for{
      for(i=0; i<NUM)
        max = A[i]>max ? A[i] : max;
    }

    #pragma omp critical{
      MaxA = max>MaxA ? max : MaxA;
    }

    #pragma omp for {
      for(i=0; i<NUM; i++){
        B[i]=A[i]/MaxA;
      }
    }
  }

}
