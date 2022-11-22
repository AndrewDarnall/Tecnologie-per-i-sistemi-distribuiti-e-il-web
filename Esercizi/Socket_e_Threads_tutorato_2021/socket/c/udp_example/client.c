#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>

#include "common.c"

int main() {
    printf("Hello World! I'm the client\n");

    int socket_descriptor = socket(PF_INET, SOCK_DGRAM, 0);
    if(socket_descriptor == -1) {
        perror("Unable to initialize socket");
        exit(1);
    }

    struct sockaddr_in server_address;

    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(9000);

    socklen_t server_address_length = sizeof(server_address);

    int address_conversion_return_code = inet_pton(AF_INET, 
        "127.0.0.1", &server_address.sin_addr);

    if(address_conversion_return_code < 1) {
        perror("Error on address conversion");
        exit(1);
    }

    char message[] = "AAAABBBBCCCCDDDDALESSIOFR";
    size_t chunk_size = 4;

    send_message(socket_descriptor, (struct sockaddr*) &server_address, 
        message, sizeof(message), chunk_size);

    printf("Message sent!\n");

    close(socket_descriptor);

    return 0;
}