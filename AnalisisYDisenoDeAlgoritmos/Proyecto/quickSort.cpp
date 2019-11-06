/*
    Quick Sort C++
    Análisis y diseño de algoritmos

    Gabriel Schlam
*/

#include<stdlib.h>
#include<stdio.h>
#include<iostream>

int particion(int arr[], int izq, int der){
    int pivot = arr[der]; //Tomamos pivote como ultimo elemento
    int i = izq - 1;
    int temp;
    
    for (int j = izq; j < der; j++) {
        //Si el elemento del arreglo en posicion j es menor o igual al pivote
        if (arr[j] <= pivot) {
            i++;
            //Intercambio de elemento en posicion i y posicion j
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    //Intercambio de elemento en posicion i+i y posicion del elemento der
    temp = arr[i+1];
    arr[i+1] = arr[der];
    arr[der] = temp;
    
    return i+1; //Regresa posicion i+1
}

void quickSort(int arr[], int izq, int der){
    if (izq < der) { //Si izq es menor a der
        int i = particion(arr, izq, der);
        quickSort(arr, izq, i-1); // QuickSort de mitad izquierda
        quickSort(arr, i+1, der); // QuickSort de mitad derecha
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

    quickSort(arreglo, 0, n-1);
    printf("El Quick Sort es: \n");
    printArray(arreglo, n);
    return 0;
}
