#include <math.h>

#include "IDL.h"

int *cubo_10_svc(int *argp, struct svc_req *rqstp) {
	static int  result;

	result = pow(*argp, 3);

	return &result;
}
