#include<stdio.h>
#include<string.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>

char* getSystemData();

int main(int argc , char* argv[])
{
    int sock, iResult;
    struct sockaddr_in server;
    char* message;

    //Create socket
    sock = socket(AF_INET , SOCK_STREAM , 0);
    if (sock == -1)
    {
        printf("Could not create socket");
        return -1;
    }
    puts("Socket created");

    server.sin_addr.s_addr = inet_addr(argv[1]);
    server.sin_family = AF_INET;
    server.sin_port = htons( atoi(argv[2]) );

    //Connect to remote server
    iResult = connect(sock , (struct sockaddr *)&server , sizeof(server));
    if (iResult < 0)
    {
        printf("Connect failed!");
        close(sock);
        return -1;
    }
    puts("Connected\n");

    message = getSystemData();

    //Send data
    iResult = send(sock , message , strlen(message) , 0);
	if( iResult < 0)
	{
		printf("Send failed!");
		close(sock);
		return -1;
	}
	puts("Message send!");
    close(sock);
    return 0;
}

char* getSystemData()
{
	long processorsOnline = -1;
	unsigned long usableMemory, t1, t2;

	processorsOnline = sysconf(_SC_NPROCESSORS_ONLN);
	t1 = get_phys_pages();
	t2 = getpagesize();
	usableMemory = t1 * t2;

	const int length = snprintf(NULL, 0, "%ld", processorsOnline);
	const int length2 = snprintf(NULL, 0, "%lu", usableMemory);
	char* buf;
	const int length3 = strlen("{\"processors\":,\"memory\":");

	buf = malloc(length + length2 + length3 + 1);
	snprintf(buf, length + length2 + + length3 + 1, "{\"processors\":%ld,\"memory\":%lu}", processorsOnline, usableMemory);

	return buf;

}
