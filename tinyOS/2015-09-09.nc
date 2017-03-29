module MaxRead {
  uses {
    interface Read<uint16_t> as Input;
    interface Timer<TMilli>;
  }
  provides {
    interface Read<uint16_t> as Output;
  }
}

implementation {
  uint16_t max;
  uint8_t i=0;

  command error_t Output.read(){
    i=0;
    call Timer<TMilli>.startPeriodic(20);
    return SUCCESS;
  }

  event void Timer<TMilli>.fired(){
    call Input.read();
  }

  event void Input.readDone(error_t err, unit16_t val){
    if(err == SUCCESS) {
      i++;
      max = val>max ? val : max;
    }
    if(i==10)
      signal readDone(SUCCESS, max);
  }
}
