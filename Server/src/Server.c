#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	int socket_desc, client_sock, c, read_size;
	struct sockaddr_in server, client;
	char client_message[3000];

	memset(client_message, 0, 3000);

	// Create socket
	socket_desc = socket(AF_INET, SOCK_STREAM, 0);
	if (socket_desc == -1)
	{
		printf("Could not create socket!");
		return -1;
	}

	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(8888);

	// Bind
	if (bind(socket_desc, (struct sockaddr *)&server, sizeof(server)) < 0)
	{
		printf("bind failed");
		return -1;
	}

	// Listen
	listen(socket_desc, 3);

	// Accept incoming connection
	puts("Waiting for connections ...");

	c = sizeof(struct sockaddr_in);

	//accept connection from incoming client
	client_sock = accept(socket_desc, (struct sockaddr *)&client, (socklen_t*)&c);
	if (client_sock < 0)
	{
		printf("Accept failed!");
		return -1;
	}

	// receive messages from client
	while ((read_size = recv(client_sock, client_message, sizeof(client_message), 0)) > 0)
	{
		puts(client_message);
		// send message back
		write(client_sock, client_message, strlen(client_message));
		memset(client_message, 0, 3000);
	}

	if (read_size == 0) {
		printf("Connection terminated, client is disconnected");
	}

	return 0;
}



