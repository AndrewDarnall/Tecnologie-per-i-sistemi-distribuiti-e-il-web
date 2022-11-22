package server;
import java.io.*;
import java.util.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import server.*;

@WebService(serviceName = "TemperaturaService")
public class TemperaturaService 
{
    private final Citta c=new Citta();
    @WebMethod(operationName="getTemperature")
    public double getTemperature(@WebParam(name="city") String city)
    {
        return c.getTempo(city);
    }
    @WebMethod(operationName="getAllTemperature")
    public String[] getAllTemperature()
    {
        String all[]=new String[c.getSize()];
        all=c.getAllCitta();
        double t[]=new double[c.getSize()];
        t=c.getAllTempo();
        for(int i=0; i<=c.getSize()-1; i++)
        {
            all[i]+=": " + t[i] + "°C;";
        }
        return all;
    }
}
