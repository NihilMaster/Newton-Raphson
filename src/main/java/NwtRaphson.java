import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
        
/**
 *
 * @author M
 */
public class NwtRaphson extends JFrame {
    ////////////////////////////////////////////////////////////////
    /////////////La función se agrega a continuación////////////////
    ////////////////////////////////////////////////////////////////
    private static final String FUN_STR = "x^3 - 9x^2 + 25x(1+(sen^2(x)/25)) + x/sec^2(x) - 24 ";
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public static float f(float x){
        return (float)(Math.pow(x,3)-(9*Math.pow(x,2))+(25*x+(x*Math.pow(Math.sin(x), 2)))+(x*Math.pow(Math.cos(x), 2))-24); 
    }
    ////////////////////////////////////////////////////////////////
    //////////////////////Derivación numérica///////////////////////
    ////////////////////////////////////////////////////////////////
    public static float fdev(float x){
        return (float) ((f((float)(x+0.0001))-f((float)(x-0.0001)))/(2*0.0001));
    }
    ////////////////////////////////////////////////////////////////
    ////////////// No. Iteraciones para tabulación /////////////////
    private static final int IT = 10;
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    
    JFrame frame;
    JPanel panelSuperior, panelInferior;
    JButton tabtt, robtt;
    JLabel descrip, albl, blbl, clbl, elbl, flbl;
    JTextField atxt, btxt, ctxt, etxt;
    JTable table1, table2;
    
    private static int codeVal = 0;
    private static float thor = 0;
    private static String fin = "";
    
