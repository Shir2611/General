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

    //create 2 new  and open them:
    sem_t *sem1 = sem_open("sem1", O_CREAT, 0777, 0);
    sem_t *sem2 = sem_open("sem2", O_CREAT, 0777, 0);

    int status;
    status = fork();

    if (status == 0)
    {
        //wait for sem1 before printing
        sem_wait(sem1);  
        printf("No! it's not true! it's impossible\n");
        //release sem2 for the parent process to proceed
        sem_post(sem2);
        sem_wait(sem1);
        printf("Noooooooooooooo\n");
        sem_post(sem2);

        exit(0);
    }

    else
    {
        printf("Luke, I am your father!\n");
        //release sem1 for the child process to proceed
        sem_post(sem1);
        //wait for sem2 before printing 
        sem_wait(sem2);  
        printf("Search your feelings, you know it to be true.\n");
        //release sem1 for the child process to proceed
        sem_post(sem1);
        //wait for sem2 before printing  
        sem_wait(sem2); 
        printf("luke, you can destroy the emperor, he has foreseen it.\n");
    }
    
    //at the end - cleanup: we unlink sem1 and sem2
    sem_unlink("sem1"); 
    sem_unlink("sem2"); 

    return 1;
}
