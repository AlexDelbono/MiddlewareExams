interface Counter {
  event void total(uint16_t val);
  command error_t reset();
}

module CounterImpl {
  uses{
    interface Receive;
  }
  provides {
    interface Counter;
  }
}

implementation {
  uint16_t counter=0;

  event message_t* receive(message_t* msg, void* payload, uint8_t len){
    counter++;

    if(counter % 10 == 0 )
      signal total(counter);
    return msg;
  }

  command error_t reset(){
    counter=0;
    return SUCCESS;
  }


}
