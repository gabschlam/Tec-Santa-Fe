# =============================================================================
# Antonio Junco de Haas A01339695 
# Alejandra Nissan Leizorek A01024682 
# Gabriel Schlam Huber A01024122 
# Héctor Reyes Manrique A0133960e
# =============================================================================

import numpy as np

def readFile(file):
	with open(file, 'r') as f:
		a = [[x for x in line.replace('{', '').replace('}', '').replace('(', '').replace(')', '').replace('\n', "").split(',')] for line in f]
	a = np.array(a)
	array2D = a.reshape((a.size//3, 3))
	return array2D

def getStatesDFA(statesNFA):
	if len(statesNFA) == 0:
		return [[]]
	r = getStatesDFA(statesNFA[:-1])
	return r + [s + [statesNFA[-1]] for s in r]

def printStatesDFA(statesDFA, file_name_DFA):
	file = open(file_name_DFA, "a")
	
	file.write("QD = {(" + str("".join(statesDFA[0])) + ")")
	for x in range(1,len(statesDFA)):
		file.write(", (" + str("".join(statesDFA[x])) + ")")
	file.write("}\n")

def getAlphabet(transitionNFA, file_name_DFA):
	file = open(file_name_DFA, "w")
	
	alphabet = []
	
	for x in range(0,len(transitionNFA)):
		alphabet = Union(alphabet, transitionNFA[x][0])
	
	file.write('\u03A3 = {' + alphabet[0])
	for x in range(1,len(alphabet)):
		file.write("," + alphabet[x])
	file.write("}\n")

	return alphabet

def getStatesNFA(transitionNFA, file_name_DFA):
	file = open(file_name_DFA, "a")
	temp = np.delete(transitionNFA, 0, 1)
	states = np.unique(temp)
	states = np.array(states)
	file.write("q0 = {" + temp[0][0] + "}\n")
	return states

def getIndexesAcceptedStatesDFA(statesDFA, acceptedStatesNFA):
	indexes=[]
	for x in range(0, len(statesDFA)):
		size = len(statesDFA[x])
		for y in range(0, size):
			for z in range(0,len(acceptedStatesNFA)):
				if statesDFA[x][y] == acceptedStatesNFA[z]:
					indexes.append(x)
	indexes = np.unique(indexes)
	return indexes

def getAcceptedStatesDFA(statesDFA, indexes, file_name_DFA):
	file = open(file_name_DFA, "a") 
	acceptedStatesDFA = []
	for x in indexes:
		acceptedStatesDFA.append(statesDFA[x])
	
	file.write("F = {(" + str("".join(acceptedStatesDFA[0])) + ")")
	for x in range(1,len(acceptedStatesDFA)):
		file.write(", (" + str("".join(acceptedStatesDFA[x])) + ")")
	file.write("}\n")
	return acceptedStatesDFA

def Union(lst1, lst2): 
	final_list = list(set(lst1) | set(lst2)) 
	final_list = np.array(final_list)
	final_list = np.sort(final_list)
	return final_list 

def getTransitionFunction(statesDFA, transitionNFA, file_name_DFA):
	file = open(file_name_DFA, "a")
	
	file.write("Transition function (\u03B4):")
		
	for x in range(0, len(statesDFA)):
		size = len(statesDFA[x])
		a = []
		b = []
		for y in range(0, size):
			for z in range(0,len(transitionNFA)):
				if transitionNFA[z][0] == '0':
					if transitionNFA[z][1] == statesDFA[x][y]:
						a = Union(a, transitionNFA[z][2])	
				elif transitionNFA[z][0] == '1':
					if transitionNFA[z][1] == statesDFA[x][y]:
						b = Union(b, transitionNFA[z][2])
		file.write("\n(0," + "".join(statesDFA[x]) + "," + "".join(a) + ")")
		file.write("\n(1," + "".join(statesDFA[x]) + "," + "".join(b) + ")")
	print("Conversion from NFA to DFA is completed!")
	print("Check: " + file_name_DFA)

def sorting(array):
	arrayTemp = sorted(np.sort(array), key = len)
	arrayTemp = np.array(arrayTemp)
	return arrayTemp

print("Make sure that the NFA has states names with one character. Example: p,q")
file_name = input ("Enter the file name where is the NFA: ")
acceptedStatesNFA = input ("Enter the accepted states, separated by a comma: ")
acceptedStatesNFA = [x.strip() for x in acceptedStatesNFA.split(',')]

transitionNFA = readFile(file_name)

file_name_DFA = "DFA"+ file_name.partition("NFA")[2]

alphabet = getAlphabet(transitionNFA, file_name_DFA)

statesNFA = getStatesNFA(transitionNFA, file_name_DFA)

statesDFA = sorting(getStatesDFA(statesNFA))

printStatesDFA(statesDFA, file_name_DFA)

indexes = getIndexesAcceptedStatesDFA(statesDFA, acceptedStatesNFA)

acceptedStatesDFA = getAcceptedStatesDFA(statesDFA, indexes, file_name_DFA)

getTransitionFunction(statesDFA, transitionNFA, file_name_DFA)
