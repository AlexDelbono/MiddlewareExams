#include <iostream>
#include <vector>
#include <omp.h>

using namespace std;

int main(int argc, char* argv[]){
  const int N = 100000;
  double data[N];

  double sum=0, total=0;
  int i;

  #pragma omp parallel num_of_threads(10) firstprivate(sum) private(i) shared(total)
  {
    #pragma omp for schedule(dynamic){
      for(i=0; i<N; i++){
        sum+=data[i];
      }
    }

    #pragma omp critical (final_sum){
      total+=sum;
    }

    #pragma omp barrier

    #pragma omp for schedule(dynamic){
      for(i=0; i<N; i++){
        data[i]=total/N;
      }
    }
  }
}
