/*
    Merge Sort C++
    Análisis y diseño de algoritmos

    Gabriel Schlam
*/

#include<stdlib.h>
#include<stdio.h>
#include<iostream>

void merge(int arr[], int izq, int med, int der) {
    int i, j, k;
    int n1 = med - izq + 1;
    int n2 =  der - med;
    
    //Creacion de arreglos temporales y copia de datos a los mismos
    int L[n1], H[n2];
    
    for (i = 0; i < n1; i++)
        L[i] = arr[izq + i];
    for (j = 0; j < n2; j++)
        H[j] = arr[med + 1+ j];
    
    i = 0;
    j = 0;
    k = izq;
    
    //Mientras que indice i y j sean menores a n1 y n2 respectivamente
    while (i < n1 && j < n2) {
        if (L[i] <= H[j]) {
            arr[k] = L[i];
            i++;
        }
        else {
            arr[k] = H[j];
            j++;
        }
        k++;
    }
    
    //Si quedan elementos en Arreglos temporales, copiarlos
    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }
    while (j < n2) {
        arr[k] = H[j];
        j++;
        k++;
    }
}

void mergeSort(int arr[], int izq, int der) {
    // Si izq es menor a der
    if (izq < der) {
        int med = izq +(der-izq)/2;
        
        mergeSort(arr, izq, med); // MergeSort mitad izquierda
        mergeSort(arr, med+1, der); // MergeSort mitad derecha
        
        merge(arr, izq, med, der); //Union de elementos
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
    
    printf("Ingresa los numeros a ordenar: ");
    for (int i = 0; i<n; i++) {
        scanf("%d,", &arreglo[i]);
    }
    
    mergeSort(arreglo, 0, n - 1);
    
    printf("El Merge Sort es: \n");
    printArray(arreglo, n);
    return 0;
}
