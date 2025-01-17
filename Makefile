.PHONY: test verilator

test:
	sbt test

verilator:
	sbt "runMain board.verilator.VerilogGenerator"
	cd verilog/verilator && verilator --trace --exe --cc sim_main.cpp Top.v && make -C obj_dir -f VTop.mk

verilator-sim: verilator
	cd verilog/verilator && obj_dir/VTop $(SIM_TIME)

indent:
	find . -name '*.scala' | xargs scalafmt
	clang-format -i verilog/verilator/*.cpp
	clang-format -i csrc/*.[ch]

clean:
	sbt clean
	-$(MAKE) -C csrc clean
	rm -rf test_run_dir
	rm -f verilog/*.asmbin.txt
	rm -f verilog/verilator/Top.anno.json verilog/verilator/Top.fir verilog/verilator/Top.v
	rm -rf verilog/verilator/obj_dir/
	rm -rf target
	rm -rf project/project project/target

