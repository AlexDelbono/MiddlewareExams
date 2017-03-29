module ReadThree{
  uses{
    interface Read<uint16_t> as Sensor1;
    interface Read<uint16_t> as Sensor2;
    interface Read<uint16_t> as Sensor3;

  }
  provides {
    interface Read<uint16_t> as Out
  }
}

implementation {
  uint8_t count;
  uint8_t data[3];

  command error_t Out.read(){
    count = 0;
    call Sensor1.read();
    call Sensor2.read();
    call Sensor3.read();

    return SUCCESS;
  }

  task void avg(){
    uint16_t sum = 0;
    uint8_t i;

    for(i=0; i<3; i++){
      sum+=data[i];
    }
    signal Out.readDone(SUCCESS, sum/3);
  }

  event void Sensor1.readDone(error_t result, uint16_t val){
    atomic{
      data[count]=val;
      count++;
      if(count == 3){
        post avg();
      }
    }
  }


    event void Sensor2.readDone(error_t result, uint16_t val){
      atomic{
        data[count]=val;
        count++;
        if(count == 3){
          post avg();
        }
      }
    }


      event void Sensor3.readDone(error_t result, uint16_t val){
        atomic{
          data[count]=val;
          count++;
          if(count == 3){
            post avg();
          }
        }
      }

}
