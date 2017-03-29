module TempListener {
  uses{
    interface Read<uint16_t> as Sensor;
    interface Timer<TMilli>;
  }
  provides{
    interface Listener;
    interface StdControl;
  }
}

implementation {
  uint16_t sum;
  uint8_t curr;

  command error_t StdControl.start(){
    call Timer<TMilli>.startPeriodic(1000);
    curr  = 0;
    sum=0;
    return SUCCESS;
  }

  command error_t StdController.stop(){
    call Timer<TMilli>.stop();
    return SUCCESS;
  }

  event void Listener.fired(){
    call Sensor.read();
  }

  event void Read.readDone(error_t result, uint16_t val){
    atomic{
      curr++;
      sum+=val;
      if(curr >= 10){
        signal notify(sum/10);
        curr=0;
        sum=0;
      }
    }
  }


}
