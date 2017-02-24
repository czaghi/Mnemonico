package AuxSimulacao;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Message {

    int process_number, destiny, time;
    ImageView msg;

    public Message(int process_number, int destiny, int type, boolean help) {

        this.process_number = process_number;
        this.destiny = destiny;
        if (help == false) this.time = MainPkg.SceneConfig.autoexec_time - 100;
        else this.time = MainPkg.SceneConfig.autoexec_time*2 - 100;

        if (type == 0) {
            msg = new ImageView("AuxSimulacao/img/Msg.jpg");
        }
        if (type == 1) {
            msg = new ImageView("AuxSimulacao/img/ACK.jpg");
        }
        if (type == 2) {
            msg = new ImageView("AuxSimulacao/img/Request.jpg");
        }

        msg.setX(MainPkg.SceneConfig.step_preX + process_number * MainPkg.SceneConfig.step_spaceX + process_number * MainPkg.SceneConfig.step_sizeX + 20);
        msg.setY(MainPkg.SceneConfig.step_preY);
    }

    public ImageView getImage() {
        return msg;
    }

    public int getDestiny() {
        return destiny;
    }

    public int getOrigin() {
        return process_number;
    }

    public Timeline Discard() {

        Timeline run = new Timeline();

        run.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 100)
                ),
                new KeyFrame(new Duration(time),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 1000)
                )
        );

        return run;
    }

    public Timeline Send() {

        Timeline run = new Timeline();

        run.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(msg.translateXProperty(), 0),
                        new KeyValue(msg.translateYProperty(), 0)
                ),
                new KeyFrame(new Duration(time / 3),
                        new KeyValue(msg.translateXProperty(), 0),
                        new KeyValue(msg.translateYProperty(), 150)
                ),
                new KeyFrame(new Duration(2*time / 3),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 150)
                ),
                new KeyFrame(new Duration(time),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 0)
                ),
                new KeyFrame(new Duration(time + 50),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 10000)
                )
        );

        return run;
    }

    public Timeline SendToBuffer() {

        Timeline run = new Timeline();

        run.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(msg.translateXProperty(), 0),
                        new KeyValue(msg.translateYProperty(), 0)
                ),
                new KeyFrame(new Duration(time),
                        new KeyValue(msg.translateXProperty(), 0),
                        new KeyValue(msg.translateYProperty(), 90)
                ),
                new KeyFrame(new Duration(5*time / 4),
                        new KeyValue(msg.translateXProperty(), 0),
                        new KeyValue(msg.translateYProperty(), 150)
                ),
                new KeyFrame(new Duration(5*time / 2),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 150)
                ),
                new KeyFrame(new Duration(3*time),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 90)
                ),
                new KeyFrame(new Duration(3*time + 50),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 10000)
                )
        );

        return run;
    }

    public Timeline SendFromBuffer() {

        Timeline run = new Timeline();

        run.getKeyFrames().addAll(
                new KeyFrame(new Duration(0),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 90)
                ),
                new KeyFrame(new Duration(2*time),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 0)
                ),
                new KeyFrame(new Duration(2*time + 50),
                        new KeyValue(msg.translateXProperty(), (destiny - process_number) * MainPkg.SceneConfig.step_spaceX + (destiny - process_number) * MainPkg.SceneConfig.step_sizeX),
                        new KeyValue(msg.translateYProperty(), 10000)
                )
        );

        return run;
    }
}
