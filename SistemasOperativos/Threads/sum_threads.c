/*
	Threads: Matrix Sum
	Operating Systems
 
	Gabriel Schlam
*/

#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#define M 3
#define N 3

struct p{
	int i;
	int j;
};

int A[M][N] = {{1,2,3}, {4,5,6}, {7,8,9}};
int B[M][N] = {{1,2,3}, {4,5,6}, {7,8,9}};
int C[M][N];

void* suma(void* pos){
	struct p* pos1 = (struct p*)pos;
	C[pos1->i][pos1->j] = A[pos1->i][pos1->j] + B[pos1->i][pos1->j];
}

int main(){
	pthread_t T[M][N];
	struct p* posiciones[M][N];
	int i;
	int j;
	
	for(i=0; i<M; i++){
		for(j=0; j<N; j++){
			posiciones[i][j] =(struct p*) malloc(sizeof(struct p));
			posiciones[i][j]->i=i;
			posiciones[i][j]->j=j;
			pthread_create(&T[i][j], NULL, suma, (void*)posiciones[i][j]);
		}
	}
	
	for(i=0; i<M; i++){
		for(j=0; j<N; j++){
			pthread_join(T[i][j], NULL);
			free(posiciones[i][j]);
		}
	}
	
	for(i=0; i<M; i++){
		for(j=0; j<N; j++){
			printf("%d	", C[i][j]);
		}
		printf("\n");
	}
}
