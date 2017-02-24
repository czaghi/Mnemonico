
/*
A funcao Receive diz que a thread espera, naquele step, uma mensagem chegando
de uma thread do processo sender.
*/

package Functions;

import AuxSimulacao.Message;
import AuxSimulacao.Thread_t;
import javafx.scene.image.Image;

public class Receive implements Function{
    
    private int sender;
    Message msg;
    private Thread_t owner;
    
    public Receive(Thread_t owner, int sender){
	this.sender = sender;
	this.owner = owner;
        this.msg = null;
    }
    
    @Override
    public String getName(){
	return "Receive";
    }
    
    @Override
    public String getFileName(){
	return "Receive" + sender;
    }
    
    public int getSender(){
	return sender;
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
    
    public void setMsg(Message msg){
        this.msg = msg;
        owner.getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
    }
    
    public Message getMsg(){
        return msg;
    }

    @Override
    public void setAck(Message ack) {}
}
