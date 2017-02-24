
package Functions;

import AuxSimulacao.Message;
import AuxSimulacao.Thread_t;

public class Exit implements Function{
    
    Thread_t owner;
    
    public Exit(Thread_t owner){
	this.owner = owner;
    }

    @Override
    public String getName() {
	return "Exit";
    }
    
    @Override
    public String getFileName() {
	return "Exit";
    }

    @Override
    public void setOwner(Thread_t owner) {
	this.owner = owner;
    }

    @Override
    public Thread_t getOwner() {
	return owner;
    }
    
    @Override
    public int getTime() {
        return 1;
    }

    @Override
    public void setMsg(Message msg) {}

    @Override
    public void setAck(Message ack) {}
    
}
