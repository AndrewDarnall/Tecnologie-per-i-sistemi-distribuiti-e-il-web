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

    // char message[] = "AAAABBBBCCCCDDDDALESSIOFR";
    // WARNING: Often failing if the message is big enough
    size_t message_size = 1024 * 10;
    char* message = malloc(sizeof(char) * 1024 * 10);

    for(size_t i = 0; i < message_size - 1; ++i) {
        message[i] = 65 + ((i * 2 + i % 3) % 26);
    }

    message[message_size - 1] = 0;

    size_t chunk_size = 4;

    send_message(socket_descriptor, (struct sockaddr*) &server_address, 
        message, message_size, chunk_size);

    printf("Message sent!\n");

    // Verification

    const size_t buffer_size = 1024 * 12;
    char receive_buffer[buffer_size];
    size_t receive_buffer_size = sizeof(receive_buffer);

    read_message(socket_descriptor, receive_buffer, chunk_size);

    for(size_t i = 0; i < message_size; ++i) {
        if(message[i] != receive_buffer[i]) {
            printf("WARNING: Transmission error at index %lu\n", i);
        }
    }

    close(socket_descriptor);

    return 0;
}