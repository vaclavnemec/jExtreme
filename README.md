jExtreme
=========

jExtreme is Java 8 library which allows mathematical optimization for given function. Library offers three optimization algorithms so far:

  - Blind Algorithm - the simplest implementation. Searches function space pseudo-randomly
  - Genetic Algorithm - probably very well known algorithm. Library is implemented to use collection of Double values to represent possible solutions instead of binary arrays.
  - Self Organized Migration Algorithm (SOMA) - currently there is implementation called SomaManyToOne - more detailes about algorithm can be found at its homepage http://www.ft.utb.cz/people/zelinka/soma/

Example of Usage
-----------

```java
// First we must create solution factory.
// Solution factory is place where you can manage how your solutions are created.
// Every class implementing solution can be build here - e.g. mathematical functions or whole simulations
// which you want to optimize.
final SolutionFactory solutionFactory = new SolutionFactory() {

    @Override
    public Solution createSolution(Genotype genotype) {
        // Genotype contains collection of Double numbers (genes) which represents point in decision space.
        // Each solution will be instance of AxisParallelHyperEllipsoid.
        return new AxisParallelHyperEllipsoid(genotype);
    }

    @Override
    public Specimen getSpecimen() {
        // Specimen defines range for our searching. Both axis has same ranges in this case.
        return new Specimen(Arrays.asList(new GeneRange(-AXIS_RANGES, AXIS_RANGES), new GeneRange(-AXIS_RANGES, AXIS_RANGES)));
    }
};

// To create genetic algorithm we must specify which crossover strategies will be used.
final List<GeneticCrossover> crossovers = new ArrayList<>();
crossovers.add(new ArithmeticCrossover());
crossovers.add(new UniformCrossover(0.2));

// We need couple of other parameters like population size, number of generations,
// mutation ratio and elitsm ratio to create genetic algorithm instance.
final OptimizationAlgorithm algorithm = 
        new GeneticAlgorithm(solutionFactory, crossovers, new ConsoleEvolutionListener(),
                POPULATION_SIZE, NUMBER_OF_GENERATIONS, MUTATION_RATIO, ELITISM_RATE);

// In this moment the genetic algorithm will do its work.
final AxisParallelHyperEllipsoid bestFoundSolution = (AxisParallelHyperEllipsoid) algorithm.getOptimumSolution();
// and the best solution should be somewhere around zero.
System.out.println("BEST FOUND SOLUTION: " + bestFoundSolution);
```
