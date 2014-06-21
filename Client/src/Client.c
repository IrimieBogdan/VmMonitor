#include<stdio.h>
#include<string.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>

/**
 * socket used to connect to server
 */
int sock;
/**
 * Structure with server data
 */
struct sockaddr_in server;

int openConnection(char* ip, char* port);
int sendMessage(char* message);
int closeConnection();
char* getSystemData();


int main(int argc , char* argv[])
{
    char* systemDetails;
    systemDetails = getSystemData();
    openConnection(argv[1], argv[2]);
    sendMessage(systemDetails);
    closeConnection();
    return 0;
}

/**
 * Open connection to server.
 *
 * @param ip	ip to connect to.
 * @param port	port used for connection.
 * @return	0 success, -1 fail to connect to server
 */
int openConnection(char* ip, char* port)
{
	int iResult;

	//Create socket
	sock = socket(AF_INET , SOCK_STREAM , 0);
	if (sock == -1)
	{
		printf("Could not create socket");
		return -1;
	}
	puts("Socket created");

	server.sin_addr.s_addr = inet_addr(ip);
	server.sin_family = AF_INET;
	server.sin_port = htons( atoi(port) );

	//Connect to remote server
	iResult = connect(sock , (struct sockaddr *)&server , sizeof(server));
	if (iResult < 0)
	{
		printf("Connect failed!");
		close(sock);
		return -1;
	}
	puts("Connected\n");
	return iResult;
}

/**
 * Send message over the socket.
 *
 * @param message	Text to send over the socket.
 * @return 			Number of bytes send.
 */
int sendMessage(char* message)
{
	int iResult;

    iResult = send(sock , message , strlen(message) , 0);
	if( iResult < 0)
	{
		printf("Send failed!");
		closeConnection();
		return -1;
	}
	puts("Message send!");
	return iResult;
}

/**
 * Close socket
 *
 * @return	0 success, -1 fail to connect to server
 */
int closeConnection()
{
	return close(sock);
}

/**
 * Get cpu count and memory and create a string in Json format.
 *
 * @return 	a string in Json format with system details.
 */
char* getSystemData()
{
	long processorsOnline = -1;
	unsigned long usableMemory, t1, t2;
	char* buf;

	processorsOnline = sysconf(_SC_NPROCESSORS_ONLN);
	t1 = get_phys_pages();
	t2 = getpagesize();
	usableMemory = t1 * t2;

	const int length = snprintf(NULL, 0, "%ld", processorsOnline);
	const int length2 = snprintf(NULL, 0, "%lu", usableMemory);
	const int length3 = strlen("{\"processors\":,\"memory\":}");

	buf = malloc(length + length2 + length3 + 1);
	snprintf(buf, length + length2 + + length3 + 1, "{\"processors\":%ld,\"memory\":%lu}", processorsOnline, usableMemory);

	return buf;
}
