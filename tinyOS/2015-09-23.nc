module PacketCounter {
  uses{
    interface Receive;
    interface Timer<TMilli>;
  }
  provides {
    interface Init;
    interface Read<uint16_t>;
  }
}

implementation {
  uint16_t counter=0;

  command error_t init(){
    call Timer<TMilli>.startPeriodic(10000);
    return SUCCESS;
  }

  event void fired(){
    atomic{
      count=0;
    }
  }

  event message_t* receive(message_t* msg, void* payload, uint8_t len){
    atomic{
      count++;
    }
    return msg;
  }

  command error_t read(){
    signal readDone(SUCCESS, counter);
    return SUCCESS;
  }
}
