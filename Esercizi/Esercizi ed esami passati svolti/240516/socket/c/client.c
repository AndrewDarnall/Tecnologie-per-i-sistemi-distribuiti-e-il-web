#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
	if (argc < 2) {
		fprintf(stderr, "%s number\n", argv[0]);
		return 1;
	}

	struct sockaddr_in addr = {0};
	char buf[20] = {0};
	int sfd, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = INADDR_ANY;
	addr.sin_port = htons(3333);

	ret = connect(sfd, (struct sockaddr *)&addr, sizeof(addr));
	if (ret < 0) {
		fprintf(stderr, "conn err\n");
		goto out;
	}

	ret = write(sfd, argv[1], strlen(argv[1]));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	ret = read(sfd, &buf, sizeof(buf));
	if (ret < 0) {
		fprintf(stderr, "read err\n");
		goto out;
	}

	printf("%s", buf);

out:
	close(sfd);
	return 0;
}
