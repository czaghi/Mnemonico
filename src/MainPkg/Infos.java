
/*
A classe infos eh como uma struct estatica. Que da apoio as classes de configuracao.
O objetivo dela eh fornecer, de forma legivel, variaveis globais que funcionem
em qualquer classe ou objeto instanciado.
*/

package MainPkg;

import AuxConfig.Function_n;
import AuxConfig.ProcessPreview;
import java.util.ArrayList;

public class Infos {
    
    public static int MAX = 10;
    
    public static int NoP = 3;
    public static int tamP[][] = {{10, 0}, {10, 0}, {10, 0}};
    public static int NoT[] = {1, 1, 1};
    public static int Ready[] = {1, 1, 1};
    
    public static Function_n func[][] = new Function_n[3][MAX];
    
    public static ProcessPreview backup[] = {null, null, null};
}
