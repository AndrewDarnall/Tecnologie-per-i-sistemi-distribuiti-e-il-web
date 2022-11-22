/*
    A)  Realizzare un server (in C o in Java) chiamato ​Server A ​che si metta in ascolto sul ​port 
    7777 ​per ricevere una stringa ​str ​composta da una sequenza di lunghezza 
    arbitraria di caratteri numerici da (0 a 9)​ ​e terminata dal carattere ​\n​. Il server dovrà 
    quindi stampare il messaggio ricevuto sullo standard output. Testare il server usando 
    telnet. 

    B)  Estendere le funzionalità dal server definito al punto precedente realizzando un secondo 
    server chiamato ​Server B ​che oltre a stampare il messaggio ricevuto sullo standard 
    output, lo invia come risposta al client (senza modificarlo). Testare il server usando 
    telnet.   

    C)  Estendere le funzionalità dal server definito al punto precedente realizzando un terzo 
    server chiamato ​Server C ​che oltre a stampare il messaggio ricevuto sullo standard 
    output, lo passa ad un metodo “​int MUL(String str)​” che per ora restituisce 
    sempre 0 per qualunque parametro di input ​str.​ Il risultato del metodo deve quindi 
    essere inviato come messaggio di risposta al client. Testare il server usando telnet.   

    D)  Estendere le funzionalità dal server definito al punto precedente realizzando un quarto 
    server chiamato ​Server D ​modificando il comportamento del metodo “​int 
    MUL(String str)​” che dovrà restituire il prodotto delle singole cifre numeriche 
    presenti nella stringa in input, ad esempio per la stringa “1234” restituirà l’intero 24. Il 
    risultato del metodo deve quindi essere inviato come messaggio di risposta al client. 
    Testare il server usando telnet.
*/
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <unistd.h>

const size_t BUFFER_SIZE = 1024;

int main() {
    printf("Hello world! I'm a server on port 7777\n");

    struct sockaddr_in address;

    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(7777);
    address.sin_family = AF_INET;

    socklen_t addressLength = sizeof(address);

    int serverSocketDescriptor = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocketDescriptor == -1) {
        perror("Unable to initialize socket");
        exit(1);
    }

    int option = 1;
    if(setsockopt(serverSocketDescriptor, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option)) == -1) {
        perror("Unable to update socket's configuration");
        exit(1);
    }

    int tryingBind = bind(serverSocketDescriptor, (struct sockaddr*) &address, addressLength);
    if(tryingBind == -1) {
        perror("Error on bind");
        exit(1);
    }

    int tryingListen = listen(serverSocketDescriptor, 1);
    if(tryingListen == -1) {
        perror("Error on listen");
        exit(1);
    }

    struct sockaddr_in clientAddress;

    socklen_t clientAddressLength = sizeof(clientAddress);

    int clientSocketDescriptor = accept(serverSocketDescriptor, (struct sockaddr*) &clientAddress, &clientAddressLength);
    if(clientSocketDescriptor == -1){
        perror("Error on accept");
        exit(1);
    }

    printf("Client connected!\n");

    //lettura

    char buffer[BUFFER_SIZE];
    ssize_t totalBytesRead = 0;
    ssize_t bytesRead;

    do{
        bytesRead = read(clientSocketDescriptor, 
                                buffer + totalBytesRead, 
                                BUFFER_SIZE - totalBytesRead);

        if(bytesRead == -1) {
            perror("read error");
            exit(1);
        }

        totalBytesRead += bytesRead;

        printf("Bytes read: %lu\n", bytesRead);
        printf("Last byte read: (%d)\n", buffer[totalBytesRead - 1]);
    } while(bytesRead > 0 && buffer[totalBytesRead - 1] != '\n');

    size_t newlineIndex = totalBytesRead - 2;

    if(buffer[newlineIndex] == '\n') {
        fprintf(stderr, "expected newline at index %lu found '%c'\n", newlineIndex, buffer[newlineIndex]);
    }

    for(int i=0; i<newlineIndex; i++){
        if(buffer[i] < 48 || buffer[i] > 57){
            fprintf(stderr, "invalid char at index %d ('%c')\n", i, buffer[i]);
            exit(1); 
        }
    }

    printf("buffer: %s\n",buffer);
    
    printf("Closing.\n");

    shutdown(clientSocketDescriptor, 2);
    shutdown(serverSocketDescriptor, 2);
    
    close(clientSocketDescriptor);
    close(serverSocketDescriptor);

    return 0;
}