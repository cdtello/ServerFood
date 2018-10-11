/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import ServerFood.AdminAplicacion;

/**
 *
 * @author C.TELLO
 */
public class InterfazHilo extends Thread{
    public AdminAplicacion administrador;
    public InterfazHilo(AdminAplicacion administrador)
    {
        this.administrador = administrador;
    }
    public void run()
    {
        int contador = 0;
        String cadena = "";
        while(true)
        {
                try {
                    Thread.sleep(600);
                    cadena = cadena + ".";
                    if (administrador.getConectado1() == 0)
                        {administrador.ventanaPricipal.getEstadoP1().setText(cadena);
                        administrador.ventanaPricipal.getEstadoP1().setForeground(Color.red);}
                    if (administrador.getConectado2() == 0)
                        {administrador.ventanaPricipal.getEstadoP2().setText(cadena);
                        administrador.ventanaPricipal.getEstadoP2().setForeground(Color.red);}
                    if (administrador.getConectado3() == 0)                    
                        {administrador.ventanaPricipal.getEstadoP3().setText(cadena);
                        administrador.ventanaPricipal.getEstadoP3().setForeground(Color.red);}
                    if (administrador.getConectado4() == 0)                    
                        {administrador.ventanaPricipal.getEstadoP4().setText(cadena);
                        administrador.ventanaPricipal.getEstadoP4().setForeground(Color.red);}
                    if (administrador.getConectado5() == 0)                    
                        {administrador.ventanaPricipal.getEstadoP5().setText(cadena);
                        administrador.ventanaPricipal.getEstadoP5().setForeground(Color.red);}
                    if (cadena.length() == 6)
                    {cadena = "";}
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(InterfazHilo.class.getName()).log(Level.SEVERE, null, ex);
                }
                        
        }
    }
}
