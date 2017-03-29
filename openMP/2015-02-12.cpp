#include <omp.h>

int main (int args, char* argv[]){

  const int ROWS =100, LINES = 100;
  double avgM=0;
  int M[ROWS][LINES], N[ROWS][LINES];

  int sum=0, i, tot=0;

  #pragma omp parallel for reduction(+:sum) private (i) shared(tot){
    for(i=0; i<ROWS*LINES; i++){
      sum+=M[i/LINES][i%LINES];
    }
  }

  tot = sum/(ROWS*LINES);

  #pragma omp parallel for private(i){
    for(i=0; i<LINES*ROWS; i++){
      N[i/LINES][i%LINES]= M[i/LINES][i%LINES] - tot;
    }
  }

}
