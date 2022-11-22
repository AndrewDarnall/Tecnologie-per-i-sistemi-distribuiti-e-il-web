/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netsixService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


/**
 *
 * @author helias
 */
@WebService(serviceName = "NewWebService")
public class NewWebService {

    public String[] serie = new String[6];
    public String[][] episodi = new String[6][3];
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getDisponibilita")
    public boolean getDisponibilita(@WebParam(name = "serieInput") String serieInput, @WebParam(name = "episodio") int episodio) {

        serie[0] = "The Walking Dead";
        serie[1] = "Breaking Bad";
        serie[2] = "Black Mirror";
        serie[3] = "Humans";
        serie[4] = "Stranger Things";
        serie[5] = "The Mentalist";

        episodi[0][0] = "twd - 1";
        episodi[0][1] = "twd - 2";
        episodi[0][2] = "twd - 3";

        episodi[1][0] = "bb - 1";
        episodi[1][1] = "bb - 2";
        episodi[1][2] = "bb - 3";

        episodi[2][0] = "bm - 1";
        episodi[2][1] = "bm - 2";

        episodi[3][0] = "h - 1";
        episodi[3][1] = "h - 2";
        episodi[3][2] = "h - 3";

        episodi[4][0] = "st - 1";
        episodi[4][1] = "st - 2";

        episodi[5][0] = "tm - 1";
        episodi[5][1] = "tm - 2";
        episodi[5][2] = "tm - 3";

        for (int i = 0; i < serie.length; i++) {

            if (serie[i].equals(serieInput)) {

                if (episodi[i].length >= episodio)
                    return true;
            }
        }

        return false;
    }
}
