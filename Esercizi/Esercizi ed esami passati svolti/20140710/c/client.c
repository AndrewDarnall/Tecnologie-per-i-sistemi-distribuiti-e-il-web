#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

int main(int argc, char *argv[]) {
	if (argc < 2) {
		fprintf(stderr, "client sXXXX\n");
		return 1;
	}

	struct sockaddr_in adr = {0};
	char buf[10] = {0};
	int sfd, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	adr.sin_family = AF_INET;
	adr.sin_addr.s_addr = INADDR_ANY;
	adr.sin_port = htons(4242);

	ret = connect(sfd, (struct sockaddr *)&adr, sizeof(adr));
	if (ret < 0) {
		fprintf(stderr, "conn err\n");
		goto out;
	}

	snprintf(buf, sizeof(buf), "%s\n", argv[1]);

	ret = write(sfd, buf, strlen(buf));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	ret = read(sfd, buf, 10);
	if (ret < 0) {
		fprintf(stderr, "read err\n");
		goto out;
	}

	printf("res: %s", buf);

out:
	close(sfd);
	return 0;
}
