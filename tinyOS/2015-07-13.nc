interface Counter{
  command error_t start();
  command error_t stop();

  command error_t increment();
  event void fired(unit16_t val);
}

module CounterImpl {
  uses{
    interface Timer<TMilli>
  }
  provides{
    interface Counter
  }
}

implementation {
  unit16_t val=0;
  unit16_t inc=0;


  event void fired(){
    atomic{
      inc=0;
      val=0;
    }
  }

  command error_t start(){
    atomic{
      call Timer<TMilli>.startPeriodic(10000);
      inc=0;
      val=0;
    }
    return SUCCESS;
  }

  command errot_t stop(){
    call Timer<TMilli>.stop();
    return SUCCESS
  }

  command error_t increment(){
    atomic{
      val++;
      inc++;
      if(inc >= 100)
        signal fired(val);
    }
  }
}
