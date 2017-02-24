
package AuxSimulacao;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Buffer {
    
    ImageView buff = new ImageView("AuxSimulacao/img/Buffer_empty.jpg");
    
    int process_number, full = 0;
    
    public Buffer(int process_number){
        
        this.process_number = process_number;
        
        buff.setX(MainPkg.SceneConfig.step_preX + process_number * MainPkg.SceneConfig.step_spaceX + process_number * MainPkg.SceneConfig.step_sizeX);
        buff.setY(MainPkg.SceneConfig.step_preY + 90);
    }
    
    public Group getImage(){
        Group g = new Group();
        g.getChildren().add(buff);
        
        return g;
    }
    
    public void addMessage(){
        buff.setImage(new Image("AuxSimulacao/img/Buffer_full.jpg"));
    }
    
    public void sendMessage(){        
        buff.setImage(new Image("AuxSimulacao/img/Buffer_empty.jpg"));
    }
    
    public boolean getFull(){
        if(full == 1) return true;
        else return false;
    }
}
