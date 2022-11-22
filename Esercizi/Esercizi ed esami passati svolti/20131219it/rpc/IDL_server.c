#include "IDL.h"

int *conta_10_svc(char **argp, struct svc_req *rqstp) {
	static int  result;

	result = strlen(*argp);

	return &result;
}
