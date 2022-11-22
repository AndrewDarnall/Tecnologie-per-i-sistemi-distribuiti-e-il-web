#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <arpa/inet.h>

int main(int argc, char *argv[]) {
	if (argc != 2) {
		fprintf(stderr, "%s cmd\n", argv[0]);
		return 1;
	}

	struct sockaddr_in saddr = {0};
	char buf[100] = {0};
	int sfd, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	saddr.sin_family = AF_INET;
	saddr.sin_addr.s_addr = INADDR_ANY;
	saddr.sin_port = htons(7777);

	ret = connect(sfd, (struct sockaddr *)&saddr, sizeof(saddr));
	if (ret < 0) {
		fprintf(stderr, "conn err\n");
		goto out;
	}

	ret = write(sfd, argv[1], strlen(argv[1]));
	if (ret < 0) {
		fprintf(stderr, "write err\n");
		goto out;
	}

	ret = read(sfd, buf, 100);
	if (ret < 0) {
		fprintf(stderr, "read err\n");
		goto out;
	}

	printf("%s\n", buf);

out:
	close(sfd);
	return 0;
}
