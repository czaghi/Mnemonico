package AuxConfig;

import static AuxConfig.Function_n.*;
import MainPkg.Infos;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.Image;

public class ProcessPreview {

    private ArrayList<StepPreview> preview = new ArrayList<>();
    private int number, size;
    private int Max;
    private String selected_img;
    private Function_n selected_func;

    public ProcessPreview(int number) {

        this.number = number;
        this.Max = Infos.MAX;

        size = 0;

        initList();
    }

    public void AddStep() {

        if (size == Max - 1) {
            return;
        }

        preview.get(size + 1).setImage(new Image("AuxConfig/img/Exit.jpg"));
        preview.get(size + 1).setFunc(Exit);
        preview.get(size + 1).setVisible(true);
        preview.get(size).changeBack();

        size++;
    }

    public void RemoveStep() {

        if (size == 0) {
            return;
        }

        preview.get(size).setVisible(false);
        preview.get(size - 1).setImage(new Image("AuxConfig/img/Exit.jpg"));
        preview.get(size - 1).setFunc(Exit);

        size--;
    }

    public String getSelectedImg() {
        return selected_img;
    }

    public void setSelectedImg(String img) {
        selected_img = img;
    }

    public Function_n getSelectedFunc() {
        return selected_func;
    }

    public void setSelectedFunc(Function_n func) {
        selected_func = func;
    }

    public Group getGroup() {

        Group ret = new Group();
        int i;

        for (i = 0; i < Max; i++) {
            ret.getChildren().add(preview.get(i));
        }

        return ret;
    }

    public int getSize() {
        return size;
    }

    public void setInfos() {

        int i;

        Infos.backup[number] = this;
        Infos.NoT[number] = 1;
        Infos.tamP[number][0] = size + 1;
        Infos.tamP[number][1] = 0;
        Infos.Ready[number] = 1;

        for (i = 0; i < size; i++) {
            Infos.func[number][i] = preview.get(i).getFunc();
        }
    }

    public int checkPreview() {

        int i;

        for (i = 0; i < size; i++) {
            if (preview.get(i).getFunc() == Empty) {
                return 0;
            }
        }

        return 1;
    }

    public void preset(int n) {
     
        int i = 0;
        
        setSelectedImg("AuxConfig/img/Empty.jpg");
        setSelectedFunc(Empty);
        
        while (size > 0) {
            RemoveStep();
            preview.get(i++).change();
        }

        if (n == 1 && number == 0) {
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send3.jpg");
            setSelectedFunc(Send3);
            preview.get(0).change();
            setSelectedImg("AuxConfig/img/Receive2.jpg");
            setSelectedFunc(Receive2);
            preview.get(1).change();
        } else if (n == 1 && number == 1) {
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send1.jpg");
            setSelectedFunc(Send1);
            preview.get(1).change();
            setSelectedImg("AuxConfig/img/Receive3.jpg");
            setSelectedFunc(Receive3);
            preview.get(0).change();
        } else if (n == 1 && number == 2) {
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send2.jpg");
            setSelectedFunc(Send2);
            preview.get(1).change();
            setSelectedImg("AuxConfig/img/Receive1.jpg");
            setSelectedFunc(Receive1);
            preview.get(0).change();
        } else if (n == 2 && number == 0) {
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send2.jpg");
            setSelectedFunc(Send2);
            preview.get(0).change();
            setSelectedImg("AuxConfig/img/Receive2.jpg");
            setSelectedFunc(Receive2);
            preview.get(1).change();
        } else if (n == 2 && number == 1) {
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send1.jpg");
            setSelectedFunc(Send1);
            preview.get(0).change();
            setSelectedImg("AuxConfig/img/Receive1.jpg");
            setSelectedFunc(Receive1);
            preview.get(1).change();
        } else if (n == 3 && number == 0) {
            AddStep();
            AddStep();
            AddStep();
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Send2.jpg");
            setSelectedFunc(Send2);
            preview.get(4).change();
            preview.get(0).change();
            setSelectedImg("AuxConfig/img/Receive2.jpg");
            setSelectedFunc(Receive2);
            preview.get(3).change();
            setSelectedImg("AuxConfig/img/Nop.jpg");
            setSelectedFunc(Nop);
            preview.get(2).change();
            preview.get(1).change();
        } else if (n == 3 && number == 1) {
            AddStep();
            AddStep();
            AddStep();
            AddStep();
            AddStep();
            setSelectedImg("AuxConfig/img/Receive1.jpg");
            setSelectedFunc(Receive1);
            preview.get(1).change();
            preview.get(4).change();
            setSelectedImg("AuxConfig/img/Nop.jpg");
            setSelectedFunc(Nop);
            preview.get(0).change();
            preview.get(3).change();
            setSelectedImg("AuxConfig/img/Send1.jpg");
            setSelectedFunc(Send1);
            preview.get(2).change();
        }
    }

    private void initList() {
        int i;

        for (i = 0; i < size; i++) {
            preview.add(new StepPreview("AuxConfig/img/Empty.jpg", Empty, this, number, i));
        }

        preview.add(new StepPreview("AuxConfig/img/Exit.jpg", Exit, this, number, size));

        for (i = (size + 1); i < Max; i++) {
            preview.add(new StepPreview("AuxConfig/img/Empty.jpg", Empty, this, number, i));
            preview.get(i).setVisible(false);
        }
    }
}
