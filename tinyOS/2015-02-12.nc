interface PeriodicRunner {
  command void start(uint16_t period);
  command void stop();
  event void readDone(error_t err, uint16_t val);
}

module PeriodicRunner {
  uses{
    interface Read<uint16_t> as Sensor;
    interface Timer<TMilli>;
  }
  provides {
    interface PeriodicRunner;
  }
}

implementation {

  command void start(uint16_t period){
    call Timer<TMilli>.startPeriodic(period);
  }

  command void stop(){
    call Timer<TMilli>.stop();
  }

  event void fired(){
    call Sensor.read();
  }

  event void Sensor.readDone(error_t err, uint16_t val){
    signal readDone(err, val);
  }
}
