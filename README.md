[![Build Status](https://travis-ci.com/vaclavnemec/jExtreme.svg?branch=master)](https://travis-ci.org/vaclavnemec/jExtreme)

jExtreme
=========

jExtreme is a Java library which provides implementations for mathematical optimization of functions (functions can be defined as lambdas). Library offers three optimization algorithms:

  - **Blind Algorithm** - Searches space pseudo-randomly
  - **Genetic Algorithm** - Algorithm is implemented to use a collection of double values to represent possible solutions instead of binary arrays
  - **Self-Organizing Migrating Algorithm (SOMA)** - there is currently an implementation called SomaManyToOne - more details about the algorithm can be found here [http://www.ft.utb.cz/people/zelinka/soma/](http://www.ft.utb.cz/people/zelinka/soma/)

> The library uses **MersenneTwister** implementation from **Apache Commons** as a random generator.

## Usage
The library is not published in any public repository. You have to build it and add it to your classpath manually.

First define a function which you want to optimize.

```java
FitnessFunction fitnessFunction = xAndY -> {
    double x = xAndY.getDimension(0);
    double y = xAndY.getDimension(1);
    return -((x * x) + (x * x + y * y));
};
```
You must specify the limits. You have to provide the same amount of dimensions.

```java
Specimen specimen = new Specimen(new Range[] {
        new Range(-600, 600),
        new Range(-600, 600)
});
```

There are 3 algorithms available. These are parameters for the genetic algorithm.


```java
GeneticAlgorithmParams params = new GeneticAlgorithmParams();
params.setFitnessFunction(fitnessFunction);
params.setSpecimen(specimen);
params.setPopulationSize(100);
params.setNumberOfGenerations(1000);
params.setMutationProbability(0.1);
params.setElitismRatio(0.05);
params.setEvolutionListener(new ConsoleEvolutionListener());
params.setCrossovers(Arrays.asList(new ArithmeticCrossover()));
```

The method `getOptimumSolution` starts the algorithm and returns the optimum solution.

```java
Genes optimumSolution = new GeneticAlgorithm(params).getOptimumSolution();
``` 