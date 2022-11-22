package server;
import java.io.*;
import java.util.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import server.*;

@WebService(serviceName = "ServerReverse")
public class ServerReverse 
{
    @WebMethod(operationName="reverse")
    public String reverse(@WebParam(name="casa") String casa)
    {
        char[] vett=casa.toCharArray();
        for(int i=0; i<=(casa.length()-1)/2; i++)
        {
            char a=casa.charAt(casa.length()-1-i);
            vett[casa.length()-1-i]=vett[i];
            vett[i]=a;
        }
        casa=String.valueOf(vett);
        return casa;
    }
}