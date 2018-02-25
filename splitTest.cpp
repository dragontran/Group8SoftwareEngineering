// Program to test execution time of linux split cmd
// only run on linux / or linux bash on windows
// compile with g++ -o splitTest splitTest.cpp -std=c++11
// run cmd ./splitTest <input_file> <output_file>

/* Results */
// avg linux split execution time: 57 s
// avg caci split execution time: 109.448 s
// avg JFSplit execution time 78 s

#include <iostream>
#include <chrono>
#include <string>

int main(int argc, char** argv){

  // record start time
  std::chrono::steady_clock::time_point begin = std::chrono::steady_clock::now();

  // linux split cmd on 8gb iso file
  system("split -b 500M toop.iso linuxsplittest.iso");

  // record end time
  std::chrono::steady_clock::time_point end= std::chrono::steady_clock::now();

  // compute time difference
  std::cout << "Time elapsed = " << std::chrono::duration_cast<std::chrono::milliseconds>(end - begin).count() <<std::endl;

  // remove split files for convenience
  system("rm linux*");
  return 0;
}
