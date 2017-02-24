package AuxConfig;

import static AuxConfig.Function_n.*;
import static MainPkg.Languages.LangStrings.cust;
import static MainPkg.Languages.LangStrings.preset;
import static MainPkg.Languages.LangStrings.salvars;
import static MainPkg.Languages.LangStrings.tooltip;
import static MainPkg.SceneConfig.autoexec_time;
import static MainPkg.Mnemônico.lang;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class PreviewButtons {

    Group All = new Group();

    Button add1 = startButton("+", 95, 20, 15);
    Button rem1 = startButton("-", 145, 20, 15);
    Button add2 = startButton("+", 205, 20, 15);
    Button rem2 = startButton("-", 255, 20, 15);
    Button add3 = startButton("+", 315, 20, 15);
    Button rem3 = startButton("-", 365, 20, 15);

    Button nop = startButton("Nop", 200, 390, 80);
    Button send1 = startButton("Send(1)", 90, 429, 80);
    Button send2 = startButton("Send(2)", 200, 429, 80);
    Button send3 = startButton("Send(3)", 310, 429, 80);
    //Button receive0 = startButton("Receive", 390, 420, 50);
    Button receive1 = startButton("Receive(1)", 90, 468, 80);
    Button receive2 = startButton("Receive(2)", 200, 468, 80);
    Button receive3 = startButton("Receive(3)", 310, 468, 80);

    Button pre1 = startButton(tooltip[0][lang], 400, 150, 70);
    Button pre2 = startButton(tooltip[1][lang], 400, 200, 70);
    Button pre3 = startButton(tooltip[2][lang], 400, 250, 70);
    Button custom = startButton(cust[lang], 400, 300, 70);

    Button save = startButton(salvars[lang], 30, 468, 50);
    
    Tooltip t1 = new Tooltip(tooltip[0][lang]);
    Tooltip t2 = new Tooltip(tooltip[1][lang]);
    Tooltip t3 = new Tooltip(tooltip[2][lang]);

    public PreviewButtons(ProcessPreview p1, ProcessPreview p2, ProcessPreview p3) {
        SequentialTransition wait1 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        SequentialTransition wait2 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        SequentialTransition wait3 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        SequentialTransition wait4 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        SequentialTransition wait5 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        SequentialTransition wait6 = new SequentialTransition(new PauseTransition(Duration.millis(500)));
        
        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(add1);
            All.getChildren().add(rem1);
            wait2.play();
        });
        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(add2);
            All.getChildren().add(rem2);
            wait3.play();
        });
        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(add3);
            All.getChildren().add(rem3);
            wait4.play();
        });
        wait4.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(receive1);
            All.getChildren().add(send2);
            All.getChildren().add(receive3);
            wait5.play();
        });
        wait5.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(nop);
            All.getChildren().add(receive2);
            wait6.play();
        });
        wait6.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            All.getChildren().add(send1);

            All.getChildren().add(send3);
        });

        All.getChildren().add(pre1);
        pre1.setTooltip(t1);
        pre1.setOnAction((ActionEvent e) -> {
            p1.preset(1);
            p2.preset(1);
            p3.preset(1);
        });
        All.getChildren().add(pre2);
        pre2.setTooltip(t2);
        pre2.setOnAction((ActionEvent e) -> {
            p1.preset(2);
            p2.preset(2);
            p3.preset(2);
        });
        pre3.setTooltip(t3);
        All.getChildren().add(pre3);
        pre3.setOnAction((ActionEvent e) -> {
            p1.preset(3);
            p2.preset(3);
            p3.preset(3);
        });

        add1.setOnAction((ActionEvent e) -> {
            p1.AddStep();
        });
        rem1.setOnAction((ActionEvent e) -> {
            p1.RemoveStep();
        });
        add2.setOnAction((ActionEvent e) -> {
            p2.AddStep();
        });
        rem2.setOnAction((ActionEvent e) -> {
            p2.RemoveStep();
        });
        add3.setOnAction((ActionEvent e) -> {
            p3.AddStep();
        });
        rem3.setOnAction((ActionEvent e) -> {
            p3.RemoveStep();
        });
        nop.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg("AuxConfig/img/Nop.jpg");
            p1.setSelectedFunc(Nop);
            p2.setSelectedImg("AuxConfig/img/Nop.jpg");
            p2.setSelectedFunc(Nop);
            p3.setSelectedImg("AuxConfig/img/Nop.jpg");
            p3.setSelectedFunc(Nop);
        });
        send1.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg(null);
            p2.setSelectedImg("AuxConfig/img/Send1.jpg");
            p2.setSelectedFunc(Send1);
            p3.setSelectedImg("AuxConfig/img/Send1.jpg");
            p3.setSelectedFunc(Send1);
        });
        send2.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg("AuxConfig/img/Send2.jpg");
            p1.setSelectedFunc(Send2);
            p2.setSelectedImg(null);
            p3.setSelectedImg("AuxConfig/img/Send2.jpg");
            p3.setSelectedFunc(Send2);
        });
        send3.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg("AuxConfig/img/Send3.jpg");
            p1.setSelectedFunc(Send3);
            p2.setSelectedImg("AuxConfig/img/Send3.jpg");
            p2.setSelectedFunc(Send3);
            p3.setSelectedImg(null);
        });
        receive1.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg(null);
            p2.setSelectedImg("AuxConfig/img/Receive1.jpg");
            p2.setSelectedFunc(Receive1);
            p3.setSelectedImg("AuxConfig/img/Receive1.jpg");
            p3.setSelectedFunc(Receive1);
        });
        receive2.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg("AuxConfig/img/Receive2.jpg");
            p1.setSelectedFunc(Receive2);
            p2.setSelectedImg(null);
            p3.setSelectedImg("AuxConfig/img/Receive2.jpg");
            p3.setSelectedFunc(Receive2);
        });
        receive3.setOnAction((ActionEvent e) -> {
            p1.setSelectedImg("AuxConfig/img/Receive3.jpg");
            p1.setSelectedFunc(Receive3);
            p2.setSelectedImg("AuxConfig/img/Receive3.jpg");
            p2.setSelectedFunc(Receive3);
            p3.setSelectedImg(null);
        });

        All.getChildren().add(save);
        save.setOnAction((ActionEvent e) -> {
            if ((p1.checkPreview() == 1) && (p2.checkPreview() == 1) && (p3.checkPreview() == 1)) {
                p1.setInfos();
                p2.setInfos();
                p3.setInfos();
            }
        });

        All.getChildren().add(custom);
        custom.setOnAction((ActionEvent e) -> {
            custom.setDisable(true);
            pre1.setDisable(true);
            pre2.setDisable(true);
            pre3.setDisable(true);
            wait1.play();
        });
    }

    public Group getGroup() {
        return All;
    }

    private Button startButton(String name, double x, double y, double w) {

        Button b = new Button(name);

        DropShadow shadow = new DropShadow();

        b.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            b.setEffect(shadow);
        });
        b.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            b.setEffect(null);
        });

        b.setTranslateX(x);
        b.setTranslateY(y);

        b.setMinWidth(w);

        return b;
    }

}


/*All.getChildren().add(receive0);
             receive0.setOnAction((ActionEvent e) -> {
             p1.setSelectedImg("AuxConfig/img/Receive0.jpg");
             p1.setSelectedFunc(Receive0);
             p2.setSelectedImg("AuxConfig/img/Receive0.jpg");
             p2.setSelectedFunc(Receive0);
             p3.setSelectedImg("AuxConfig/img/Receive0.jpg");
             p3.setSelectedFunc(Receive0);
             });*/
