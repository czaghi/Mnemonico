
/*
Da classe thread_t, instanciamos os objetos que de fato sao usados na simuacao.
Elas contem um vetor de steps, cada um contendo sua respectiva funcao, que
manipulam o que aparece na tela. No entanto nao trabalham com a parte grafica
em si. Apenas executam quando ordenadas e retornam a ultima funcao e as
animacoes que devem rodar.
*/

package AuxSimulacao;

import Functions.*;
import MainPkg.FunctionHandler;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.scene.Group;

public class Thread_t {
    
    private int size, process_number;
    private int blocked, active;
    private int iteration, finished;
    private Step current;
    private ArrayList<Step> steps = new ArrayList<>();
    private Buffer buff;

    public Thread_t(int size, int process_number) {
	
	this.size = size;
	this.blocked = 0;
	this.iteration = 0;
	this.process_number = process_number;
	this.active = 0;
	if (size > 0) this.finished = 0;
	else this.finished = 1;
        
        buff = new Buffer(process_number);
	
	this.initializeSteps();
    }
    
    public void setStep(int position, Function function){
	
	function.setOwner(this);
	
	steps.set(position, new Step(process_number, position, function));
    }
    
    public void Finish(){
	this.finished = 1;
    }
    
    public int getFinished(){
	return finished;
    }
    
    public void takeNextStep(){
	
	int i;
	Timeline curr;

	current = steps.get(iteration);
        
        if (blocked == 1) return;
	
	for(i=iteration; i<size; i++){
	    if ((curr = steps.get(i).getAnimation()) != null){
		curr.play();
	    }
	}
	
	iteration = iteration + 1;
    }
    
    public Step getStep(){
        if(iteration == size) return new Step(0, 0, new Nop(this));
        else return steps.get(iteration);
    }
    
    public Group getNode(){
	
	Group node = new Group();
	int i;
        
	for(i=0; i<size; i++){
	    node.getChildren().add(steps.get(i));
        }
        
        if(FunctionHandler.buffer == true) node.getChildren().add(buff.getImage());
	
	return node;
    }
    
    public Buffer getBuffer(){
        return buff;
    }
    
    public int getNumber(){
	return process_number;
    }
    
    public int getSize(){
        return size;
    }
    
    public int getIteration(){
        return iteration;
    }
    
    private void initializeSteps(){
	
	int i;
	
	steps.ensureCapacity(size);
	
	for(i=0; i<(size - 1); i++){
	    steps.add(new Step(process_number, i, new Nop(this)));
	}
	
	steps.add(new Step(process_number, i, new Exit(this)));
    }
}
