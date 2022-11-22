CC=gcc
#DEFS=-Ddebug
DEFS=
CFLAGS=-Wall $(DEFS)
LDFLAGS=-lpthread

all: prodcons prodcons1 account

hello: hello.o gettid.o

account: accountmain.o account.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

account0: account01main.o account0.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

account1: account01main.o account1.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

account2: account2main.o account2.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

prodcons: prodconsmain.o synqueue.o show.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

prodcons1: prodconsmain.o synqueue1.o show.o
	$(CC) $(CFLAGS) $(LDFLAGS) $+ -o $@

synqueue.c: synqueue.h
synqueue1.c: synqueue.h
prodconsmain.c: synqueue.h

clean: 
	rm -f *.o *~ prodcons prodcons1 passargs philosophers hello? account? hello thrdmutex prodcons1shot_broad hit account

