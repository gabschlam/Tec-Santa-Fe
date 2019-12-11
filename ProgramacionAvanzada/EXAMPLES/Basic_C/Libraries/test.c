/*
 * Examples of using struct / union / enum / typedef
 *
 * Gilberto Echeverria
 * 19/08/2019
 */

#include <stdio.h>
#include <string.h>
#include "person.h"

int main()
{
    person_t Gabriel;

    strncpy(Gabriel.name, "Gabriel Schlam", NAME_LENGTH);
    //sprintf(Gil.name, "Gilberto Echeverria %d %f", Gil.age, Gil.height);
    Gabriel.age = 21;
    Gabriel.height = 1.80;
    Gabriel.weight = 70;

    printPerson(Gabriel);

    return 0;
}
