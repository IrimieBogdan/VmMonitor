#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main(void)
{
	long processorsOnline = -1;
	unsigned long usableMemory, t1, t2;

	processorsOnline = sysconf(_SC_NPROCESSORS_ONLN);
	t1 = get_phys_pages();
	t2 = getpagesize();
	usableMemory = t1 * t2;

	printf("Processor count: %ld\n", processorsOnline);
	printf("Total usable memory: %lu\n", usableMemory);
	exit (EXIT_SUCCESS);
}
