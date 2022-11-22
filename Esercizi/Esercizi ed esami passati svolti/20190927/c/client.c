#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[]) {
	struct sockaddr_in sadr = {0};
	char buf[20] = {0};
	int sfd, ret;

	if (argc < 2) {
		fprintf(stderr, "%s cmd", argv[0]);
		return 1;
	}

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		fprintf(stderr, "sock err\n");
		return 1;
	}

	sadr.sin_family = AF_INET;
	sadr.sin_addr.s_addr = INADDR_ANY;
	sadr.sin_port = htons(9999);

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
	return 0;
}
