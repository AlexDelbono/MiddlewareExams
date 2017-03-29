#include <omp.h>

int main(int args, char* argv[]){

  const int NUM = 10000;
  int data[NUM];

  int max, end, i;

  //integers are positive
  max=0;
  end=0;

  #pragma omp parallel private(i) shared(end) firstprivate(max){
    #pragma omp for
    for(i=0; i<NUM; i++){
      max = data[i]>max ? data[i] : max;
    }

    #pragma omp critical{
      end=max>end ? max : end;
    }
  }

}