    public NwtRaphson() {
        super("Newton Raphson");
        
        // PANEL SUPERIOR
        panelSuperior = new JPanel ();
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSuperior.setLayout(new GridLayout(4,2,8,8));
        albl = new JLabel("Ingrese el menor valor:");
        atxt = new JTextField("0",20);
        blbl = new JLabel("Ingrese el mayor valor:");
        btxt = new JTextField("0",20);
        clbl = new JLabel("Valor aproximado a la tercera ra\u00EDz:");
        ctxt = new JTextField("",20);
        elbl = new JLabel("Ingrese el error permitido:");
        etxt = new JTextField("0",20);
        panelSuperior.add(albl); panelSuperior.add(atxt);
        panelSuperior.add(blbl); panelSuperior.add(btxt);
        panelSuperior.add(clbl); panelSuperior.add(ctxt);
        panelSuperior.add(elbl); panelSuperior.add(etxt);
        // Estilo
        clbl.setVisible(false); ctxt.setVisible(false);
        elbl.setVisible(false); etxt.setVisible(false);
        
    
        // PANEL INFERIOR
        panelInferior= new JPanel();
        panelInferior.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.PAGE_AXIS));
        tabtt = new JButton("Tabular");
        robtt = new JButton("Encontrar ra\u00EDz");
        table1 = new JTable(); table2 = new JTable();
        flbl = new JLabel("");
        JScrollPane scrollPanel1 = new JScrollPane(table1);
        JScrollPane scrollPanel2 = new JScrollPane(table2);
        // Estilo
        scrollPanel2.setVisible(false);
        tabtt.setBackground(Color.BLUE); tabtt.setForeground(Color.WHITE);
        robtt.setVisible(false); robtt.setBackground(Color.YELLOW);
        // Adici�n
        panelInferior.add(tabtt);
        panelInferior.add(robtt);
        panelInferior.add(scrollPanel1); panelInferior.add(scrollPanel2);
        panelInferior.add(flbl);
        
        // DESCRIPCI�N
        descrip = new JLabel("Seleccione el intervalo a tabular.");
        descrip.setHorizontalAlignment(SwingConstants.LEFT);
        descrip.setFont(new Font("Arial", Font.BOLD, 20));
        
        // GENERAL
        frame =new JFrame("Newton - Raphson     |     Mateo L. - Weimar J.");
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.add(descrip);
        frame.add(panelSuperior);
        frame.add(panelInferior);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        //Escuchadores
        tabtt.addActionListener((ActionEvent e) -> {
            String astr = atxt.getText();
            String bstr = btxt.getText();
            Object[] col = {"x", ""+FUN_STR,};
            Object[][] dat = tab1(Float.parseFloat(astr), Float.parseFloat(bstr));
            DefaultTableModel dmt = new DefaultTableModel(dat,col);
            table1.setModel(dmt);
            // Cambio de estilo
            panelSuperior.setBackground(Color.GRAY); panelInferior.setBackground(Color.GRAY);
            atxt.setText(""); btxt.setText("");
            albl.setText("Valor aproximado a la primera ra\u00EDz:");
            blbl.setText("Valor aproximado a la segunda ra\u00EDz:");
            descrip.setText("Ingrese el valor aproximado y el error permitido");
            tabtt.setVisible(false); robtt.setVisible(true);
            clbl.setVisible(true); ctxt.setVisible(true);
            elbl.setVisible(true); etxt.setVisible(true);            
            showMessageDialog(null, fin);
        });
        
        
        robtt.addActionListener((ActionEvent e) -> {
            scrollPanel1.setVisible(false); scrollPanel2.setVisible(true);
            String astr = atxt.getText(); String bstr = btxt.getText();
            String cstr = ctxt.getText(); String estr = etxt.getText();
            float af=0,bf=0,cf=0; boolean sine; 
            boolean ab=true,bb=true,cb=true; 
            try {af = Float.parseFloat(astr);} catch (NumberFormatException excepcion) {ab=false;} 
            try {bf = Float.parseFloat(bstr);} catch (NumberFormatException excepcion) {bb=false;} 
            try {cf = Float.parseFloat(cstr);} catch (NumberFormatException excepcion) {cb=false;} 
            try {Float.parseFloat(estr);sine=false;} catch (NumberFormatException excepcion) {sine = true;}
            
            if(ab && bb && cb){codeVal=0;} 
            else if(ab && !bb && !cb){codeVal=1;} else if(!ab && bb && !cb){codeVal=2;} else if(!ab && !bb && cb){codeVal=3;} 
            else if(ab && bb && !cb){codeVal=4;} else if(ab && !bb && cb){codeVal=5;} else if(!ab && bb && cb){codeVal=6;}
            
            if (Float.parseFloat(estr)<=0 || sine){
                showMessageDialog(null, "El error no puede ser 0 o menor que 0");
            }else{
                Object[] col = {"No.", "Ra\u00EDz", "E"};
                Object[][] dat = tab2(af,bf,cf,Float.parseFloat(estr));
                DefaultTableModel mdma = new DefaultTableModel(dat,col);
                table2.setModel(mdma);
                flbl.setText("...");
                table2.getColumnModel().getColumn(0).setMaxWidth(50);
            }
        });
    }
    
    private static Object[][] tab1(float a, float b){
        Object[][] s = new Object[IT][2];
        float iter = (b-a)/IT;
        String strMap = "";
        for (int i=0; i<IT; i++){
            s[i][0]=a; s[i][1]=f(a);
            try {
                if (f(a)>0 && f(a-iter)<=0 || f(a)<=0 && f(a-iter)>0){
                    s[i][1]=a; s[i][0]=a-iter;
                    strMap += "\t["+String.format("%.2f", s[i][0])+" - "+String.format("%.2f", s[i][1])+"]\n";
                }
            } catch (HeadlessException e) {System.out.println("Error de iteración esperado: "+e);}
            a += iter;
        }
        fin = "Rangos sugeridos para proponer aproximados: \n"+strMap;
        return s;
    }
    
    private static Object[][] tab2(float a, float b, float c, float e){
        Object[][] s = new Object[3][3];
        float aux, error=0; int i=0;
        switch(codeVal){
            case 0 -> {
                float ax=a, bx=b, cx=c;
                do{ thor = ax - (f(ax)/fdev(ax)); aux=ax; ax=thor; error=Math.abs(ax-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=ax; s[i][2]=error; i++; error=0;
                do{thor = bx - (f(bx)/fdev(bx)); aux=bx; bx=thor; error=Math.abs(bx-aux);}while(e<error);
                s[i][0]=i+1; s[i][1]=bx; s[i][2]=error; i++; error=0;
                do{ thor = cx - (f(cx)/fdev(cx)); aux=cx; cx=thor; error=Math.abs(cx-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=cx; s[i][2]=error; i++;
            }
            case 1 -> {
                float ax=a;
                do{ thor = ax - (f(ax)/fdev(ax)); aux=ax; ax=thor; error=Math.abs(ax-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=ax; s[i][2]=error; i++;
            }
            case 2 -> {
                float bx=b;
                do{thor = bx - (f(bx)/fdev(bx)); aux=bx; bx=thor; error=Math.abs(bx-aux);}while(e<error);
                s[i][0]=i+1; s[i][1]=bx; s[i][2]=error; i++;
            }
            case 3 -> {
                float cx=c;
                do{ thor = cx - (f(cx)/fdev(cx)); aux=cx; cx=thor; error=Math.abs(cx-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=cx; s[i][2]=error; i++;
            }
            case 4 -> {
                float ax=a, bx=b;
                do{ thor = ax - (f(ax)/fdev(ax)); aux=ax; ax=thor; error=Math.abs(ax-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=ax; s[i][2]=error; i++; error=0;
                do{thor = bx - (f(bx)/fdev(bx)); aux=bx; bx=thor; error=Math.abs(bx-aux);}while(e<error);
                s[i][0]=i+1; s[i][1]=bx; s[i][2]=error; i++;
            }
            case 5 -> {
                float ax=a, cx=c;
                do{ thor = ax - (f(ax)/fdev(ax)); aux=ax; ax=thor; error=Math.abs(ax-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=ax; s[i][2]=error; i++; error=0;
                do{ thor = cx - (f(cx)/fdev(cx)); aux=cx; cx=thor; error=Math.abs(cx-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=cx; s[i][2]=error; i++;
            }
            case 6 -> {
                float bx=b, cx=c;                
                do{thor = bx - (f(bx)/fdev(bx)); aux=bx; bx=thor; error=Math.abs(bx-aux);}while(e<error);
                s[i][0]=i+1; s[i][1]=bx; s[i][2]=error; i++; error=0;
                do{ thor = cx - (f(cx)/fdev(cx)); aux=cx; cx=thor; error=Math.abs(cx-aux); }while(e<error);
                s[i][0]=i+1; s[i][1]=cx; s[i][2]=error; i++;
            }
            default -> System.out.println("Problemas");
        }
        return s;
    }
    
    public static void main(String[] args) throws IOException{
        NwtRaphson run = new NwtRaphson();
        System.out.println(run);
    }
}