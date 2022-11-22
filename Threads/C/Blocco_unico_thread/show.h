/* show.h */

void show_val(char * why, long int val, void * q);
void show_q(char * why, void * q);

void show_str(char * why, char * msg);

int get_myindex();

void show_q(char * why, void * queue);

#ifdef debug
#define SHOW_Q(action, q) show_q(action, q);
#else 
#define SHOW_Q(action, q) 
#endif

#define WAIT(cond,mutex) {\
    SHOW_Q("sleep", q);\
    pthread_cond_wait(cond, mutex);\
    SHOW_Q("wakeup", q);}

#define SIGNAL(cond) {\
    SHOW_Q("signal", q);\
    pthread_cond_signal(cond);}

#define BROADCAST(cond) {\
    SHOW_Q("broad", q);\
    pthread_cond_broadcast(cond);}

#define LOCK(mutex) pthread_mutex_lock(mutex)

#define UNLOCK(mutex) {\
    show_val((get_myindex()>0) ? "get" : "put", val, q);\
    pthread_mutex_unlock(mutex);}

