/*Server multiclient per ricevere messaggi e mostrarli a schermo.*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h> 
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#include <netdb.h>
#include <arpa/inet.h>

#define RCVBUFFER 1024
#define SNDBUFFER 2048
#define MAXREMOTE 3
#define MAXQUEUE 5
#define SERVICEPORT 7777
#define TRUE 1

char 
        snd_buffer[SNDBUFFER], 
        rcv_buffer[RCVBUFFER];
struct sockaddr_in remote_addr[MAXREMOTE];
int remote_sockfd[MAXREMOTE];

void doserv(char * msg){

}

void * comunication(void *data){
    int i = *((int *)data); // to identify a specific remote in remote_addr[]

    printf("Connected with: %s @ %d\n", inet_ntoa(remote_addr[i].sin_addr), ntohs(remote_addr[i].sin_port));
            
    int n = 0;
    while((n = read(remote_sockfd[i], rcv_buffer, RCVBUFFER)) > 0){
        //printf("DEBUG:\nConnection num = %d\npid = %d\nn = %d\n", i, getpid(), n);
        rcv_buffer[n-1] = '\0';

        printf("From[%s @ %d]: %s\n",
            inet_ntoa(remote_addr[i].sin_addr), 
            ntohs(remote_addr[i].sin_port),
            rcv_buffer
        );

        doserv(rcv_buffer); //do the service
    }
    printf("From[%s @ %d]: exit\n",
        inet_ntoa(remote_addr[i].sin_addr), 
        ntohs(remote_addr[i].sin_port) 
    );
    close(remote_sockfd[i]);
    pthread_exit(NULL);
}


int main(int argc, char * argv[]){
    pthread_t thread[MAXREMOTE];
    //void * t_res[MAXREMOTE]; 

    struct sockaddr_in server_addr;
    int server_sockfd;

    
    socklen_t remote_size[MAXREMOTE];

    if((server_sockfd = socket(AF_INET,SOCK_STREAM,0)) == -1){
        perror("while opening socket fd");
        exit(-1);
    }

    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(SERVICEPORT);
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

    if(bind(server_sockfd,(struct sockaddr *)&server_addr,sizeof(server_addr)) < 0){
        perror("while binding");
        exit(-1);
    }
    
    printf("Waiting for connection...\n");

    listen(server_sockfd, MAXQUEUE);
    int i = 0;
    while(TRUE){
        remote_sockfd[i] = accept(server_sockfd,(struct sockaddr *)&remote_addr[i], &remote_size[i]);
        if(remote_sockfd[i] == -1){
            perror("while accepting");
            exit(-1);
        }
        int param = i;
        pthread_create(&thread[i], 0, comunication, (void *)&param);
        i++; //set i to the next position
        if(i >= MAXREMOTE) break;
    }
    close(server_sockfd);

    for(int i=0;i<MAXREMOTE;i++)
        pthread_join(thread[i], NULL);

    exit(0);
}

