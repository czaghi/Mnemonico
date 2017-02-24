
package AuxConfig;

import javafx.event.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StepPreview extends ImageView{
    
    private ProcessPreview owner;
    private String img, old_img;
    private int process_number, pos;
    private Function_n func, old_func;
    
    private int preX = 90, preY = 350, spaceX = 30, spaceY = 0, sizeX = 80, sizeY = 30;
    
    public StepPreview(String img, Function_n func, ProcessPreview owner, int process_number, int pos){
	super(img);
	
	this.img = img;
	this.old_img = "AuxConfig/img/Empty.jpg";
	this.owner = owner;
	this.process_number = process_number;
	this.pos = pos;
	this.func = func;
	this.old_func = func;
	
	this.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event e) -> {
	    change();
	});
	
	this.setX(calcX());
	this.setY(calcY());
    }
    
    public String getImg(){
	return img;
    }
    
    public void setFunc(Function_n new_func){
	old_func = func;
	func = new_func;
    }
    
    public void changeBack(){
	img = old_img;
	func = old_func;
	setImage(new Image(img));
    }
    
    public Function_n getFunc(){
	return func;
    }
    
    public void change(){
	if (pos == owner.getSize()) return;
	if (owner.getSelectedImg() == null) return;
	
	this.setImage(new Image(img = old_img = owner.getSelectedImg()));
	func = old_func = owner.getSelectedFunc();
    }
    
    private double calcX(){
	return (double) preX + process_number*(sizeX + spaceX);
    }
    
    private double calcY(){
	return (double) preY - pos*(sizeY + spaceY);
    }
}
