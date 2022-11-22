	.section	__TEXT,__text,regular,pure_instructions
	.macosx_version_min 10, 13
	.globl	_print_message_fun
	.p2align	4, 0x90
_print_message_fun:                     ## @print_message_fun
	.cfi_startproc
## BB#0:
	pushq	%rbp
Lcfi0:
	.cfi_def_cfa_offset 16
Lcfi1:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
Lcfi2:
	.cfi_def_cfa_register %rbp
	subq	$48, %rsp
	movq	%rdi, -16(%rbp)
	movq	-16(%rbp), %rdi
	movq	%rdi, -24(%rbp)
	movq	-24(%rbp), %rdi
	movsbl	(%rdi), %edi
	callq	_toupper
	subl	$65, %eax
	movl	%eax, %edi
	callq	_sleep
	movl	%eax, -44(%rbp)         ## 4-byte Spill
	callq	_getpid
	leaq	L_.str(%rip), %rdi
	movq	-24(%rbp), %rdx
	movl	%eax, %esi
	movb	$0, %al
	callq	_printf
	movabsq	$1311768467750121251, %rdi ## imm = 0x12345678ABCDEF23
	movl	$2, %esi
	movl	%esi, %edx
	movl	$1, %esi
	movl	%esi, %ecx
	movq	-24(%rbp), %r8
	movsbl	(%r8), %esi
	cmpl	$67, %esi
	cmoveq	%rcx, %rdx
	movq	%rdx, -32(%rbp)
	movq	-32(%rbp), %rcx
	movq	%rcx, -40(%rbp)
	movl	%eax, -48(%rbp)         ## 4-byte Spill
	callq	_pthread_exit
	.cfi_endproc

	.globl	_main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## BB#0:
	pushq	%rbp
Lcfi3:
	.cfi_def_cfa_offset 16
Lcfi4:
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
Lcfi5:
	.cfi_def_cfa_register %rbp
	subq	$224, %rsp
	movq	___stack_chk_guard@GOTPCREL(%rip), %rax
	movq	(%rax), %rax
	movq	%rax, -8(%rbp)
	movl	%edi, -116(%rbp)
	movq	%rsi, -128(%rbp)
	movl	L_main.message1(%rip), %edi
	movl	%edi, -166(%rbp)
	movw	L_main.message1+4(%rip), %cx
	movw	%cx, -162(%rbp)
	cmpl	$1, -116(%rbp)
	jne	LBB1_2
## BB#1:
	leaq	L_.str.1(%rip), %rsi
	movq	___stderrp@GOTPCREL(%rip), %rax
	movq	(%rax), %rdi
	movq	-128(%rbp), %rax
	movq	(%rax), %rdx
	movb	$0, %al
	callq	_fprintf
	leaq	L_.str.2(%rip), %rsi
	movq	___stderrp@GOTPCREL(%rip), %rdx
	movq	(%rdx), %rdi
	movl	%eax, -180(%rbp)        ## 4-byte Spill
	movb	$0, %al
	callq	_fprintf
	leaq	L_.str.3(%rip), %rsi
	movq	___stderrp@GOTPCREL(%rip), %rdx
	movq	(%rdx), %rdi
	movl	%eax, -184(%rbp)        ## 4-byte Spill
	movb	$0, %al
	callq	_fprintf
	movl	$5, %edi
	movl	%eax, -188(%rbp)        ## 4-byte Spill
	callq	_exit
LBB1_2:
	xorl	%esi, %esi
	movl	$100, %eax
	movl	%eax, %edx
	leaq	L_.str.4(%rip), %rcx
	leaq	-112(%rbp), %rdi
	movq	-128(%rbp), %r8
	movq	8(%r8), %r8
	movb	$0, %al
	callq	___sprintf_chk
	leaq	-136(%rbp), %rdi
	xorl	%esi, %esi
                                        ## kill: %RSI<def> %ESI<kill>
	leaq	_print_message_fun(%rip), %rdx
	leaq	-166(%rbp), %rcx
	movl	%eax, -192(%rbp)        ## 4-byte Spill
	callq	_pthread_create
	leaq	-144(%rbp), %rdi
	xorl	%r9d, %r9d
	movl	%r9d, %esi
	leaq	_print_message_fun(%rip), %rdx
	leaq	-112(%rbp), %rcx
	movl	%eax, -196(%rbp)        ## 4-byte Spill
	callq	_pthread_create
	leaq	-152(%rbp), %rsi
	movq	-136(%rbp), %rdi
	movl	%eax, -200(%rbp)        ## 4-byte Spill
	callq	_pthread_join
	leaq	-160(%rbp), %rsi
	movq	-144(%rbp), %rdi
	movl	%eax, -204(%rbp)        ## 4-byte Spill
	callq	_pthread_join
	leaq	L_.str.5(%rip), %rdi
	movq	-160(%rbp), %rcx
	movq	%rcx, -176(%rbp)
	movq	-176(%rbp), %rcx
	addq	$2, %rcx
	movq	%rcx, -176(%rbp)
	movq	-176(%rbp), %rsi
	movl	%eax, -208(%rbp)        ## 4-byte Spill
	movb	$0, %al
	callq	_printf
	leaq	L_.str.6(%rip), %rdi
	movq	-152(%rbp), %rsi
	movq	-160(%rbp), %rdx
	movl	%eax, -212(%rbp)        ## 4-byte Spill
	movb	$0, %al
	callq	_printf
	movl	%eax, -216(%rbp)        ## 4-byte Spill
	callq	_getpid
	leaq	L_.str.7(%rip), %rdi
	movl	%eax, %esi
	movb	$0, %al
	callq	_printf
	movq	___stack_chk_guard@GOTPCREL(%rip), %rcx
	movq	(%rcx), %rcx
	movq	-8(%rbp), %rdx
	cmpq	%rdx, %rcx
	movl	%eax, -220(%rbp)        ## 4-byte Spill
	jne	LBB1_4
## BB#3:
	xorl	%eax, %eax
	addq	$224, %rsp
	popq	%rbp
	retq
LBB1_4:
	callq	___stack_chk_fail
	.cfi_endproc

	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"\npid %d: %s"

L_main.message1:                        ## @main.message1
	.asciz	"Ciao\n"

L_.str.1:                               ## @.str.1
	.asciz	"Usage: %s STRING\n"

L_.str.2:                               ## @.str.2
	.asciz	"    due thread distinti scrivono su stdout, uno scrive \"Ciao\" e l'altro \n"

L_.str.3:                               ## @.str.3
	.asciz	"    scrive STRING con ritardo dato da STRING[0] (0 per 'a', 1 per 'b'...)\n"

L_.str.4:                               ## @.str.4
	.asciz	"%s\n"

L_.str.5:                               ## @.str.5
	.asciz	"%ld\n"

L_.str.6:                               ## @.str.6
	.asciz	"\nthread1->%ld, thread2->%ld\n"

L_.str.7:                               ## @.str.7
	.asciz	"\nThread padre pid=%d  terminato\n\n"


.subsections_via_symbols
