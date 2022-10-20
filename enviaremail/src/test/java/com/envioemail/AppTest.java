package com.envioemail;

import java.io.FileInputStream;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @org.junit.Test
    public void testeEmail()throws Exception {  

        StringBuilder stringBuilderTextEmail = new StringBuilder();
        
        stringBuilderTextEmail.append("<h2>Olá, Meu nome é João Victor</h2>");
        stringBuilderTextEmail.append(" <h3>Venha conferir meu Perfil no Linekdin:<a href=https://www.linkedin.com/in/jo%C3%A3o-victor-paiva-305459211/>Meu Perfil<h3>");
        stringBuilderTextEmail.append("");
        ObjEnviaEmail enviaEmail = new ObjEnviaEmail("paivajoaovictor00@gmail.com","João Victor Linkedin","Email Enviado com Java", stringBuilderTextEmail.toString());
	
        

        enviaEmail.enviarEmailAnexo(true);
            
        Thread.sleep(5000);
    
}
}