uCOS-II Scheduler Simulator
===========================

A simple Java Swing based application to simulate and visualize the operation of the scheduler in the [uCOS-II](http://en.wikipedia.org/wiki/MicroC/OS-II) RTOS,
developed as an assignment for an Embedded Systems module. 
>uCOS-II is a priority-based pre-emptive real-time multitasking operating system kernel for microprocessors,
>written mainly in the C programming language.

![uCOS-II Scheduler Simulator Screenshot](https://github.com/murjay/uCOS-II-Scheduler-Simulator/raw/master/doc/ucossim_screenshot.png)


Operation
---------

- A blue square in the OSRdyTbl indcates that the task associated with that priority is running
- A green square indicates that an element is enabled
- A red element indicates that an element is disabled

Dependencies 
------------

uCOS-II Scheduler Simulator is written in Java and hence requires the [Java Runtime Enviroment](http://www.oracle.com/technetwork/java/javase/downloads/jre7u9-downloads-1859586.html) (Version 7 or later).
