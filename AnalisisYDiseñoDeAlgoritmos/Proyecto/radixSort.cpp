/*
    Radix Sort C++
    Análisis y diseño de algoritmos

    Gabriel Schlam
*/

#include <iostream>

//Function to get the max value in the Array
int getMax(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++)
        if (max < arr[i])
            max = arr[i];
    return max;
}

// Uses the Count Sort method to order the values in the output
void countSort(int arr[], int n, int digit) {
    int* output = new int[n];
    int bucket[10] = {0};
    
    for (int i = 0; i < n; i++)
        bucket[(arr[i] / digit) % 10]++;
    
    for (int i = 1; i < 10; i++)
        //Add the value to the corresponding Bucket
        //depending on the digit that we are on
        bucket[i] += bucket[i - 1];
    
    for (int i = n - 1; i >= 0; i--) {
        //Pass the value of each bucket to the output array
        //Bottom first
        //The bucket-0 goes out first
        output[bucket[(arr[i] / digit) % 10] - 1] = arr[i];
        bucket[(arr[i] / digit) % 10]--;
    }
    
    for (int i = 0; i < n; i++)
        arr[i] = output[i];
    
    delete[] output;
}

//This function is what makes different Radix Sort
//from the Count Sort
void Radixsort(int arr[], int n) {
    int max = getMax(arr, n);
    //This will give the value to the digit
    //and will do the for for each digit the max value has
    for (int digit = 1; max / digit > 0; digit *= 10)
        countSort(arr, n, digit);
}

//Print Function
void printSort(int arr[], int n) {
    for (int i = 0; i < n; i++)
        std::cout << arr[i] << " ";
    std::cout << "\n";
}


int main() {
    int numDatos;
    
    printf("Ingresa la cantidad de numeros en el arreglo: ");
    scanf("%d", &numDatos);
    int *arr = new int[numDatos];
    
    printf("Ingresa los numeros a ordenar: ");
    for (int i = 0; i < numDatos; i++) {
        scanf("%d,", &arr[i]);
    }
    
    Radixsort(arr, numDatos);
    
    printf("El Radix Sort es: \n");
    printSort(arr, numDatos);
}
