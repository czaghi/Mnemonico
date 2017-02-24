
/*
 Classe principal, sera usada apenas para alternar entre cenas quando necessario.
 Tudo que precisar ser configurado, para montar a cena, sera feito nas classes
 auxiliars (AuxSimulacao) com o apoio dos metodos da classe SceneConfig.

 Por enquanto, estou usando para testar se as classes funcionam, e qualquer duvida
 que apareca sobre o uso do javaFX em si.
 */
package MainPkg;

import static MainPkg.Languages.LangStrings.*;
import static MainPkg.SceneConfig.hlp;
import java.io.File;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import static javafx.geometry.Pos.BASELINE_CENTER;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.*;
import static javafx.scene.paint.Color.WHITE;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Mnemônico extends Application {

    public static int lang;

    public static CheckBox mut = new CheckBox(m[lang]);

    public static boolean mt = true;

    SceneConfig sceneconfig = new SceneConfig();

    private static final Label sbshelp1 = new Label();
    private static final Label sbshelp2 = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pS) {

        lang = 0;
        
        ClassLoader cl = new ClassLoader() {};

        //AudioClip music = new AudioClip(new Media(new File("Mnemônico - tema.wav").toURI().toString()).getSource());
        //music.setCycleCount(Timeline.INDEFINITE);

        //====================================================================//
        // Botões da tela inicial        
        Button start = startButton(iniciar[lang], 50, 400);
        Button intro = startButton(introducao[lang], 158, 400);
        Button cred = startButton(creditos[lang], 287, 400);
        Button exit = startButton(sair[lang], 410, 400);

        
        mut.setTextFill(WHITE);
        mut.setTranslateX(20);
        mut.setTranslateY(20);
        mut.setSelected(hlp);
        mut.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov,
                        Boolean old_val, Boolean new_val) -> {
                    mt = !mt;
                    if (mt == true) {
                        //music.stop();
                    } else {
                        //music.play();
                    }
                });
          

        //====================================================================//
        // Botões do menu
        Button configsim = startButton(configsims[lang], 50, 100);
        Button configproc = startButton(configprocs[lang], 50, 150);
        Button autosim = startButton(autosims[lang], 50, 200);
        Button stbstsim = startButton(stbstsims[lang], 50, 250);
        Button mainback = startButton(voltar[lang], 393, 400);

        //====================================================================//
        // Botões da intro
        Button introback = startButton(voltar[lang], 5, 468);

        //====================================================================//
        //Botões da simulação automatica
        Button autosimback = startButton(voltar[lang], 693, 20);

        //====================================================================//
        //Botões da simulação sbs
        Button stbstsimback = startButton(voltar[lang], 5, 568);

        //====================================================================//
        // Botões dos cred
        Button credback = startButton(voltar[lang], 5, 468);

        //====================================================================//
        // Botões da configuração de simulação
        Button simback = startButton(voltar[lang], 5, 468);

        //====================================================================//
        // Botões da configuração de processos
        Button ppback = startButton(voltar[lang], 400, 468);

        //====================================================================//
        start.setOnAction((ActionEvent e) -> {
            pS.setTitle(menu[lang]);
            pS.setScene(sceneconfig.mainConfig(configsim, configproc, autosim, stbstsim, mainback));
        });
        intro.setOnAction((ActionEvent e) -> {
            pS.setTitle(intros[lang]);
            pS.setScene(sceneconfig.introConfig(introback));
        });
        cred.setOnAction((ActionEvent e) -> {
            pS.setTitle(creds[lang]);
            pS.setScene(sceneconfig.creditConfig(credback));
        });
        exit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });

        configsim.setOnAction((ActionEvent e) -> {
            pS.setTitle(confsim[lang]);
            pS.setScene(sceneconfig.simConfig(simback));
        });
        configproc.setOnAction((ActionEvent e) -> {
            pS.setTitle(confproc[lang]);
            pS.setScene(sceneconfig.processPropertiesConfig(ppback));
        });
        autosim.setOnAction((ActionEvent e) -> {
            pS.setTitle(simulando[lang]);
            pS.setScene(sceneconfig.autoSimulationConfig(autosimback, sbshelp1, sbshelp2));
        });
        stbstsim.setOnAction((ActionEvent e) -> {
            pS.setTitle(simulando[lang]);
            pS.setScene(sceneconfig.stbstSimulationConfig(stbstsimback, sbshelp1, sbshelp2));
        });
        mainback.setOnAction((ActionEvent e) -> {
            pS.setTitle(bemvindo[lang]);
            pS.setScene(sceneconfig.titleConfig(start, intro, cred, exit, pS));
        });

        introback.setOnAction((ActionEvent e) -> {
            pS.setTitle(bemvindo[lang]);
            pS.setScene(sceneconfig.titleConfig(start, intro, cred, exit, pS));
        });

        autosimback.setOnAction((ActionEvent e) -> {
            pS.setTitle(menu[lang]);
            pS.setScene(sceneconfig.mainConfig(configsim, configproc, autosim, stbstsim, mainback));
            if (SceneConfig.timer != null) {
                SceneConfig.timer.stop();
                SceneConfig.timer = null;
            }
        });

        sbshelp1.setMinWidth(600);
        sbshelp1.setAlignment(BASELINE_CENTER);
        sbshelp1.setTranslateX(100);
        sbshelp1.setTranslateY(530);
        sbshelp1.setFont(new Font(25));

        sbshelp2.setMinWidth(600);
        sbshelp2.setAlignment(BASELINE_CENTER);
        sbshelp2.setTranslateX(100);
        sbshelp2.setTranslateY(560);
        sbshelp2.setFont(new Font(25));

        stbstsimback.setOnAction((ActionEvent e) -> {
            pS.setTitle(menu[lang]);
            pS.setScene(sceneconfig.mainConfig(configsim, configproc, autosim, stbstsim, mainback));
        });

        credback.setOnAction((ActionEvent e) -> {
            pS.setTitle(bemvindo[lang]);
            pS.setScene(sceneconfig.titleConfig(start, intro, cred, exit, pS));
        });

        simback.setOnAction((ActionEvent e) -> {
            pS.setTitle(menu[lang]);
            pS.setScene(sceneconfig.mainConfig(configsim, configproc, autosim, stbstsim, mainback));
        });

        ppback.setOnAction((ActionEvent e) -> {
            pS.setTitle(menu[lang]);
            pS.setScene(sceneconfig.mainConfig(configsim, configproc, autosim, stbstsim, mainback));
        });

        //====================================================================//
        pS.setScene(sceneconfig.titleConfig(start, intro, cred, exit, pS));
        pS.setTitle(bemvindo[lang]);

        pS.show();
    }

    public static void setSbsHelp1(String newString) {
        sbshelp1.setText(newString);
    }

    public static void setSbsHelp2(String newString) {
        sbshelp2.setText(newString);
    }

    public static Button startButton(String name, double x, double y) {

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

        return b;
    }
}
