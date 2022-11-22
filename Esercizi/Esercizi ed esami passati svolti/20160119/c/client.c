#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>

int main(int argc, char *argv[]) {
	if (argc < 2) {
		fprintf(stderr, "%s command\n", argv[0]);
		return 1;
	}

	struct sockaddr_in sadr = {0};
	char buf[50] = {0};
	int sfd, ret;

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(39999);

	ret = connect(sfd, (struct sockaddr *)&sadr, sizeof(sadr));
	if (ret < 0)
		goto out;

	ret = write(sfd, argv[1], strlen(argv[1]));
	if (ret < 0)
		goto out;

	ret = read(sfd, buf, sizeof(buf));
	if (ret < 0)
		goto out;

	printf("resp: %s\n", buf);

out:
	close(sfd);
	printf("exiting..\n");
	return 0;
}
