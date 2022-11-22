#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
	struct sockaddr_in addr = {0};
	char buf[512] = {0};
	int sfd, ret;

	if (argc < 2) {
		fprintf(stderr, "%s command\n", argv[0]);
		return 1;
	}

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = INADDR_ANY;
	addr.sin_port = htons(7777);

	ret = connect(sfd, (struct sockaddr *) &addr, sizeof(addr));
	if (ret < 0) {
		fprintf(stderr, "conn err\n");
		goto out;
    }

	ret = write(sfd, argv[1], strlen(argv[1]));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	while((ret = read(sfd, buf, 511)) > 0)
		printf("%s", buf);

    if (ret < 0) {
	fprintf(stderr, "read err\n");
	goto out;
    }

out:
	close(sfd);
	return 0;
}
