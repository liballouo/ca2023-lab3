.globl _start
_start:
  li a0, 0x4
  li t0, 0xDEADBEEF
  sb t0, 0(a0)
  lw t1, 0(a0)
  li s2, 0x15
  sb s2, 1(a0)
  lw ra, 0(a0)
loop:
  j loop
