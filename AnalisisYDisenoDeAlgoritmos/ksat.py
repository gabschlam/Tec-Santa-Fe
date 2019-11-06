
# Cálculo de KSAT desde archivo
# Análisis y diseño de algoritmos

# Gabriel Schlam


import re
from itertools import islice

def getFirstLine(file):
	a = []
	with open(file, 'r') as file:
		first_line = file.readline()
		for i in re.findall(r'\d+', first_line):
			a.append(int(i))
	return a

def getNumbers(file, a):
	c = []
	with open(file, 'r') as file:
		for line in islice(file, 1, a[1]+1):
			c.append(line.rstrip('\n\r').split())
	num = [list(map(int,i)) for i in c]
	return num

def getValues(file, a):
	values = []
	with open(file, 'r') as file:
		for line in islice(file, a[1]+1, a[1]+2):
			values = list(map(int,str(line)))
	return values

def kSat(numbers, values):
	for i in range(0,len(numbers)):
		for j in range(0,len(numbers[0])):
			num = numbers[i][j]
			num_abs = abs(num)
			resp_claus = None;
			if(num < 0):
				resp_claus = not(values[num_abs-1])
			else:
				resp_claus = bool(values[num_abs-1])
			if (resp_claus == True):
				break
		if (resp_claus == False):
			print("Result: 0")
			return
	print("Result: 1")

selectfile = input("Enter Filename: ")

a = getFirstLine(selectfile)

numbers = getNumbers(selectfile, a)

values = getValues(selectfile, a)

kSat(numbers, values)

