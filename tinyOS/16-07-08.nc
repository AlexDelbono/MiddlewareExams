module MovingAvgTemp{
  uses {
    interface Read<float> as TempReader
    interface Timer<TMilli>
  }

  provides {
    interface StdControl
    interface Read<float> as AvgReader
  }
}

implementation {
  float temps[60];
  uint8_t curr_sec

  task void computeAverage(){
    uint8_t i;
    float sum =0;

    atomic{
      for(i=0; i<60; i++){
        sum += temps[i];
      }
    }
    signal AvgReader.readDone(SUCCESS, sum / 60);
  }

  command error_t AvgReader.read(){
    post computeAverage();
    return SUCCESS;
  }

  command error_t start(){
    curr_sec = 0;
    call Timer.startPeriodic(1000);
    return SUCCESS;
  }

  command error_t stop(){
    call Timer.stop();
    return SUCCESS;
  }

  event void fired(){
    call TempReader.read();
  }

  event void readDone(error_t err, float val){
    if(err == SUCCESS){
      atomic {
        temps[curr_sec++] = val;
      }
    }
  }
}
