package calc;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "Calcolatore")
public class Calcolatore
{

    @WebMethod(operationName = "calcola")
    public double calcola(@WebParam(name = "operazione") char operazione, @WebParam(name = "operando1") double x, @WebParam(name = "operando2") double y)
    {
        double z;
        switch (operazione)
        {
            case '+':
                z = x + y;
                break;
            case '-':
                z = x - y;
                break;
            case '*':
                z = x * y;
                break;
            case '/':
                z = x / y;
                break;
            case '%':
                z = x % y;
                break;
            case '^':
                z = Math.pow(x, y);
                break;
            default:
                    z = 0;
        }
        return z;
    }
}
