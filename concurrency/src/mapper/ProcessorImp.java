package mapper;

public class ProcessorImp implements Processor {
	@Override
	public int process(int i) {
		return i*i;
	}

}
