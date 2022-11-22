#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>

int main() {
    printf("Hello World! I'm the client\n");

    int socket_descriptor = socket(PF_INET, SOCK_STREAM, 0);
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

    int connection_return_code = connect(socket_descriptor, 
        (struct sockaddr*) &server_address, server_address_length);

    if(connection_return_code == -1) {
        perror("Error on connection");
        exit(1);
    }

    printf("Connected to server!\n");

    size_t chunk_size = 4;

    char message[] = "AAAABBBBCCCCDDDDALESSIO";
    size_t message_size = sizeof(message);

    size_t total_bytes_sent = 0; 

    while(total_bytes_sent < message_size) {
        size_t message_bytes_remaining = message_size - total_bytes_sent;
        size_t bytes_to_send = chunk_size < message_bytes_remaining ? 
            chunk_size : message_bytes_remaining;

        ssize_t bytes_sent = send(socket_descriptor, 
            message + total_bytes_sent, 
            bytes_to_send, 
            0);

        if(bytes_sent == -1) {
            perror("Unable to send chunk");
            exit(1);
        }

        total_bytes_sent += bytes_sent;
    }

    printf("Message sent!\n");

    close(socket_descriptor);

    return 0;
}