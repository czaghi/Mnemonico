
/*
 A classe SceneConfig possui apenas metodos estaticos, funcionando como uma
 "classe system" especifica para nossa aplicacao.
 No caso dessa classe, seus metodos recebem todas as informacoes necessasrias
 para instanciar todos os objetos de cada cena.
 Depois disso, a cena funciona "sozinha", ate que o usuario decida mudar de cena.
 (Nesse caso a classe principal que deve cuidar de muda-la.)
 Isso porque o javaFX funciona basicamente apenas a partir de eventos. Que sao
 tratados pelos proprios objetos da cena. Por isso basta instancia-los corretamente.
 */
package MainPkg;

import static AuxConfig.Function_n.*;
import AuxConfig.PreviewButtons;
import AuxConfig.ProcessPreview;
import AuxSimulacao.Process_t;
import Functions.Receive;
import Functions.Send;
import static MainPkg.Languages.LangStrings.*;
import static MainPkg.Mnemônico.lang;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.Pos.BASELINE_CENTER;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneConfig {

    public static int scene_sizeX = 500, scene_sizeY = 500;
    public static int step_preX = 150, step_preY = 300;
    public static int step_spaceX = 100, step_spaceY = 0;
    public static int step_sizeX = 100, step_sizeY = 60;

    public static int autoexec_time = 1000;

    public static int stbstexec_time = 50;

    public static Timeline timer;
    public static int working;
    public static Process_t proc[] = {null, null, null};
    public static ArrayList<Integer> active_p = new ArrayList<>();
    public static int schedule;

    public static boolean hlp = false;

    public Scene titleConfig(Button b1, Button b2, Button b3, Button b4, Stage pS) {

        Group root = new Group();
        Scene title = new Scene(root, scene_sizeX, scene_sizeY);

        ImageView art = new ImageView(new Image("MainPkg/img/art.jpg"));

        ImageView langPt = new ImageView("MainPkg/img/LangPt.jpg");
        ImageView langEng = new ImageView("MainPkg/img/LangEng.jpg");

        langPt.setX(100);
        langPt.setY(450);
        langPt.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Mnemônico.lang = 0;
            pS.setScene(titleConfig(b1, b2, b3, b4, pS));
        });
        langPt.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            title.setCursor(Cursor.HAND);
        });
        langPt.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            title.setCursor(Cursor.DEFAULT);
        });

        langEng.setX(362);
        langEng.setY(450);
        langEng.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Mnemônico.lang = 1;
            pS.setScene(titleConfig(b1, b2, b3, b4, pS));
        });
        langEng.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            title.setCursor(Cursor.HAND);
        });
        langEng.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            title.setCursor(Cursor.DEFAULT);
        });

        b1.setText(iniciar[lang]);
        b2.setText(introducao[lang]);
        b3.setText(creditos[lang]);
        b4.setText(sair[lang]);

        root.getChildren().add(art);
        root.getChildren().add(langPt);
        root.getChildren().add(langEng);
        root.getChildren().add(b1);
        root.getChildren().add(b2);
        root.getChildren().add(b3);
        root.getChildren().add(b4);
        //root.getChildren().add(Mnemônico.mut);

        return title;
    }

    public Scene mainConfig(Button b1, Button b2, Button b3, Button b4, Button b5) {

        Group root = new Group();
        Scene title = new Scene(root, scene_sizeX, scene_sizeY);

        ImageView art = new ImageView(new Image("MainPkg/img/back.jpg"));

        CheckBox helpop = new CheckBox(help[lang]);

        helpop.setTextFill(WHITE);
        helpop.setTranslateX(50);
        helpop.setTranslateY(300);
        helpop.setSelected(hlp);
        helpop.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) -> {
                    hlp = !hlp;
                });

        b1.setText(configsims[lang]);
        b2.setText(configprocs[lang]);
        b3.setText(autosims[lang]);
        b4.setText(stbstsims[lang]);
        b5.setText(voltar[lang]);

        root.getChildren().add(art);
        root.getChildren().add(b1);
        root.getChildren().add(b2);
        root.getChildren().add(b3);
        root.getChildren().add(b4);
        root.getChildren().add(b5);
        root.getChildren().add(helpop);

        return title;
    }

    public Scene creditConfig(Button b1) {

        Group root = new Group();
        Scene scene = new Scene(root, scene_sizeX, scene_sizeY);

        b1.setText(voltar[lang]);

        root.getChildren().add(new ImageView("MainPkg/img/creditos.jpg"));
        root.getChildren().add(b1);

        return scene;
    }

    public Scene introConfig(Button b1) {

        Group root = new Group();
        Scene scene = new Scene(root, scene_sizeX, scene_sizeY);

        ImageView Intros[][] = {{new ImageView("MainPkg/img/introbackPt.jpg"),
            new ImageView("MainPkg/img/introbackEng.jpg")},
        {new ImageView("MainPkg/img/BBPt.jpg"),
            new ImageView("MainPkg/img/BBEng.jpg")},
        {new ImageView("MainPkg/img/nBBPt.jpg"),
            new ImageView("MainPkg/img/nBBEng.jpg")},
        {new ImageView("MainPkg/img/BnBPt.jpg"),
            new ImageView("MainPkg/img/BnBEng.jpg")},
        {new ImageView("MainPkg/img/nBnBPt.jpg"),
            new ImageView("MainPkg/img/nBnBEng.jpg")}};

        Rectangle allback = new Rectangle(500, 500, TRANSPARENT);
        allback.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            root.getChildren().remove(7);
            root.getChildren().remove(6);
        });

        Rectangle nBnB = new Rectangle(320, 20, TRANSPARENT);
        nBnB.setX(110);
        nBnB.setY(170);
        nBnB.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            scene.setCursor(Cursor.HAND);
        });
        nBnB.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        nBnB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            root.getChildren().add(Intros[4][lang]);
            root.getChildren().add(allback);
        });

        Rectangle nBB = new Rectangle(320, 20, TRANSPARENT);
        nBB.setX(110);
        nBB.setY(195);
        nBB.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            scene.setCursor(Cursor.HAND);
        });
        nBB.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        nBB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            root.getChildren().add(Intros[2][lang]);
            root.getChildren().add(allback);
        });

        Rectangle BnB = new Rectangle(320, 20, TRANSPARENT);
        BnB.setX(110);
        BnB.setY(225);
        BnB.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            scene.setCursor(Cursor.HAND);
        });
        BnB.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        BnB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            root.getChildren().add(Intros[3][lang]);
            root.getChildren().add(allback);
        });

        Rectangle BB = new Rectangle(320, 20, TRANSPARENT);
        BB.setX(110);
        BB.setY(250);
        BB.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            scene.setCursor(Cursor.HAND);
        });
        BB.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        BB.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            root.getChildren().add(Intros[1][lang]);
            root.getChildren().add(allback);
        });

        b1.setText(voltar[lang]);

        root.getChildren().add(Intros[0][lang]);
        root.getChildren().add(BB);
        root.getChildren().add(nBB);
        root.getChildren().add(BnB);
        root.getChildren().add(nBnB);
        root.getChildren().add(b1);

        return scene;
    }

    public Scene processPropertiesConfig(Button b1) {

        Group root = new Group();
        Scene scene = new Scene(root, scene_sizeX, scene_sizeY);

        root.getChildren().add(new ImageView("MainPkg/img/back.jpg"));

        ProcessPreview p1;
        ProcessPreview p2;
        ProcessPreview p3;

        if (Infos.backup[0] == null) {
            p1 = new ProcessPreview(0);
        } else {
            p1 = Infos.backup[0];
        }

        if (Infos.backup[1] == null) {
            p2 = new ProcessPreview(1);
        } else {
            p2 = Infos.backup[1];
        }

        if (Infos.backup[2] == null) {
            p3 = new ProcessPreview(2);
        } else {
            p3 = Infos.backup[2];
        }

        PreviewButtons btn = new PreviewButtons(p1, p2, p3);

        timer = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
            Infos.backup[0] = p1;
            Infos.backup[1] = p2;
            Infos.backup[2] = p3;
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        b1.setText(voltar[lang]);

        root.getChildren().add(b1);
        root.getChildren().add(p1.getGroup());
        root.getChildren().add(p2.getGroup());
        root.getChildren().add(p3.getGroup());
        root.getChildren().add(btn.getGroup());

        return scene;
    }

    public Scene simConfig(Button b1) {

        Group root = new Group();
        Scene scene = new Scene(root, scene_sizeX, scene_sizeY);

        Label title = new Label(titles[lang]);
        title.setTranslateX(50);
        title.setTranslateY(50);
        title.setTextFill(WHITE);
        title.setFont(new Font(15));

        CheckBox buffer = new CheckBox(buffers[lang]);
        CheckBox blocking = new CheckBox(Sblockings[lang]);
        CheckBox sync = new CheckBox(Rblockings[lang]);

        buffer.setTextFill(WHITE);
        buffer.setFont(new Font(15));
        buffer.setTranslateX(50);
        buffer.setTranslateY(100);
        buffer.setSelected(FunctionHandler.buffer);
        buffer.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) -> {
                    FunctionHandler.buffer = !FunctionHandler.buffer;
                });

        blocking.setTextFill(WHITE);
        blocking.setFont(new Font(15));
        blocking.setTranslateX(50);
        blocking.setTranslateY(150);
        blocking.setSelected(FunctionHandler.Sblocking);
        blocking.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) -> {
                    FunctionHandler.Sblocking = !FunctionHandler.Sblocking;
                });

        sync.setTextFill(WHITE);
        sync.setFont(new Font(15));
        sync.setTranslateX(50);
        sync.setTranslateY(200);
        sync.setSelected(FunctionHandler.Rblocking);
        sync.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) -> {
                    FunctionHandler.Rblocking = !FunctionHandler.Rblocking;
                });

        b1.setText(voltar[lang]);

        root.getChildren().add(new ImageView("MainPkg/img/confback.jpg"));
        root.getChildren().add(b1);
        root.getChildren().add(title);
        root.getChildren().add(buffer);
        root.getChildren().add(blocking);
        root.getChildren().add(sync);

        return scene;
    }

    public Scene autoSimulationConfig(Button b1, Label help1, Label help2) {

        int i;

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        FunctionHandler.root = root;

        Rectangle background = new Rectangle(800, 600, WHITE);
        Rectangle foreground = new Rectangle(800, 75, GRAY);

        ImageView kernel[] = {new ImageView("AuxSimulacao/img/kernelPt.jpg"), new ImageView("AuxSimulacao/img/kernelEng.jpg")};
        kernel[lang].setX(150);
        kernel[lang].setY(450);

        root.getChildren().add(background);

        Button start = new Button(iniciars[lang]);
        start.setTranslateX(50);
        start.setTranslateY(20);

        active_p.removeAll(active_p);

        SceneConfig.working = 1;
        
        

        for (i = 0; i < Infos.NoP; i++) {
            if (Infos.Ready[i] == 0) {
                proc[i] = new Process_t(i, Infos.NoT[i], Infos.tamP[i][0], Infos.tamP[i][1]);
            } else {
                proc[i] = create_proc(i);
            }

            if (proc[i].getThread(1).getSize() > 1) {
                root.getChildren().add(proc[i].getThread(1).getNode());
                active_p.add(i);
            }
        }

        timer = new Timeline(new KeyFrame(Duration.seconds(1.1), (ActionEvent e) -> {
            if (Schedule(active_p.size()) == -1) {
                timer.stop();
            } else {
                schedule = active_p.get(Schedule(active_p.size()));
                if (proc[schedule].getFinished() == 0) {
                    proc[schedule].takeNextStep();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        start.setOnAction((ActionEvent e) -> {
            timer.play();
        });

        DropShadow shadow = new DropShadow();
        start.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            start.setEffect(shadow);
        });
        start.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            start.setEffect(null);
        });
        
        help1.setText(autoinit[lang]);
        help2.setText("");

        root.getChildren().add(foreground);
        root.getChildren().add(start);

        FunctionHandler.setRoot(root);

        b1.setText(voltar[lang]);

        root.getChildren().add(kernel[lang]);
        root.getChildren().add(b1);
        if (hlp == true) {
            root.getChildren().add(help1);
        }
        if (hlp == true) {
            root.getChildren().add(help2);
        }
        
        Label buf = new Label();
        Label sbloq = new Label();
        Label rbloq = new Label();
        
        if(FunctionHandler.buffer == true)
            buf.setText(b[0][lang]);
        else
            buf.setText(b[1][lang]);
        
        if(FunctionHandler.Rblocking == true)
            rbloq.setText(rb[0][lang]);
        else
            rbloq.setText(rb[1][lang]);
        
        if(FunctionHandler.Sblocking == true)
            sbloq.setText(sb[0][lang]);
        else
            sbloq.setText(sb[1][lang]);
        
        buf.setTranslateX(20);
        buf.setTranslateY(200);
        //buf.setAlignment(BASELINE_CENTER);
        buf.setFont(new Font(20));
        buf.setMinWidth(100);
        
        rbloq.setTranslateX(20);
        rbloq.setTranslateY(275);
        //rbloq.setAlignment(BASELINE_CENTER);
        rbloq.setFont(new Font(20));
        rbloq.setMinWidth(100);
        
        sbloq.setTranslateX(20);
        sbloq.setTranslateY(350);
        //sbloq.setAlignment(BASELINE_CENTER);
        sbloq.setFont(new Font(20));
        sbloq.setMinWidth(100);
        
        root.getChildren().add(buf);
        root.getChildren().add(rbloq);
        root.getChildren().add(sbloq);

        return scene;
    }

    public Scene stbstSimulationConfig(Button b1, Label help1, Label help2) {

        int i, flag = 0;

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        Rectangle background = new Rectangle(800, 600, WHITE);
        Rectangle foreground = new Rectangle(800, 75, GRAY);

        ImageView kernel[] = {new ImageView("AuxSimulacao/img/kernelPt.jpg"), new ImageView("AuxSimulacao/img/kernelEng.jpg")};
        kernel[lang].setX(150);
        kernel[lang].setY(450);
        
        Label buf = new Label();
        Label sbloq = new Label();
        Label rbloq = new Label();

        root.getChildren().add(background);

        Button st1 = new Button("Step!");
        st1.setTranslateX(step_preX);
        st1.setTranslateY(25);
        st1.setMinWidth(step_sizeX);

        Button st2 = new Button("Step!");
        st2.setTranslateX(step_preX + step_spaceX + step_sizeX);
        st2.setTranslateY(25);
        st2.setMinWidth(step_sizeX);

        Button st3 = new Button("Step!");
        st3.setTranslateX(step_preX + 2 * step_spaceX + 2 * step_sizeX);
        st3.setTranslateY(25);
        st3.setMinWidth(step_sizeX);

        SequentialTransition wait = new SequentialTransition(
                new PauseTransition(Duration.millis(autoexec_time + 100))
        );

        wait.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            st1.setDisable(false);
            st2.setDisable(false);
            st3.setDisable(false);
        });

        SceneConfig.working = 1;
        SceneConfig.timer = null;

        for (i = 0; i < Infos.NoP; i++) {
            if (Infos.Ready[i] == 0) {
                proc[i] = new Process_t(i, Infos.NoT[i], Infos.tamP[i][0], Infos.tamP[i][1]);
            } else {
                proc[i] = create_proc(i);
            }

            if (proc[i].getThread(1).getSize() > 1) {
                flag = 1;
                root.getChildren().add(proc[i].getThread(1).getNode());
                active_p.add(i);
            }
        }

        st1.setOnAction((ActionEvent e) -> {
            if (SceneConfig.working == 1) {
                proc[0].takeNextStep();
                //st1.setDisable(true);
                //st2.setDisable(true);
                //st3.setDisable(true);
                //wait.play();
            }
        });
        st2.setOnAction((ActionEvent e) -> {
            if (SceneConfig.working == 1) {
                proc[1].takeNextStep();
                //st1.setDisable(true);
                //st2.setDisable(true);
                //st3.setDisable(true);
                //wait.play();
            }
        });
        st3.setOnAction((ActionEvent e) -> {
            if (SceneConfig.working == 1) {
                proc[2].takeNextStep();
                //st1.setDisable(true);
                //st2.setDisable(true);
                //st3.setDisable(true);
                //wait.play();
            }
        });

        root.getChildren().add(foreground);
        root.getChildren().add(st1);
        root.getChildren().add(st2);
        root.getChildren().add(st3);

        FunctionHandler.setRoot(root);

        b1.setText(voltar[lang]);

        help1.setText(sbshinit[lang]);
        help2.setText("");

        root.getChildren().add(kernel[lang]);
        root.getChildren().add(b1);
        if (hlp == true) {
            root.getChildren().add(help1);
        }
        if (hlp == true) {
            root.getChildren().add(help2);
        }
        
        if(FunctionHandler.buffer == true)
            buf.setText(b[0][lang]);
        else
            buf.setText(b[1][lang]);
        
        if(FunctionHandler.Rblocking == true)
            rbloq.setText(rb[0][lang]);
        else
            rbloq.setText(rb[1][lang]);
        
        if(FunctionHandler.Sblocking == true)
            sbloq.setText(sb[0][lang]);
        else
            sbloq.setText(sb[1][lang]);
        
        buf.setTranslateX(20);
        buf.setTranslateY(200);
        //buf.setAlignment(BASELINE_CENTER);
        buf.setFont(new Font(20));
        buf.setMinWidth(100);
        
        rbloq.setTranslateX(20);
        rbloq.setTranslateY(275);
        //rbloq.setAlignment(BASELINE_CENTER);
        rbloq.setFont(new Font(20));
        rbloq.setMinWidth(100);
        
        sbloq.setTranslateX(20);
        sbloq.setTranslateY(350);
        //sbloq.setAlignment(BASELINE_CENTER);
        sbloq.setFont(new Font(20));
        sbloq.setMinWidth(100);
        
        root.getChildren().add(buf);
        root.getChildren().add(rbloq);
        root.getChildren().add(sbloq);

        return scene;
    }

    public static void blinkProc(int n, boolean block, Group root) {
        Rectangle blink = new Rectangle();
        blink.setX(step_preX + n * step_sizeX + n * step_spaceX - 10);
        blink.setY(step_preY - (proc[n].getThread(1).getSize() - proc[n].getThread(1).getIteration() - 1) * step_sizeY - 10);
        blink.setWidth(step_sizeX + 20);
        blink.setHeight((proc[n].getThread(1).getSize() - proc[n].getThread(1).getIteration()) * step_sizeY + 20);

        if (block == true) {
            blink.setFill(RED);
        } else {
            blink.setFill(LAWNGREEN);
        }
        blink.setEffect(new BoxBlur(20, 20, 10));

        SequentialTransition wait = new SequentialTransition(
                new PauseTransition(Duration.millis(200))
        );

        root.getChildren().add(1, blink);

        wait.play();

        wait.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            root.getChildren().remove(1);
        });
    }

    private int Schedule(int n) {

        if (n > 0) {
            return (int) (Math.random() * n);
        }

        return -1;
    }

    private Process_t create_proc(int n) {

        int i;
        Process_t newproc = new Process_t(n, Infos.NoT[n], Infos.tamP[n][0], Infos.tamP[n][1]);

        for (i = 0; i < Infos.tamP[n][0] - 1; i++) {
            if (Infos.func[n][i] == Send1) {
                newproc.getThread(1).setStep(i, new Send(newproc.getThread(0), 0));
            } else if (Infos.func[n][i] == Send2) {
                newproc.getThread(1).setStep(i, new Send(newproc.getThread(0), 1));
            } else if (Infos.func[n][i] == Send3) {
                newproc.getThread(1).setStep(i, new Send(newproc.getThread(0), 2));
            } else if (Infos.func[n][i] == Receive0) {
                newproc.getThread(1).setStep(i, new Receive(newproc.getThread(0), -1));
            } else if (Infos.func[n][i] == Receive1) {
                newproc.getThread(1).setStep(i, new Receive(newproc.getThread(0), 0));
            } else if (Infos.func[n][i] == Receive2) {
                newproc.getThread(1).setStep(i, new Receive(newproc.getThread(0), 1));
            } else if (Infos.func[n][i] == Receive3) {
                newproc.getThread(1).setStep(i, new Receive(newproc.getThread(0), 2));
            }
        }

        return newproc;
    }
}
