#include <omp.h>

int main(int args, char* argv[]){

  const int NUM = 100000;
  int data1[NUM], data2[NUM];

  int distance=0, i;

  #pragma omp prallalel for num_of_threads(100) reduction(+:distance) private(i) {
    for(i=0; i<NUM; i++){
      if(data1[i]!=data2[i])
        distance++;
    }
  }
}
