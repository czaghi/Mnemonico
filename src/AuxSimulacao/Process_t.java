
/*
A classe process_t cuida devarias threads. (2 ou 3, precisamos decidir (prefiro
duas por causa do espaco)). Um objeto Process_t contem uma ou duas threads e
as escalona conforme ganha o processador. Ele anda os steps de uma thread por
vez e cuida da funcao retornada.
*/

package AuxSimulacao;

import Functions.Function;
import MainPkg.FunctionHandler;

public class Process_t {

    private int number, active_n;
    private Thread_t Thread1;
    private Thread_t Thread2;
    private int active;
    private int p_msg[] = new int[3];
    private int p_req[] = new int[3];

    public Process_t(int number, int active_n, int s1, int s2) {

	Thread1 = new Thread_t(s1, number);
        
        p_msg[0] = 0;
        p_msg[1] = 0;
        p_msg[2] = 0;
        p_req[0] = 0;
        p_req[1] = 0;
        p_req[2] = 0;

	this.active_n = active_n;
	this.number = number;

    }

    public void takeNextStep() {

	Function next;
        
        if(Thread1.getFinished() == 1) return;
        
	next = Thread1.getStep().getFunction();
        FunctionHandler.HandleFunction(next);
    }

    public Thread_t getThread(int n) {
	
	if (active_n == 0) {
	    return null;
	}
	if (n == 1) {
	    return Thread1;
	}
	if (active_n == 1) {
	    return null;
	}
	if (n == 2) {
	    return Thread2;
	}

	return null;
    }

    public int getStatus() {
	return active;
    }
    
    public int getNumber(){
	return number;
    }

    public void activate() {
	active = 1;
    }

    public void deactivate() {
	active = 0;
    }
    
    public int getFinished() {
	return Thread1.getFinished();
    }
    
    public void addPmsg(int n){
        p_msg[n]++;
    }
    
    public void remPmsg(int n){
        p_msg[n]--;
    }
    
    public int getPmsg(int n){
        if(n == -1) return p_msg[0]+p_msg[1]+p_msg[2];
        return p_msg[n];
    }
    
    public void addPreq(int n){
        p_req[n]++;
    }
    
    public void remPreq(int n){
        p_req[n]--;
    }
    
    public int getPreq(int n){
        return p_req[n];
    }

    private int schedule() {

	int esc = (int) (Math.random() * active_n) + 1;

	return esc;
    }
}
