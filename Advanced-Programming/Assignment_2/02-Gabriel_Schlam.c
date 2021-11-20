/*
	Assignment 2
	Advanced Programming
 
	Gabriel Schlam 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define FILE_SIZE 50

// Definition of data types
typedef struct matrix {
	int row; // Number of rows
	int col; // Number of columns
	float ** data; // Data stored un matrix
} matrix_t;

// Function declarations
matrix_t * initMat(int row, int col);
matrix_t * getMatrix(char file_name[FILE_SIZE]);
void multMatrix(char file_res[FILE_SIZE], matrix_t * mat1, matrix_t * mat2);
void printMatrix(matrix_t * mat);
void printMatrixOnFile(char file_res[FILE_SIZE], matrix_t * mat);
void freeMat(matrix_t * mat);


int main(int argc, char * argv[]) 
{
	int option;
	char file_mat1[FILE_SIZE];
	char file_mat2[FILE_SIZE];
	char file_res[FILE_SIZE];

	matrix_t * ptr_mat1 = NULL;
	matrix_t * ptr_mat2 = NULL;

	// Recieve the arguments for the files' names.
	while ((option = getopt(argc, argv, "1:2:r:")) != -1) {
		switch(option){
			case '1': // First matrix file to multiply
				strncpy(file_mat1, optarg, FILE_SIZE);
				break;
			case '2': // Second matrix file to multiply
				strncpy(file_mat2, optarg, FILE_SIZE);
				break;
			case 'r': // Matrix file for saving the result
				strncpy(file_res, optarg, FILE_SIZE);
				break;
			case '?':
				printf("Unknown option: %c\n", option);
				break;
		}
	}

	ptr_mat1 = getMatrix(file_mat1); // Receive pointer to first matrix's structure
	ptr_mat2 = getMatrix(file_mat2); // Receive pointer to second matrix's structure

	multMatrix(file_res, ptr_mat1, ptr_mat2); // Multiply both matrices by passing their pointers

	// Free space in memory
	freeMat(ptr_mat1);
	freeMat(ptr_mat2);

	return 0;
}

matrix_t * initMat(int row, int col) 
{
	matrix_t * mat = NULL;

	// Get memory for the structure
	mat = malloc(sizeof (matrix_t));

	// Get memory for the internal matrix

	// Single malloc for the indices of the rows
	mat->data = (float**) malloc(row * sizeof(float*));

	// Store the pointer as the first index in the rows' array
	mat->data[0] = (float*) malloc(row * col * sizeof(float));

    // Store the indices of where every row begins
	for (int i = 1; i < row; i++) {
		mat->data[i] = mat->data[i-1] + col;
	}

	// Store the size
	mat->row = row;
	mat->col = col;

	return mat; // Return matrix structure
}

matrix_t * getMatrix(char file_name[FILE_SIZE])
{
	FILE *file;
	matrix_t * mat = NULL;

	int row;
	int col;

	file = fopen(file_name, "r"); // Open file for reading

	if (file == NULL) {
		printf("Opening file '%s' failed\n", file_name);
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	fscanf(file, "%d %d", &row, &col); // Save the size of the matrix

	mat = initMat(row, col); // Initialize matrix structure

	// Save the matrix data
	for (int i = 0; i < row; i++) {
		for (int j = 0; j < col; j++) {
			fscanf(file, "%f ", &mat->data[i][j]);
		}
	}

	fclose(file);

	return mat;	// Return matrix structure
}

void multMatrix(char file_res[FILE_SIZE], matrix_t * mat1, matrix_t * mat2)
{
	matrix_t * resp = NULL;

	// Check if the matrices can be multiplied
	if (mat1->col != mat2->row) {
		printf("Cannot multiply matrices\n");
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	resp = initMat(mat1->row, mat2->col); // Initialize matrix structure for the result

	for (int i = 0; i < mat1->row; i++) { 
		for (int j = 0; j < mat2->col; j++) {
			resp->data[i][j] = 0; // Restart the summation at the start of the loop
			for (int k = 0; k < mat2->row; k++) {
				resp->data[i][j] += mat1->data[i][k] * mat2->data[k][j]; 
			}
		} 
	}

	printMatrixOnFile(file_res, resp); 	// Save matrix on file
}

void printMatrix(matrix_t * mat)
{
	for (int i = 0; i < mat->row; i++) {
		for (int j = 0; j < mat->col; j++) {
			printf("%.2f\t", mat->data[i][j]);
		}
		printf("\n");	
	}
}

void printMatrixOnFile(char file_res[FILE_SIZE], matrix_t * resp)
{
	FILE *file;

	file = fopen(file_res, "w"); // Open file for writing

	if (file == NULL) {
		printf("Opening file '%s' failed\n", file_res);
		printf("Exiting program...\n");
		exit(EXIT_FAILURE);
	}

	fprintf(file, "%d %d\n", resp->row, resp->col); // Write size in file

	for (int i = 0; i < resp->row; i++) {
		for (int j = 0; j < resp->col; j++) {
			fprintf(file, "%.2f\t", resp->data[i][j]); // Write data of matrix in file
		}
		fprintf(file, "\n");
	}

	printf("Check file '%s' for result\n", file_res);

	fclose(file);

	freeMat(resp); // Free space in memory
}

void freeMat(matrix_t * mat)
{
	free(mat->data[0]);
	free(mat->data);
	free(mat);
}