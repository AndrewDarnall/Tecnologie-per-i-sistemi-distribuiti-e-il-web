/**
 * 
 * - Server_B
 * 
 * Soluzione proposta -> Andrew R. Darnall
 * 
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>

#define PORT 3333
#define BUFFLEN 1024
#define MAX_CONN 1

int main(int argc, char** argv)
{

    int sockfd, newsockfd, n;
    struct sockaddr_in remote_addr;
    socklen_t len;
    char sendline[BUFFLEN];
    char recvline[BUFFLEN];

    printf("Server listening on port [%d]\n",PORT);

    if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket");
        exit(1);
    }

    memset(&remote_addr, 0, sizeof(struct sockaddr_in));
    remote_addr.sin_family = AF_INET;
    remote_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    remote_addr.sin_port = htons(PORT);

    if(bind(sockfd, (struct sockaddr*)&remote_addr, sizeof(remote_addr)) < 0) {
        perror("bind");
        exit(1);
    }


    listen(sockfd, MAX_CONN);

    while(1) {

        len = sizeof(remote_addr);
        if((newsockfd = accept(sockfd, (struct sockaddr*)&remote_addr, &len)) < 0) {
            perror("accept");
            exit(1);
        }

        if(fork() == 0) {

            close(sockfd);

            //Non ho messo un loop poiche' so di dover ricevere una sola stringa!
            n = recv(newsockfd, recvline, BUFFLEN, 0);
            recvline[n] = '\0';
            printf("From:\t%s\t%s\n",inet_ntoa(remote_addr.sin_addr),recvline);
            strcpy(sendline,"OK");
            strcat(sendline,"\r\n");
            send(newsockfd, sendline, strlen(sendline), 0);

            close(newsockfd);

            exit(0);

        } else {

            close(newsockfd);

        }

    }


    return EXIT_SUCCESS;
}