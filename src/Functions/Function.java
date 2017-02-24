
/*
A interface function existe para garantir o polimorfismo das funcoes atribuidas
aos steps (criar um vetor de funcoes quaisquer).
Ela obriga que a funcao, ao ser instanciada receba a thread a que pertence
(Owner) e seu nome. O nome da funcao eh o que define como a HandleFunction deve
trata-la e ainda qual o caminho da imagem que deve ser carregada para seu step.
*/

package Functions;

import AuxSimulacao.Message;
import AuxSimulacao.Thread_t;

public interface Function {
    
    public abstract String getName();
    
    public abstract String getFileName();
    
    public abstract int getTime();
    
    public abstract void setOwner(Thread_t owner);
    
    public abstract Thread_t getOwner();
    
    public abstract void setMsg(Message msg);
    
    public abstract void setAck(Message ack);
    
}
