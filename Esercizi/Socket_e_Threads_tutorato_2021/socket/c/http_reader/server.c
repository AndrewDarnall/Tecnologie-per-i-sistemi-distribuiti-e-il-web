#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>

#define MAX_LINE_LENGTH 256

#define TRUE 1
#define FALSE 0

typedef int bool;

ssize_t readLine(int descriptor, char* lineBuffer) {
    ssize_t totalBytesRead = 0;
    char* writeLineSlot = lineBuffer; // &lineBuffer[0]

    while(1) {
        ssize_t readsByte = read(descriptor, writeLineSlot, sizeof(char));

        if(readsByte == -1){
            perror("Error on read");
            exit(1);
        }

        if(readsByte == 0) {
            break;
        }

        totalBytesRead += readsByte;

        // Controlliamo il carattere che abbiamo appena scritto (offset 0)
        // E quello che abbiamo scritto al ciclo prima (offset -1)
        // writeLineSlot non è altro che un puntatore alla cella di memoria
        // su cui abbiamo appena scritto
        bool isLineEnded = (writeLineSlot[0] == '\n') && (writeLineSlot[-1] == '\r');

        ++writeLineSlot;

        if(isLineEnded) {
            *writeLineSlot = '\0';
            break;
        }
    }

    return totalBytesRead;
}

int main() {
    bool waitForBody = FALSE;

    int socketfd=socket(AF_INET, SOCK_STREAM, 0);
    if (socketfd==-1)
    {
        perror("error to socket()");
        exit(1);
    }

    struct sockaddr_in address;
    address.sin_family=AF_INET;
    address.sin_addr.s_addr=INADDR_ANY;
    address.sin_port=htons(39999);

    int yes = 1;
    if (setsockopt(socketfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
    {
        perror("setsockopt");
        exit(1);
    }

    int checkBind = bind(socketfd, (struct sockaddr*) &address, sizeof(address));
    if(checkBind==-1){
        perror("Error on bind");
        exit(1);
    }

    int checkListen = listen(socketfd, 1);
    if(checkListen==-1){
        perror("Error on Listen");
        exit(1);
    }

    char lineBuffer[MAX_LINE_LENGTH];

    while(1) {
        printf("Waiting for client connection...\n");

        int clientConnection= accept(socketfd, NULL, NULL);
        if(clientConnection==-1){
            perror("Error on Accept");
            exit(1);
        }
        printf("Client connection enstablished\n");


        readLine(clientConnection, lineBuffer);
        printf("Request line: %s", lineBuffer);

        while(TRUE) {
            ssize_t lineLength = readLine(clientConnection, lineBuffer);
            if(lineLength == 2)  {
                break;
            }
            printf("Header: %s", lineBuffer);
        }

        printf("Headers have finished, waiting for body...\n");

        if(waitForBody) {
            ssize_t bodyLength = readLine(clientConnection, lineBuffer);
            if(bodyLength > 2) {
                printf("Request body: %s", lineBuffer);
            } else {
                printf("The request didn't contain any body\n");
            }
        } else {
            printf("Ignoring request body\n");
        }

        const char response[] = "HTTP/1.1 201 ACASO\r\n\r\n\r\n";

        ssize_t bytesWritten = write(clientConnection, response, sizeof(response));
        if(bytesWritten == -1) {
            perror("Error on response");
            exit(1);
        }

        close(clientConnection);
    }

    return 1;
}
