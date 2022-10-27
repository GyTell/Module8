//Module 8 Concurrency Glenney Tello 10/24/2022
//import for random number generator to work
import java.util.Random;

//Must extend class 
//https://www.youtube.com/watch?v=0ySznjdXMEA&list=PLS1QulWo1RIbfTjQvTdj8Y6yyq4R7g-Al&index=44
class thread2 extends Thread {

	public int[] rarray;
	public int low, high, partial;
//priorities
public thread2(int[] array, int low, int high){
	this.rarray = array;
	this.low = low;
	this.high = Math.min(high, array.length);
}
	public int getPartialSum(){

	return partial;
}
// Running Thread
public void run(){ 
	
	partial = sum(rarray, low, high);}

public static int sum(int[] array){

	return sum(array, 0, array.length);}

public static int sum(int[] array, int low, int high){

	int total = 0;
	
	for (int i = low; i < high; i++) {
	total += array[i];}
	return total;
}

// Source: https://eddmann.com/posts/parallel-summation-in-java/
//Parallel Summation 
public static int getParallelSum(int[] array) {

	return getParallelSum(array, Runtime.getRuntime().availableProcessors());
}
public static int getParallelSum(int[] array, int threads){
	
	int size = (int) Math.ceil(array.length * 1.0 / threads);

	thread2[] sums = new thread2[threads];

	for (int i = 0; i<threads; i++) {
//source:https://www.programiz.com/java-programming/library/math/ceil
		sums[i] = new thread2(array, i* size, (i + 1)* size);
		sums[i].start();
}
	try {
	for (thread2 sum : sums) {
	sum.join();
}}
	catch (InterruptedException e) { }
	int total = 0;
	for (thread2 sum : sums) {
	total += sum.getPartialSum();
}
return total;
}

//Finally part. Random number generation and System Print Out
public static void main(String[] args)
// Random number generator
{ Random randomNumber = new Random(); 
//200,000,000 random numbers
int[] array = new int[200000000];
//https://www.educative.io/answers/how-to-get-the-sum-of-an-array-in-javascript
	for (int i = 0; i < array.length; i++) {
// Numbers between 1-10
		array[i] = randomNumber.nextInt(10);
}
//Source: https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
long start = System.currentTimeMillis();
	System.out.println("Sum for single thread: " + thread2.sum(array) + " Time in Milliseconds: "+ (System.currentTimeMillis() - start) );
	start = System.currentTimeMillis();
	System.out.println("Sum for parallel thread: " + thread2.getParallelSum(array) +  " Time in Milliseconds: "+ (System.currentTimeMillis() - start));
}}
//Additional sources: https://stackoverflow.com/questions/53651338/calculating-the-sum-and-average-of-a-random-array