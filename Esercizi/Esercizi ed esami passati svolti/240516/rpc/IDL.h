/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _IDL_H_RPCGEN
#define _IDL_H_RPCGEN

#include <rpc/rpc.h>


#ifdef __cplusplus
extern "C" {
#endif


#define CUBO_RPC 20
#define CUBO_VER 10

#if defined(__STDC__) || defined(__cplusplus)
#define cubo 1
extern  int * cubo_10(int *, CLIENT *);
extern  int * cubo_10_svc(int *, struct svc_req *);
extern int cubo_rpc_10_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define cubo 1
extern  int * cubo_10();
extern  int * cubo_10_svc();
extern int cubo_rpc_10_freeresult ();
#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_IDL_H_RPCGEN */
