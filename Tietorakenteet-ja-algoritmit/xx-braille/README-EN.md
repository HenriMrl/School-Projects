# Braille converter optional exercise

Data structures and algorithms 2022.

## The goal

This *optional* exercise is about:

1. [Braille](https://en.wikipedia.org/wiki/Braille) symbols, used by blind people to read.
2. Implementing Hash tables.

Note that braille symbols and how they relate to "normal" characters is *language specific*. For example, Finnish language braille symbols may be different than the ones used in, let's say English. In this exercise we use the Finnish braille symbol set ([cf fi.wikipedia](https://fi.wikipedia.org/wiki/Pistekirjoitus#Unicode)).

The goal of this exercise is to:

* Study how a naive converter converting text (like books) to braille symbol text files work.
* Analyse the time complexity of the above algorithm.
* Implement a hash table version of the converter.
* Compare the time complexities and time performance of the first given implementation and your hash table implementation.

**ATTENTION** Here **do not use** the generic hash table you implemented in `67-phonebook`. The challenge here is to find out how to implement the hash table in a much more simpler way when you:

* understand how Java char (Character) and int (Integer) datatypes relate to each other, and
* how using this information you may easily implement a so called lookup table so that this implements, at the same time, the hash table principle.

So **you will not get any points if you use the** hash table implemented in `67-phonebook`!


## Prerequisites

You have all the tools installed and working. This was tested in the `00-init` exercise  of the course. If you haven't done that yet, do it now.

You know what hash tables are. If not, check the lectures.

## Instructions Phase 1

The exercise has some code for you ready to start with:

* `BrailleSymbol` -- a class that maps a `char` in the "usual" character range (in this case Finnish major chars) to Braille characters.
* `BrailleTable` -- an *abstract base class* for handling mapping of the usual chars to Braille symbols. This class cannot be used as such, but it must be subclassed and the subclasses then can be instantiated and used.
* `SlowBrailleTable` -- a subclass of `BrailleTable`, implementing a simple, naive way to look up a Braille char for a Finnish char, and vice versa.
* `BrailleConverter` -- a class that reads book files and uses the subclasses of `BrailleTable` to convert book text files to braille symbol text files. After printing these to a paper using braille symbol printers they can then be read! Very useful to have!

Then there is one more class for **you** to implement -- `HashBrailleTable`. This class should do what the `SlowBrailleTable` does, but *much faster* by using hash tables to look up braille symbols using a char, and vice versa.

Braille symbols are characters in the Unicode range `U+2800–U+28FF`. Java is therefore able to handle braille symbols, so it is up to your console and editor setup to make sure you can see the content. 

The UML class diagram below shows the overall structure of the project:

![UML class diagram](classes.png)

**Before testing**, unzip the testfiles in directory `src/test/resources/` from the zip file to the same directory as the zipfile is.

You can now test the slow implementation by using the test `SlowBrailleTests`:

```console
mvn -Dtest=SlowBrailleTests test
```

**Do not** yet test the other tests since they test your implementation in Phase 2 below.

The tests use two test files. [Kalevala](https://en.wikipedia.org/wiki/Kalevala), the Finnish epic poetry compiled at the 19th century. The second test file is a large file `Bulk.txt`.

The tests execute ten times and measure the execution time in total.

Tests produce output files containing the input file text in braille symbols. Note that your text editor and font used must support Unicode and displaying braille symbols (VS Code editor is OK). 

After executing the tests, Kalevala braille version can be found in the `Kalevala-Braille.txt` file and the larger file output is in the `Bulk-Braille.txt` file.

> If you cannot see the braille symbols, switch to a editor that can show them. You can also configure your terminal window to use UTF-8 if viewing braille text in terminal. Or use a better terminal app instead of Windows Console (a.k.a Command Prompt) since it is not a good tool for a developer. Instead, install Windows Terminal from the Microsoft Store.

Just for fun (and making sure you actually can see already now what the output looks like), here's a screenshot of the beginning of the Kalevala from the console of the teacher's computer, created by this app:

![Kalevala in braille](Kalevala-Braille.png)

After executing the tests, do note the execution time measurements printed out, when the `SlowBrailleTable` implementation is used to generate the braille version of the input files.


## Instructions Phase 2

This is the phase where you will implement a faster converter. 

**Implement** the `HashBrailleTable` using hash tables as the data structure. Remember that you *must not* use the Java Map/HashMap/Hashtable or any other Java container classes in your implementation. Just use the plain Java arrays.

The class `HashBrailleTable` and the overridden method signatures are already there. You just need to think how to implement the hash table in this case.

The `BrailleTable` class has **comments** with the abstract methods you need to implement, describing what those methods should do. **Use these comments** to assist in implementing your `HashBrailleTable`.

If you need help to get started, come to the course exercises.

> Hint: you do not need `BrailleSymbol` class in your implementation. Plain Java arrays and `Character` class / `char` should be enough with some surprisingly simple algorithms. Do your own design and see how it works!

> Another hint: as said in the intro of this task, think carefully how Java char (Character) relates to int (Integer), and you may find out how to implement a significantly much simpler hash table (lookup table) than in the task `67-phonebook`. So do not use that generic hash table here!

If you are interested in the conversion to braille, check out the chapter Some background for the conversion process at the end of this readme.

## Testing 

There is test called `HashCorrectnessTests`. It produces an output file `simple-Braille.txt`. This is evaluated against the expected output. You can use this test to **check the correctness** of your implementation:

```console
mvn -Dtest=HashCorrectnessTests test
```

After your implementation is correct, run the `AllBrailleTests` to check for the time performance against the slow implementation:

```console
mvn -Dtest=AllBrailleTests test
```

As usual, run the tests to see that your implementation works. Check also from the output files that they contain the expected output, no errors.

If the tests do not pass, you will see errors. Otherwise you will see that the tests succeed. If you have issues, fix your implementation and try again.

What is **important** in the tests is the **time performance** of your hash table implementation. Let's see the output from the teacher's solution:

```console
               ========== Statistics ==========

 Converting Kalevala.txt with slow table 10 times took    157 ms
 Converting Kalevala.txt with hash table 10 times took    149 ms
 Converting Bulk.txt     with slow table 10 times took   9795 ms
 Converting Bulk.txt     with hash table 10 times took   5112 ms
 >> Hashtable execution time was 52,19% of slow table time
 ```

Here you can see that the teacher's hash table implementation execution time with the large test file is **only 52,19%** of the slow algorithm!

You should have similar results. If your implementation is slower, you shoud try to make it more effective.

The tests **will fail** if your implementation is not below 75% of the slow algorithm execution time!

**THINK** the following questions since they might be asked in the course exams:

1. What is the time complexity of the slow table implementation?
2. What is the time complexity of the hash table implementation?


## Delivery

Deliver the exercise as instructed in the course, after your tests pass and you are satisfied with the hash table speed.


## Questions or problems?

Participate in the course lectures, exercises and online support group.

If you have issues building and running the tests, make sure you have the correct JDK installed, environment variables are as they should and Maven is installed.


## Some background for the conversion process

Note that the symbol table in `src/test/resources/symboltable.tsv` that is used in this app, has only uppercase letters for the "normal" characters. However, book files contain upper and lowercase chars. So how can the converters find the braille symbol for 'a' if the symbol table has only character 'A'? 

You can see from the implementation of `SlowBrailleTable` that when comparing the symbol table character to the one read from the book file, the book file character is first *converted to uppercase*. That one is used in the lookup. In this way, the table does not have to contain both uppercase and lowercase letters. Since braille system uses the *same symbol for both* upper and lowercase character. 

But wait -- How anyone reading a braille book with their fingers can then know if a letter is uppercased or lowercased?!

Braille works so that if a word has an uppercase character first, it is **prefixed by a special symbol** telling this to the reader. The first line of Kalevala is "Ensimäinen runo". The corresponding braille line is "⠠⠑⠝⠎⠊⠍⠜⠊⠝⠑⠝⠀⠗⠥⠝⠕". When you count the characters, you will see that the braille text has 16 characters as the Finnish has 15 characters. The first extra symbol in the braille text is a symbol telling that the next symbol is a capital letter.

The same principle is applied to numbers. Before each number, a special symbol is telling that the next symbol is a symbol, not a letter.

If you take a look at the `symboltable.tsv`, you will see that the character '1' and 'A' have the same braille symbol:

![Braille symbols](Braille-Symbols.png)

So, when generating the braille file from a text character file, one must check that if the character now processed is uppercased or a number, a correct braille special symbol is *put before the actual symbol*. *This is already done for you* in `BrailleConverter.convertTextFileToBraille()`:

```Java
   if (Character.isUpperCase(character)) {
      builder.append(BrailleSymbol.CAPITAL_LETTER_FOLLOWS);
      onNumberSequence = false;
   } else if (Character.isDigit(character)) {
      if (!onNumberSequence) {
         builder.append(BrailleSymbol.NUMBER_FOLLOWS);
         onNumberSequence = true;
      }
   } else {
      onNumberSequence = false;
   }
   builder.append(symbol);
```

Where `builder` is a `StringBuilder` creating the output text file braille symbol line from each character of the input text file. As you can see above, if necessary, first a special symbol is put to the output String, then the actual symbol is inserted. For series of numbers, only one special symbol is in front of the numbers.

Now the brailler reader knows what the symbol actually is: a symbol for character, uppercased. Or perhaps a number instead.


## About

* Course material for Tietorakenteet ja algoritmit | Data structures and algorithms 2021-2022.
* Study Program for Information Processing Science, University of Oulu.
* (c) Antti Juustila, INTERACT Research Group.
