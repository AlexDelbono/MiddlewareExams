module PacketFilter {
  uses{
    interface Receive as Input;
  }

  provides {
    interface Receive as Output;
    interface Read<uit16_t>;
  }
}

implementation {

  uit16_t dropped = 0;

  event message_t* Input.receive(message_t* msg, void * payload, uit8_t len){
    if(payload[0]==255){
      signal Output.receive(msg,payload,len);
      return msg;
    }
    dropped++;
    return null;
  }

  task void doRead(){
    signal readDone(SUCCESS, dropped);
  }

  command error_t read(){
    post doRead();
    return SUCCESS;
  }
}
