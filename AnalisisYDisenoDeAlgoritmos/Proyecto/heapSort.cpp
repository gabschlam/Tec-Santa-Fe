/*
    Heap Sort C++
    Análisis y diseño de algoritmos

    Gabriel Schlam
*/

#include<iostream>
#include<stdio.h>


//Changes the first value in the array
//for the one with the max value in it
void Heapify(int arr[], int n, int i) {
    int max = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    if (left < n && arr[left] > arr[i])
        max = left;
    else
        max = i;
    
    if (right < n && arr[right] > arr[max])
        max = right;
    
    if (max != i) {
        std::swap(arr[i], arr[max]);
        Heapify(arr, n, max);
    }
}

//Puts the element with greater value at the
//begining of the array
void BuildMaxHeap(int arr[], int n) {
    for (int i = n / 2 - 1; i >= 0; i--)
        Heapify(arr, n, i);
}

//Max value changes positioon with the last
//in the array
void HeapSort(int arr[], int n) {
    BuildMaxHeap(arr, n);
    for (int i = n-1; i >=0; i--) {
        std::swap(arr[0], arr[i]);
        n = n - 1;
        Heapify(arr, n, 0);
    }
}

//Print function
void printSort(int arr[], int n) {
    for (int i = 0; i < n; i++)
        std::cout << arr[i] << " ";
    std::cout << "\n";
}



int main() {
    int numdatos;
    
    printf("Ingresa la cantidad de numeros en el arreglo: ");
    scanf("%d", &numdatos);
    int  *arr = new int[numdatos];
    
    printf("Ingresa los numeros a ordenar: ");
    for (int i = 0; i < numdatos; i++)
        scanf("%d,", &arr[i]);
    
    
    HeapSort(arr, numdatos);
    
    printf("El Heap Sort es: \n");
    printSort(arr, numdatos);
}
