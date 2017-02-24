
/*
Envia naquele step uma mensagem a thread receiver.
*/

package Functions;

import AuxSimulacao.Message;
import AuxSimulacao.Thread_t;

public class Send implements Function{
    
    private int receiver;
    Message ack;
    private Thread_t owner;
    
    public Send(Thread_t owner, int receiver){
	this.receiver = receiver;
	this.owner = owner;
        this.ack = null;
    }
    
    @Override
    public String getName(){
	return "Send";
    }
    
    @Override
    public String getFileName(){
	return "Send" + receiver;
    }
    
    public int getReceiver(){
	return receiver;
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
    public void setOwner(Thread_t owner) {
	this.owner = owner;
    }
    
    @Override
    public void setAck(Message ack){
        this.ack = ack;
    }
    
    public Message getAck(){
        return ack;
    }

    @Override
    public void setMsg(Message msg) {}
}
