// definizione condizionale (rispetto alla piattaforma)

#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>

unsigned int gettid()
{
#ifdef __linux__
	return (unsigned int) syscall(SYS_gettid);
#elif __APPLE__
	uint64_t res;
	pthread_threadid_np(NULL,&res);
	return (unsigned int) res;
#else
	return 0;
#endif
}
