av:
	gcc server.c -o av
bv:
	gcc -DB server.c -o bv
cv:
	gcc -DC server.c -o cv
dv:
	gcc -DD server.c -o dv
client: 
	gcc client.c -o client

all: av bv cv dv client
clean:
	rm -f *.o av bv cv dv client
