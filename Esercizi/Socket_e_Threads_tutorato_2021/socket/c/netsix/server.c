#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>


#define MAXBUF 1024
#define SELEN 64

struct __series_t {
    char seriesName[SELEN];
    unsigned int episodesCount;
};

typedef struct __series_t series_t;

int main(){
    series_t catalog[] = {
        { "Sense8", 5 },
        { "Test", 3 },
    };

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

    printf("Waiting for client connection\n");

    char buffer[MAXBUF];

    while (1)
    {
        int clientConnection= accept(socketfd, NULL, NULL);
        if(clientConnection==-1){
            perror("Error on Accept");
            exit(1);
        }
        printf("Client connection enstablished\n");

        ssize_t readsByte = read(clientConnection, buffer, sizeof(buffer));
        if(readsByte==-1){
            perror("Error on read");
            exit(1);
        }

        char seriesName[SELEN];
        unsigned int episodeNumber;

        int elementsMatched = sscanf(buffer, "%[^,],%u", seriesName, &episodeNumber);

        if(elementsMatched == 2) {
            printf("Series name: %s, episode: %u\n", seriesName, episodeNumber);

            int seriesIndex=-1;
            for(int i=0; i<2; i++){
                series_t currentSeries = catalog[i];
                if(strcmp(seriesName, currentSeries.seriesName) == 0 && 
                    episodeNumber <= currentSeries.episodesCount){
                    seriesIndex=i;
                    break;
                }
            }

            if(seriesIndex!=-1){
                printf("Series found\n");
                sprintf(buffer, "Disponibile\n");
            } else {
                printf("Series not found\n");
                sprintf(buffer, "ComingSoon\n");
            }
        } else {
            printf("Malformed input\n");
            sprintf(buffer, "Malformed request\n");
        }

        write(clientConnection, buffer, strlen(buffer));

        close(clientConnection);
    }
}