#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>

#include "common.c"

int main() {
    printf("Hello World! I'm the server\n");

    int socket_descriptor = socket(PF_INET, SOCK_DGRAM, 0);
    if(socket_descriptor == -1) {
        perror("Unable to initialize socket");
        exit(1);
    }

    struct sockaddr_in address;

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(9000);

    socklen_t address_length = sizeof(address);

    int bindReturnCode = bind(socket_descriptor, (struct sockaddr*) &address, address_length);

    if(bindReturnCode == -1) {
        perror("Error on bind");
        exit(1);
    }

    char receive_buffer[1024];
    size_t receive_buffer_size = sizeof(receive_buffer);
    size_t chunk_size = 4;

    read_message(socket_descriptor, receive_buffer, chunk_size);

    printf("Read message: %s\n", receive_buffer);

    close(socket_descriptor);
    
    return 0;
}