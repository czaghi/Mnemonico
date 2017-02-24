
/*
 A funcao function Handler tem acesso as opcoes de configuracao do usuario, em
 relacao a simulacao. A partir disso, quando uma thread, dentro de um processo
 deseja executar uma de suas funcoes, basta chamar HandleFunction(funcao);
 Como a propria funcao tem a referencia para a thread que a possui ela tomara
 a decisao de bloquear ou nao, dependendo do estado da simulacao.
 */
package MainPkg;

import AuxSimulacao.Message;
import Functions.*;
import static MainPkg.Languages.LangStrings.*;
import static MainPkg.SceneConfig.autoexec_time;
import static MainPkg.SceneConfig.proc;
import static MainPkg.Mnemônico.lang;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class FunctionHandler {

    public static boolean buffer = false;
    public static boolean Sblocking = false;
    public static boolean Rblocking = false;

    public static Group root;

    private static Function function;

    private static int time;

    public static void setRoot(Group root) {
        FunctionHandler.root = root;
    }

    public static void HandleFunction(Function function) {

        if (SceneConfig.hlp) {
            time = 2 * autoexec_time;
        } else {
            time = autoexec_time;
        }

        FunctionHandler.function = function;

        if (function == null) {
            return;
        } else if (function.getName() == "Nop") {
            HandleNop();
        } else if (function.getName() == "Exit") {
            HandleExit();
        } else if (function.getName() == "Send") {
            if (Sblocking == true) {
                if (buffer == true) {
                    HandleSend11();
                } else {
                    HandleSend01();
                }
            } else {
                if (buffer == true) {
                    HandleSend10();
                } else {
                    HandleSend00();
                }
            }
        } else if (function.getName() == "Receive") {
            if (Rblocking == true) {
                if (buffer == true) {
                    HandleReceive11();
                } else {
                    HandleReceive01();
                }
            } else {
                if (buffer == true) {
                    HandleReceive10();
                } else {
                    HandleReceive00();
                }
            }
        }
    }

    private static void HandleNop() {
        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(autoexec_time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        if (lang == 0) {
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("Processo " + (function.getOwner().getNumber() + 1) + " está executando seu codigo.");
        } else {
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("Process " + (function.getOwner().getNumber() + 1) + " is executing its code.");
        }

        function.getOwner().takeNextStep();
        SceneConfig.blinkProc(function.getOwner().getNumber(), false, root);

        wait1.play();

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleExit() {
        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(autoexec_time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        Integer obj = new Integer(function.getOwner().getNumber());

        if (lang == 0) {
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("Processo " + (function.getOwner().getNumber() + 1) + " está finalizando.");
        } else {
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("Process " + (function.getOwner().getNumber() + 1) + " is ending.");
        }

        function.getOwner().takeNextStep();

        SceneConfig.active_p.remove(obj);
        function.getOwner().Finish();

        wait1.play();

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    //========================================================================//
    private static void HandleSend00() {
        Send func = (Send) function;
        Message msg = new Message(func.getOwner().getNumber(), func.getReceiver(), 0, SceneConfig.hlp);
        Message ack = new Message(func.getReceiver(), func.getOwner().getNumber(), 1, SceneConfig.hlp);

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        root.getChildren().add(msg.getImage());
        msg.Send().play();

        //chegou o send não bloqueante e mandou mensagem
        if (lang == 0) {
            Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1));
            Mnemônico.setSbsHelp2(" está enviando uma mensagem ao processo " + (func.getReceiver() + 1));
        } else {
            Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1));
            Mnemônico.setSbsHelp2(" is sending a message to the process " + (func.getReceiver() + 1));
        }

        wait1.play();

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            if (proc[func.getReceiver()].getThread(1).getStep().getFunction().getName() == "Receive") {
                if (((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == -1
                        || ((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == func.getOwner().getNumber()) {
                    proc[func.getReceiver()].getThread(1).getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
                    root.getChildren().add(ack.getImage());
                    ack.Send().play();
                    //A outra  função é receive e responde o ack (o send n espera)
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " está enviando a confirmação ao processo " + (func.getOwner().getNumber() + 1));
                        Mnemônico.setSbsHelp2("Processo " + (func.getOwner().getNumber() + 1) + " não espera pela confirmação");
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " is sending a ACK to the process " + (func.getOwner().getNumber() + 1));
                        Mnemônico.setSbsHelp2("Process " + (func.getOwner().getNumber() + 1) + " doesn't wait for ACK");
                    }

                    wait2.play();
                } else {
                    //A outra func n é receive e a  mensagem é perdida
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("");
                        Mnemônico.setSbsHelp2("Não há garantia de entrega");
                    } else {
                        Mnemônico.setSbsHelp1("");
                        Mnemônico.setSbsHelp2("No delivery guarantee");
                    }
                    if (SceneConfig.timer != null) {
                        SceneConfig.timer.play();
                    } else {
                        SceneConfig.working = 1;
                    }
                }
            } else {
                //A outra func n é receive e a  mensagem é perdida
                if (lang == 0) {
                    Mnemônico.setSbsHelp1("");
                    Mnemônico.setSbsHelp2("A mensagem foi perdida");
                } else {
                    Mnemônico.setSbsHelp1("");
                    Mnemônico.setSbsHelp2("The message was lost");
                }
                if (SceneConfig.timer != null) {
                    SceneConfig.timer.play();
                } else {
                    SceneConfig.working = 1;
                }
            }
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getReceiver(), false, root);
            proc[func.getReceiver()].getThread(1).takeNextStep();
            //A outra função é receive e a mensagem não é  perdida
            if (lang == 0) {
                Mnemônico.setSbsHelp1("");
                Mnemônico.setSbsHelp2("Processo " + (func.getReceiver() + 1) + " continua a execução");
            } else {
                Mnemônico.setSbsHelp1("");
                Mnemônico.setSbsHelp2("Process " + (func.getReceiver() + 1) + " keeps on running");
            }

            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleSend01() {
        Send func = (Send) function;
        Message req = new Message(func.getOwner().getNumber(), func.getReceiver(), 2, SceneConfig.hlp);
        Message ack = new Message(func.getReceiver(), func.getOwner().getNumber(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getOwner().getNumber(), func.getReceiver(), 0, SceneConfig.hlp);

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait4 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        if (proc[func.getReceiver()].getPreq(func.getOwner().getNumber()) == 0) {
            proc[func.getReceiver()].addPreq(func.getOwner().getNumber());
            root.getChildren().add(req.getImage());
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            req.Send().play();
            //O send chegou pela primeira  vez e manda request
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " envia uma requisição ao processo " + (func.getReceiver() + 1));
                Mnemônico.setSbsHelp2("");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " sends a request to process " + (func.getReceiver() + 1));
                Mnemônico.setSbsHelp2("");
            }

            wait1.play();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), true, root);
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            //O send estábloqueado pq ja mandou o request antes (esperando ack)
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " está bloqueado esperando");
                Mnemônico.setSbsHelp2("confirmação do processo " + (func.getReceiver() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " is blocked waiting");
                Mnemônico.setSbsHelp2("ACK from " + (func.getReceiver() + 1));
            }
            return;
        }

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (proc[func.getReceiver()].getThread(1).getStep().getFunction().getName() == "Receive") {
                if (((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == -1
                        || ((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == func.getOwner().getNumber()) {
                    proc[func.getReceiver()].remPreq(func.getOwner().getNumber());
                    root.getChildren().add(ack.getImage());
                    ack.Send().play();
                    wait2.play();
                    //A outra função é receive e responde o req com ack (nesse momento aidan ta bloqueado esperando ack)
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " responde a requisicao do processo " + (func.getOwner().getNumber() + 1));
                        Mnemônico.setSbsHelp2("com uma confirmação");
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " answer the request from " + (func.getOwner().getNumber() + 1));
                        Mnemônico.setSbsHelp2("with an ACK");
                    }
                } else {
                    if (SceneConfig.timer != null) {
                        SceneConfig.timer.play();
                    } else {
                        SceneConfig.working = 1;
                    }
                    //A outra n é  receive e  a função bloqueia
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " está bloqueado esperando");
                        Mnemônico.setSbsHelp2("confirmação do processo " + (func.getReceiver() + 1));
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " is blocked waiting");
                        Mnemônico.setSbsHelp2("ACK from " + (func.getReceiver() + 1));
                    }

                    return;
                }
            } else {
                if (SceneConfig.timer != null) {
                    SceneConfig.timer.play();
                } else {
                    SceneConfig.working = 1;
                }
                //A outra n é  receive e  a função bloqueia
                if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " está bloqueado esperando");
                        Mnemônico.setSbsHelp2("confirmação do processo " + (func.getReceiver() + 1));
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " is blocked waiting");
                        Mnemônico.setSbsHelp2("ACK from " + (func.getReceiver() + 1));
                    }
                return;
            }
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            root.getChildren().add(msg.getImage());
            msg.Send().play();
            //Osend recebeu o ack e manda a msg
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " recebeu a confirmação");
                Mnemônico.setSbsHelp2("e envia a mensagem ao processo " + (func.getReceiver() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " received the ACK");
                Mnemônico.setSbsHelp2("and sends a message to the process " + (func.getReceiver() + 1));
            }

            wait3.play();
        });

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            ack.Send().play();
            proc[func.getReceiver()].getThread(1).getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            //O receive recebe a mensgem e amnda o segudo ack(send bloqueado)
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " recebeu a mensagem");
                Mnemônico.setSbsHelp2("e enviou confirmação de entrega ao processo " + (func.getOwner().getNumber() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " received the message");
                Mnemônico.setSbsHelp2("and sent an ACK of delivery to the process " + (func.getOwner().getNumber() + 1));
            }
            wait4.play();
        });

        wait4.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            SceneConfig.blinkProc(func.getReceiver(), false, root);
            proc[func.getReceiver()].getThread(1).takeNextStep();
            //O send  recebe o ack e os dois processos andam
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Os dois processos processos continuam executando");
                Mnemônico.setSbsHelp2("");
            } else {
                Mnemônico.setSbsHelp1("Both processes keep on running");
                Mnemônico.setSbsHelp2("");
            }
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleSend10() {
        Send func = (Send) function;
        Message msg = new Message(func.getOwner().getNumber(), func.getReceiver(), 0, SceneConfig.hlp);
        Message ack = new Message(func.getOwner().getNumber(), func.getOwner().getNumber(), 1, SceneConfig.hlp);

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );
        SequentialTransition wait4 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait5 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }
        //O send envia a mensagem pro seu buffer e n espera ack (buffer ainda manda)
        if (lang == 0) {
            Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " envia uma mensagem ao seu buffer");
            Mnemônico.setSbsHelp2("");
        } else {
            Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " sends a message to it's buffer");
            Mnemônico.setSbsHelp2("");
        }
        root.getChildren().add(msg.getImage());
        msg.SendToBuffer().play();
        wait4.play();

        wait4.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Buffer envia uma confirmação de entrega");
                Mnemônico.setSbsHelp2("mas o processo não espera por uma");
            } else {
                Mnemônico.setSbsHelp1("Buffer sends an ACK of delivery");
                Mnemônico.setSbsHelp2("but the process doesn't wait for one");
            }
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            root.getChildren().add(ack.getImage());
            ack.SendFromBuffer().play();
            wait1.play();
            wait5.play();
        });

        wait5.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            proc[func.getReceiver()].getThread(1).getBuffer().addMessage();
        });

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            //O kernel envia a mensagem ao outro buffer
            if (lang == 0) {
                Mnemônico.setSbsHelp1("O sistema operacional envia a mensagem do");
                Mnemônico.setSbsHelp2("buffer do processo " + (func.getOwner().getNumber() + 1) + " para o do buffer do processo " + (func.getReceiver() + 1));
            } else {
                Mnemônico.setSbsHelp1("The operating system sends the message from");
                Mnemônico.setSbsHelp2("the process " + (func.getOwner().getNumber() + 1) + "'s buffer" + " to the process " + (func.getReceiver() + 1) + "'s buffer");
            }
            wait2.play();
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {

            if (proc[func.getReceiver()].getThread(1).getStep().getFunction().getName() == "Receive") {
                if (((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == -1
                        || ((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == func.getOwner().getNumber()) {
                    msg.SendFromBuffer().play();
                    //A outra função é receive e já pega a msg do seu buffer
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " recebe a mensagem do seu buffer");
                        Mnemônico.setSbsHelp2("e continua a execução");
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " receives the message from it's buffer");
                        Mnemônico.setSbsHelp2("and continues the executing");
                    }

                    wait3.play();
                }
            } else {
                //A outra função neh receive e a mensagem fica esperando no seu buffer
                if (lang == 0) {
                    Mnemônico.setSbsHelp1("A mensagem fica armazenada no buffer");
                    Mnemônico.setSbsHelp2("esperando o receive() adequado");
                } else {
                    Mnemônico.setSbsHelp1("The message is stored in the buffer");
                    Mnemônico.setSbsHelp2("waiting the appropriate receive()");
                }

                proc[func.getReceiver()].addPmsg(func.getOwner().getNumber());
                //proc[func.getReceiver()].getThread(1).getBuffer().addMessage();
                if (SceneConfig.timer != null) {
                    SceneConfig.timer.play();
                } else {
                    SceneConfig.working = 1;
                }
                return;
            }
        });

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            proc[func.getReceiver()].getThread(1).getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            SceneConfig.blinkProc(func.getReceiver(), false, root);
            proc[func.getReceiver()].getThread(1).takeNextStep();
            //O receive pegou a mensagem
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " recebe a mensagem do seu buffer");
                Mnemônico.setSbsHelp2("e continua a execução");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " receive the message from its buffer");
                Mnemônico.setSbsHelp2("and keeps on running");
            }

            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleSend11() {
        Send func = (Send) function;
        Message req = new Message(func.getOwner().getNumber(), func.getReceiver(), 2, SceneConfig.hlp);
        Message ack = new Message(func.getReceiver(), func.getOwner().getNumber(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getOwner().getNumber(), func.getReceiver(), 0, SceneConfig.hlp);

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );
        SequentialTransition wait4 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        if (proc[func.getReceiver()].getPreq(func.getOwner().getNumber()) == 0) {
            proc[func.getReceiver()].addPreq(func.getOwner().getNumber());
            root.getChildren().add(msg.getImage());
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            msg.SendToBuffer().play();
            //O send manda msg ao proprio buffer
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " envia a mensagem ao seu buffer");
                Mnemônico.setSbsHelp2("");
            } else {
                Mnemônico.setSbsHelp1("Processs " + (func.getOwner().getNumber() + 1) + " sends the message to its buffer");
                Mnemônico.setSbsHelp2("");
            }

            wait1.play();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), true, root);
            //O send já havia mandado msg (acho que nunca cai nesse caso, depois tenho q ver)
            if (lang == 0) {
                Mnemônico.setSbsHelp1("AEAEAEAEAEAEAEAEAEAE");
                Mnemônico.setSbsHelp2("AEAEAEAEAEAEAEAEAEAE");
            } else {
                Mnemônico.setSbsHelp1("AEAEAEAEAEAEAEAEAEAE");
                Mnemônico.setSbsHelp2("AEAEAEAEAEAEAEAEAEAE");
            }

            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        }

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            //O buffer (do send) responde a msg com ack
            //O kernel pega a mensagem e manda ao buffer do outro (e garante q vai chegar)
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Buffer envia confirmação de entrega");
                Mnemônico.setSbsHelp2("S.O. envia a mensagem de um buffer para outro");
            } else {
                Mnemônico.setSbsHelp1("Buffer sends an ACK of delivery");
                Mnemônico.setSbsHelp2("O.S. sends the message from buffer to another");
            }

            root.getChildren().add(ack.getImage());
            ack.SendFromBuffer().play();
            wait2.play();
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            if (proc[func.getReceiver()].getThread(1).getStep().getFunction().getName() == "Receive") {
                if (((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == -1
                        || ((Receive) proc[func.getReceiver()].getThread(1).getStep().getFunction()).getSender() == func.getOwner().getNumber()) {
                    msg.SendFromBuffer().play();
                    //A outra mensagem é receive e pega a mensagem de seu buffer (eh escalonada)
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " recebe a mensagem do seu buffer");
                        Mnemônico.setSbsHelp2("e continua a execução");
                    } else {
                        Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " receive the message from it's buffer");
                        Mnemônico.setSbsHelp2("and continues executing");
                    }

                    wait3.play();
                } else {
                    proc[func.getReceiver()].remPreq(func.getOwner().getNumber());
                    proc[func.getReceiver()].addPmsg(func.getOwner().getNumber());
                    proc[func.getReceiver()].getThread(1).getBuffer().addMessage();
                    if (SceneConfig.timer != null) {
                        SceneConfig.timer.play();
                    } else {
                        SceneConfig.working = 1;
                    }
                    //A outra funçao nao eh receive e a mensagem fica guardada no seu buffer
                    if (lang == 0) {
                        Mnemônico.setSbsHelp1("A mensagem fica armazenada no buffer");
                        Mnemônico.setSbsHelp2("esperando o receive() adequado");
                    } else {
                        Mnemônico.setSbsHelp1("The message is stored in the buffer");
                        Mnemônico.setSbsHelp2("waiting the appropriate receive()");
                    }
                    return;
                }
            } else {
                proc[func.getReceiver()].remPreq(func.getOwner().getNumber());
                proc[func.getReceiver()].addPmsg(func.getOwner().getNumber());
                proc[func.getReceiver()].getThread(1).getBuffer().addMessage();
                if (SceneConfig.timer != null) {
                    SceneConfig.timer.play();
                } else {
                    SceneConfig.working = 1;
                }
                //A outra funçao nao eh receive e a mensagem fica guardada no seu buffer
                if (lang == 0) {
                    Mnemônico.setSbsHelp1("A mensagem fica armazenada no buffer");
                    Mnemônico.setSbsHelp2("esperando o receive() adequado");
                } else {
                    Mnemônico.setSbsHelp1("The message is stored in the buffer");
                    Mnemônico.setSbsHelp2("waiting the appropriate receive()");
                }
                return;
            }
        });

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getReceiver(), false, root);
            proc[func.getReceiver()].remPreq(func.getOwner().getNumber());
            proc[func.getReceiver()].getThread(1).getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            //A mensagem chegou no receive e ele anda
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getReceiver() + 1) + " recebe a mensagem do seu buffer");
                Mnemônico.setSbsHelp2("e continua a execução");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getReceiver() + 1) + " receive the message from it's buffer");
                Mnemônico.setSbsHelp2("and continues executing");
            }
            proc[func.getReceiver()].getThread(1).takeNextStep();
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        });
        /*
         wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
         proc[func.getReceiver()].getThread(1).takeNextStep();
         if (SceneConfig.timer != null) {
         SceneConfig.timer.play();
         } else {
         SceneConfig.working = 1;
         }
         return;
         });*/
    }

    //========================================================================//
    private static void HandleReceive00() {
        Receive func = (Receive) function;
        int sender, flag = 0;

        Message ack = new Message(func.getOwner().getNumber(), func.getSender(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getSender(), func.getOwner().getNumber(), 0, SceneConfig.hlp);

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait4 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (proc[func.getOwner().getNumber()].getPmsg(func.getSender()) > 0) {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            proc[func.getOwner().getNumber()].remPmsg(0);
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("");
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        }

        if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(0) == 1) {
            sender = 0;
        } else if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(1) == 1) {
            sender = 1;
        } else if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(2) == 1) {
            sender = 2;
        } else if (proc[func.getOwner().getNumber()].getPreq(func.getSender()) == 1) {
            sender = func.getSender();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            //Não há requests enviados e o receive não espera por mensagens
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Não há nenhum request pendente");
                Mnemônico.setSbsHelp2("Receive() foi perdido");
            } else {
                Mnemônico.setSbsHelp1("There isn't pending request");
                Mnemônico.setSbsHelp2("Receive() was lost");
            }
            sender = 0;
            flag = 1;
        }
        if (flag == 0) {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            proc[func.getOwner().getNumber()].remPreq(sender);
            root.getChildren().add(ack.getImage());
            ack.Send().play();
            //O receive responde o req que já havia sido  enviado
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " responde a requisição do processo " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("com uma confirmação");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " answer the request from " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("with an ACK");
            }
            wait1.play();
        } else {
            wait4.play();
        }

        wait4.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        });

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            root.getChildren().add(msg.getImage());
            msg.Send().play();
            //O send (que estava bloqueado) envia mensagem ao processo
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("está enviando a mensagem ao processo " + (func.getOwner().getNumber() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("is sending the message to the process " + (func.getOwner().getNumber() + 1));
            }
            wait2.play();
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            ack.Send().play();
            func.getOwner().getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            //O receive recebe a mensagem e manda ack
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " envia uma confirmação de entrega");
                Mnemônico.setSbsHelp2("para o processo " + (func.getSender() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " sends an ACK of delivery");
                Mnemônico.setSbsHelp2("to the process " + (func.getSender() + 1));
            }

            wait3.play();
        });

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            SceneConfig.blinkProc(func.getSender(), false, root);
            func.getOwner().takeNextStep();
            proc[func.getSender()].getThread(1).takeNextStep();
            //Os  dois processos continuam
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Os dois processos processos continuam executando");
                Mnemônico.setSbsHelp2("");
            } else {
                Mnemônico.setSbsHelp1("Both processes keep executing");
                Mnemônico.setSbsHelp2("");
            }

            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleReceive01() {
        Receive func = (Receive) function;
        int sender;

        Message ack = new Message(func.getOwner().getNumber(), func.getSender(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getSender(), func.getOwner().getNumber(), 0, SceneConfig.hlp);

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (proc[func.getOwner().getNumber()].getPmsg(func.getSender()) == 1) {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().takeNextStep();
            proc[func.getOwner().getNumber()].remPmsg(0);
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("");
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        }

        if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(0) == 1) {
            sender = 0;
        } else if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(1) == 1) {
            sender = 1;
        } else if (func.getSender() == -1 && proc[func.getOwner().getNumber()].getPreq(2) == 1) {
            sender = 2;
        } else if (proc[func.getOwner().getNumber()].getPreq(func.getSender()) == 1) {
            sender = func.getSender();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), true, root);
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            //Não há reqs e o receive bloqueia
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Não há requisições pendentes");
                Mnemônico.setSbsHelp2("Processo " + (func.getOwner().getNumber() + 1) + " está bloqueado");
            } else {
                Mnemônico.setSbsHelp1("There are no pending requests");
                Mnemônico.setSbsHelp2("Process " + (func.getOwner().getNumber() + 1) + " is blocked");
            }

            return;
        }

        SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
        proc[func.getOwner().getNumber()].remPreq(sender);
        root.getChildren().add(ack.getImage());
        ack.Send().play();
        //Há pelo menos um req pendente e o receive envia um ack ao sender
        if (lang == 0) {
            Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " envia uma confirmação de requisição");
            Mnemônico.setSbsHelp2("");
        } else {
            Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " sends an ACK of request");
            Mnemônico.setSbsHelp2("");
        }
        wait1.play();

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            root.getChildren().add(msg.getImage());
            msg.Send().play();
            //O sender recebe o ack e envia a mensagem ao processo
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("está enviando a mensagem ao processo " + (func.getOwner().getNumber() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getSender() + 1));
                Mnemônico.setSbsHelp2("is sending the message to the process " + (func.getOwner().getNumber() + 1));
            }
            wait2.play();
        });

        wait2.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            ack.Send().play();
            func.getOwner().getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            //O receive recebe a mensagem e envia um ack
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " recebe a mensagem");
                Mnemônico.setSbsHelp2("e envia uma confirmação de entrega ao processo " + (func.getSender() + 1));
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " recives the message");
                Mnemônico.setSbsHelp2("and sends an ACK of delivery to the process " + (func.getSender() + 1));
            }
            wait3.play();
        });

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            SceneConfig.blinkProc(func.getSender(), false, root);
            func.getOwner().takeNextStep();
            proc[func.getSender()].getThread(1).takeNextStep();
            //O send recebe o ack e ambos os processos andam
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Os dois processos processos continuam executando");
                Mnemônico.setSbsHelp2("");
            } else {
                Mnemônico.setSbsHelp1("Both processes keep executing");
                Mnemônico.setSbsHelp2("");
            }
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
        });
    }

    private static void HandleReceive10() {
        Receive func = (Receive) function;
        int sender = 0, flag = 0;

        Message ack = new Message(func.getOwner().getNumber(), func.getSender(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getSender(), func.getOwner().getNumber(), 0, SceneConfig.hlp);

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(2 * time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );
        SequentialTransition wait3 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (proc[func.getOwner().getNumber()].getPmsg(func.getSender()) > 0) {
            sender = func.getSender();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            proc[func.getOwner().getNumber()].remPmsg(func.getSender());
            func.getOwner().takeNextStep();
            //Não há mensagem no buffer e o receive segue normalmente
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Não há mensagens armazenadas");
                Mnemônico.setSbsHelp2("Receive() foi perdido");
            } else {
                Mnemônico.setSbsHelp1("There isn't stored message");
                Mnemônico.setSbsHelp2("Receive() was lost");
            }
            flag = 1;
            wait3.play();
        }

        wait3.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        });

        if (flag == 0) {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            proc[func.getOwner().getNumber()].remPmsg(func.getSender());
            if (proc[func.getOwner().getNumber()].getPmsg(-1) == 0) {
                func.getOwner().getBuffer().sendMessage();
            }
            root.getChildren().add(msg.getImage());
            msg.SendFromBuffer().play();
            //Há mensagem presente no buffer e o receive a recebe do buffer
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " recebe a mensagem do seu buffer");
                Mnemônico.setSbsHelp2("e continua a execução");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " receives the message from its buffer");
                Mnemônico.setSbsHelp2("and continues the executing");
            }
            wait1.play();
        }
        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            func.getOwner().takeNextStep();
            //O receive recebeu a mensagem e segue normalmente
            Mnemônico.setSbsHelp1("");
            Mnemônico.setSbsHelp2("");
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        });
    }

    private static void HandleReceive11() {
        Receive func = (Receive) function;
        int sender = 0;

        Message ack = new Message(func.getOwner().getNumber(), func.getSender(), 1, SceneConfig.hlp);
        Message msg = new Message(func.getSender(), func.getOwner().getNumber(), 0, SceneConfig.hlp);

        if (SceneConfig.timer != null) {
            SceneConfig.timer.stop();
        } else {
            SceneConfig.working = 0;
        }

        SequentialTransition wait1 = new SequentialTransition(
                new PauseTransition(Duration.millis(2*time + 100))
        );
        SequentialTransition wait2 = new SequentialTransition(
                new PauseTransition(Duration.millis(time + 100))
        );

        if (proc[func.getOwner().getNumber()].getPmsg(func.getSender()) > 0) {
            sender = func.getSender();
        } else {
            SceneConfig.blinkProc(func.getOwner().getNumber(), true, root);
            proc[func.getOwner().getNumber()].remPmsg(func.getSender());
            //Não há mensagem no buffer  e o processo bloqueia
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Não há mensagens no buffer");
                Mnemônico.setSbsHelp2("Processo bloqueado");
            } else {
                Mnemônico.setSbsHelp1("There isn't messages in the buffer");
                Mnemônico.setSbsHelp2("Blocked process");
            }
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        }

        SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
        proc[func.getOwner().getNumber()].remPmsg(func.getSender());
        if (proc[func.getOwner().getNumber()].getPmsg(-1) == 0) {
            func.getOwner().getBuffer().sendMessage();
        }
        root.getChildren().add(msg.getImage());
        //O buffer envia a mensagem ao receive
        msg.SendFromBuffer().play();
        if (lang == 0) {
            Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " recebe a mensagem do seu buffer");
            Mnemônico.setSbsHelp2("e continua a execução");
        } else {
            Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " receives the message from its buffer");
            Mnemônico.setSbsHelp2("and continues the executing");
        }
        wait1.play();

        wait1.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            SceneConfig.blinkProc(func.getOwner().getNumber(), false, root);
            func.getOwner().getStep().setImage(new Image("AuxSimulacao/img/ReceiveOk.jpg"));
            func.getOwner().takeNextStep();
            //O receive recebe a mensagem e o prcesso continua
            if (lang == 0) {
                Mnemônico.setSbsHelp1("Processo " + (func.getOwner().getNumber() + 1) + " recebe a mensagem do seu buffer");
                Mnemônico.setSbsHelp2("e continua a execução");
            } else {
                Mnemônico.setSbsHelp1("Process " + (func.getOwner().getNumber() + 1) + " receives the message from its buffer");
                Mnemônico.setSbsHelp2("and continues the executing");
            }
            if (SceneConfig.timer != null) {
                SceneConfig.timer.play();
            } else {
                SceneConfig.working = 1;
            }
            return;
        });
    }
}
