#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>

void send_message(int socket_descriptor, struct sockaddr* dest_addr, 
                char* message, size_t message_size, size_t chunk_size) {
    size_t total_bytes_sent = 0; 

    while(total_bytes_sent < message_size) {
        size_t message_bytes_remaining = message_size - total_bytes_sent;
        size_t bytes_to_send = chunk_size < message_bytes_remaining ? 
            chunk_size : message_bytes_remaining;

        ssize_t bytes_sent = sendto(socket_descriptor, 
            message + total_bytes_sent, 
            bytes_to_send, 
            0,
            dest_addr,
            sizeof(*dest_addr));

        if(bytes_sent == -1) {
            perror("Unable to send chunk");
            exit(1);
        }

        total_bytes_sent += bytes_sent;

        printf("Total bytes sent: %lu\n", total_bytes_sent);
    }
}

void read_message(int socket_descriptor, char* receive_buffer, size_t chunk_size) {
    size_t total_bytes_read = 0;

    while(1) {
        ssize_t bytes_read = recv(socket_descriptor, 
            receive_buffer + total_bytes_read, 
            chunk_size,
            0);

        if(bytes_read == -1) {
            perror("Error on message read");
            exit(1);
        }

        total_bytes_read += bytes_read;

        if(receive_buffer[total_bytes_read - 1] == 0) {
            break;
        }

        receive_buffer[total_bytes_read] = 0;

        printf("Partially received message: %s\n", receive_buffer);
    }
}