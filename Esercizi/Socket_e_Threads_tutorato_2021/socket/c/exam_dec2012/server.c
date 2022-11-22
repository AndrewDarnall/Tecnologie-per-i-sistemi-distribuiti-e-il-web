/*
    resta in attesa di dati sul port 33333
    continua a leggere dati, nella forma XX...XNN...N, in cui:
        XX...X è una sequenza di caratteri non cifra
        NN...N è una sequenza di cifre (da 0 a 9)
    il server effettua la somma delle cifre contenute in NN...N, sia somma
    il server mantiene un numero che rappresenta la somma totale di tutte le richieste ricevute
    risponde al client con la nuova somma ottenuta con il messaggio ricevuto (somma totale + somma)
    chiude la connessione con il client e torna in attesa di connessioni al punto 1
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>

int funcSum(char *buffer)
{
    int i = 0;
    int sum = 0;

    while (buffer[i] != '\n' && buffer[i] != '\r' && (buffer[i] < '0' || buffer[i] > '9'))
    {
        i++;
    }

    while (buffer[i] != '\n' && buffer[i] != '\r')
    {
        if (buffer[i] < '0' || buffer[i] > '9')
        {
            return -1;
        }

        sum += (int)(buffer[i] - '0');
        ++i;
    }

    return sum;
}

int main()
{
    printf("start server\n");

    int socketfd = socket(PF_INET, SOCK_STREAM, 0);
    if (socketfd == -1)
    {
        perror("error to socket()");
        exit(1);
    }

    struct sockaddr_in address;
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(33333);

    int yes = 1;
    if (setsockopt(socketfd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)) == -1)
    {
        perror("setsockopt");
        exit(1);
    }

    int bindcheck = bind(socketfd, (struct sockaddr *)&address, sizeof(address));
    if (bindcheck == -1)
    {
        perror("error bind");
        exit(1);
    }

    int listencheck = listen(socketfd, 1);
    if (listencheck == -1)
    {
        perror("error listen");
        exit(1);
    }

    //struct sockaddr_in addressClient;
    //socklen_t lenAddressClient = sizeof(addressClient);
    //int socket_connection = accept(socketfd,(struct sockaddr*) &addressClient, &lenAddressClient);

    int totalSum = 0;
    while (1)
    {
        int socket_connection = accept(socketfd, NULL, NULL);

        if (socket_connection == -1)
        {
            perror("error accept");
            exit(1);
        }
        printf("Client connection\n");
        char buffer[1024];
        ssize_t totalBytesRead = 0;
        ssize_t currentBytesRead = 0;

        do
        {
            currentBytesRead = read(socket_connection,
                                    buffer + totalBytesRead,
                                    sizeof(buffer) - totalBytesRead);

            if (currentBytesRead == -1)
            {
                perror("error read");
                exit(1);
            }
            totalBytesRead += currentBytesRead;

        } while (buffer[totalBytesRead - 1] != '\n');

        printf("msg: %s size: %ld\n", buffer, totalBytesRead);
        int sum = funcSum(buffer);

        if (sum == -1)
        {
            sprintf(buffer, "ERROR: Invalid input\n");
        }
        else
        {
            totalSum += sum;
            sprintf(buffer, "Total sum :%d\n", totalSum);
        }

        printf("Sending response: %s\n", buffer);

        int writecheck = write(socket_connection,buffer,strlen(buffer));
        if(writecheck == -1){
            perror("error write");
            exit(1);
        }

        close(socket_connection);
    }

    return 0;
}
