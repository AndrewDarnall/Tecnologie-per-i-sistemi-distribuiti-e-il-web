/**
Un noto distributore di contenuti video on­demand, N​etSix,​ è famoso a livello internazionale per essere l’unico a rendere disponibili ben ​6 serie tv,​ ognuna composta da un certo numero di ​episodi.​

Espone un servizio NetSixServer interrogabile tramite socket sulla porta ​3333, che risponde a messaggi nel formato "n​ome_serie,n” con cui un client può chiedere se è disponibile l’episodio ’n’ della serie ’nome_serie' (il carattere virgola fa da separatore), rispondendo alla richiesta con la stringa '​Disponibile' s​e l’episodio è presente nel sistema, o ​'ComingSoon’​se l’episodio o la serie richiesta non sono attualmente disponibili.

L’elenco delle 6 serie tv (popolabile a piacere) con il rispettivo numero di episodi, deve essere gestito in una classe separata chiamata ​ShowList,​ che ha al suo interno il metodo ​isAvailable(nome_serie,n)​ che verrà usato dal ​NetSixServer​ per sapere se l’episodio richiesto è disponibile o meno.
**/

package com.netsix.server;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println("Creating show list..");

        ShowList showList = new ShowList();
        
        showList
            .add("House of Karts", 5)
            .add("Strange Things", 10)
            .add("Baking Brad", 12)
            .add("Marcos", 7)
            .add("Black Mails", 15)
            .add("Rick e Morto", 4);

        NetsixServer netsixServer = new NetsixServer(3333, showList);

        netsixServer.waitForRequests();
    }
}