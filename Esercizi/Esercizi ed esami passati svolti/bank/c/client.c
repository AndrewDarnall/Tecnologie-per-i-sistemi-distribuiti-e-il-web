#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
	struct sockaddr_in sadr = {0};
	char buf[20] = {0};
	int sfd, ret;

	if (argc < 2){
		fprintf(stderr, "%s command\n", argv[0]);
		return 1;
	}

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(7777);

	ret = connect(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0) {
		fprintf(stderr, "conn err\n");
		goto out;
	}

	ret = write(sfd, argv[1], strlen(argv[1]));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	ret = read(sfd, buf, 20);
	if (ret < 0) {
		fprintf(stderr, "read err\n");
		goto out;
	}

	printf("%s\n", buf);

out:
	close(sfd);
	return 0;
}
