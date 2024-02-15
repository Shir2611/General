//Shir Matathias
//208000281


#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <signal.h>
#include <semaphore.h>

/*
 ============================================================================
 Name        : 2023_ex2_part1.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#define SNAME "MySem"

/*
 * This is a syntax assistance for part 1 question 3
 open semaphore
 sem_t *sem = sem_open(SNAME, O_CREAT, 0777, 0);

 increment semaphore
sem_post(sem);

 decrement semaphore
sem_wait(sem);
*/

int main(void) {

	int fd1[2];
	int fd2[2];
	char buff1[10];
	char buff2[10];

	if(pipe(fd1) == -1 || pipe(fd2) == -1)
	{
		printf("error");
		return -1;
	}

	int status = fork();

	if (status == 0)
	{
		//child process
		//safety first prevent reading
		close(fd1[0]);
		close(fd2[1]);

		read(fd2[0], buff2, sizeof(buff2));
		
		//write sentence into pip
		printf("No! it's not true! it's impossible\n");
		write(fd1[1], "child", 5);
		read(fd2[0], buff1, sizeof(buff1));
		printf("Noooooooooooooo\n");
		write(fd1[1], "child", 5);

		exit(0);
	}

	else
	{
		//parent process
		//lcose write end of first pipe
		close(fd1[1]);
		close(fd2[0]);

		//print the output
		printf("Luke, I am your father!\n");
		write(fd2[1], "parent", 6);
		read(fd1[0], buff2, sizeof(buff2));
		printf("Search your feelings, you know it to be true.\n");
		write(fd2[1], "parent", 6);
		read(fd1[0], buff2, sizeof(buff2));
		//write sentence to pipe
		printf("luke, you can destroy the emperor, he has foreseen it.\n");
	}
	
	return 1;
}
