/*
    Bubble Sort C++
    Análisis y diseño de algoritmos

    Gabriel Schlam
*/

#include<stdlib.h>
#include<stdio.h>
#include<iostream>

void bubbleSort(int arr[], int size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - 1 - i; j++) {
            //Comparacion entre elemento y elemento siguiente. Si es menor el primero, se intercambia
            if (arr[j] > arr[j+1]) {
                // Intercambio entre los elementos
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

void printArray(int arr[], int size){
    for (int i=0; i < size; i++)
        std::cout << arr[i] << " ";
    std::cout << "\n";
}

int main() {
    int n;
    
    printf("Dame la cantidad de valores a introducir: ");
    scanf("%d", &n);
    
    int arreglo[n];
    
    printf("ingresa los numeros a ordenar: ");
    for (int i = 0; i<n; i++) {
        scanf("%d,", &arreglo[i]);
    }
    
    bubbleSort(arreglo, n);
    
    printf("El Bubble Sort es: \n");
    printArray(arreglo, n);
    return 0;
}
