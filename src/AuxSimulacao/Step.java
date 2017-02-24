
/*
A classe step, eh uma classe filha de ImageView. A classe usada para manipular
imagens no javaFX. Elas alem de conter a imagem, manipulam, imprimem e criam a
animacao necessaria a cada passo.
A posicao em que se imprimem na tela, depende do processo/thread do qual fazem
parte. E sua posicao naquela thread.
*/

package AuxSimulacao;

import Functions.Function;
import MainPkg.SceneConfig;
import MainPkg.Mnemônico;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Step extends ImageView {

    private int preX = MainPkg.SceneConfig.step_preX, preY = MainPkg.SceneConfig.step_preY;
    private int spaceX = MainPkg.SceneConfig.step_spaceX, spaceY = MainPkg.SceneConfig.step_spaceY;
    private int sizeX = MainPkg.SceneConfig.step_sizeX, sizeY = MainPkg.SceneConfig.step_sizeY;

    private int position;
    private int process_number;
    private int pX, pY;
    private Function function;

    public Step(int process_number, int position, Function function) {

	super(new Image("AuxSimulacao/img/" + function.getFileName() + ".jpg"));

	this.position = position;
	this.function = function;
	this.process_number = process_number;

	this.pX = calcX();
	this.pY = calcY();

	reset();
    }

    public Function getFunction() {
	return function;
    }

    public void reset() {

	super.setX(pX);
	super.setY(pY);

	super.setFitWidth(sizeX);
	super.setFitHeight(sizeY);
    }

    public Timeline getAnimation() {

        int time = function.getTime()*1000;
	int time2;
	Timeline run = new Timeline();
	
	time2 = 4*time;
	    
	if (super.getY() + super.getTranslateY() < preY) {
	    run.getKeyFrames().addAll(
		    new KeyFrame(Duration.ZERO,
			    new KeyValue(super.translateXProperty(), super.getTranslateX()),
			    new KeyValue(super.translateYProperty(), super.getTranslateY())
		    ),
		    new KeyFrame(new Duration(time),
			    new KeyValue(super.translateXProperty(), super.getTranslateX()),
			    new KeyValue(super.translateYProperty(), super.getTranslateY() + spaceY + sizeY)
		    )
	    );
	} else if (super.getY() + super.getTranslateY() == preY) {
	    run.getKeyFrames().addAll(
		    new KeyFrame(Duration.ZERO,
			    new KeyValue(super.translateXProperty(), super.getTranslateX()),
			    new KeyValue(super.translateYProperty(), super.getTranslateY())
		    ),
		    new KeyFrame(new Duration(time2),
			    new KeyValue(super.translateXProperty(), super.getTranslateX()),
			    new KeyValue(super.translateYProperty(), super.getTranslateY() + 1000)
		    )
	    );
	} else {
	    return null;
	}

	return run;
    }

    private int calcX() {

	int p = 0;
	p += preX;
	p += process_number * spaceX;
	p += process_number * sizeX;

	return p;
    }

    private int calcY() {

	int p = 0;
	p += preY;
	p -= position * spaceY;
	p -= position * sizeY;

	return p;
    }

}
