
/*
A funcao Nop, alem de uma linda homenagem ao melhor professor do semestre,
caracteriza que naquele "step" a thread nao tem nada a fazer. Ou no caso nada
relacionado a simulacao de IPC.
*/

package Functions;

import AuxSimulacao.Message;
import AuxSimulacao.Thread_t;

public class Nop implements Function{
    
    private Thread_t owner;
    
    public Nop(Thread_t owner){
	this.owner = owner;
    }

    @Override
    public String getName() {
	return "Nop";
    }
    
    @Override
    public String getFileName() {
	return "Nop";
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
    public void setMsg(Message msg) {}

    @Override
    public void setAck(Message ack) {}
}
