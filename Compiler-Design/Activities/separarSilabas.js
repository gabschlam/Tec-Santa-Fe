function new_count(word) {
  word = word.toLowerCase();                                     //word.downcase!
  console.log(word)
  if(word.length <= 3) { return 1; }                             //return 1 if word.length <= 3
    word = word.replace(/(?:[^laeiouy]es|ed|[^laeiouy]e)$/, '');   //word.sub!(/(?:[^laeiouy]es|ed|[^laeiouy]e)$/, '')
    word = word.replace(/^y/, '');                                 //word.sub!(/^y/, '')
    return word.match(/[aeiouy]{1,2}/g);                    //word.scan(/[aeiouy]{1,2}/).size
}

console.log(new_count('queso'));
console.log(new_count('cuando'))
console.log(new_count('hola'))
console.log(new_count('adios'))