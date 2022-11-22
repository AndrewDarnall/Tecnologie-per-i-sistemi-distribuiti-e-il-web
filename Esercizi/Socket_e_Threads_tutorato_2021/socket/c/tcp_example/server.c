#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>

int main() {
    printf("Hello World! I'm the server\n");

    int socket_descriptor = socket(PF_INET, SOCK_STREAM, 0);
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

    int listenReturnCode = listen(socket_descriptor, 1);

    if(listenReturnCode == -1) {
        perror("Error on listen");
        exit(1);
    }

    printf("Waiting for client connection...\n");

    int connection_socket_descriptor = accept(socket_descriptor, 
        (struct sockaddr*) &address, &address_length); 

    if(connection_socket_descriptor == -1) {
        perror("Unable to accept client connection: ");
        exit(1);
    }

    printf("Client connection established!\n");

    size_t chunk_size = 4;

    char receive_buffer[1024];
    size_t receive_buffer_size = sizeof(receive_buffer);

    size_t total_bytes_read = 0;

    while(1) {
        ssize_t bytes_read = read(connection_socket_descriptor, 
            receive_buffer + total_bytes_read, 
            chunk_size);

        if(bytes_read == -1) {
            perror("Error on message read");
            exit(1);
        }

        total_bytes_read += bytes_read;

        if(receive_buffer[total_bytes_read - 1] == 0) {
            break;
        }
    }
    
    printf("Read message: %s\n", receive_buffer);

    close(connection_socket_descriptor);
    close(socket_descriptor);

    return 0;
}