CFLAGS=-Wall

EXES=fingerc fingers addrcnv byaddr byname gethost \
unclnt unsrvr inclnt insrvr inskelsrvr inparsrvr mboxclnt mboxsrvr nblksock \
sndrcvClessClnt sndrcvClessSrvr \
sndrcvConnClnt sndrcvConnClntStream sndrcvConnSrvrStream

all: $(EXES)

$(EXES): mkaddr.o

byaddr: printaddr.o byaddr.o
byname: printaddr.o byname.o
gethost: printaddr.o gethost.o
getmsgTout.o: getmsg.o
inclnt: getmsg.o getmsgTout.o inclnt.o
insrvr: insrvr.o doserv.o
inskelsrvr: inskelsrvr.o doserv.o
inparsrvr: inparsrvr.o doserv.o
sndrcvConnSrvrStream: sndrcvConnSrvrStream.o doserv.o
mboxsrvr: mboxsrvr.o
mboxclnt: mboxclnt.o

clean:
	-rm *.o
	-rm $(EXES)
