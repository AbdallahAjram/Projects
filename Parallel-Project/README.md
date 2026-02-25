# Parallel-Project

## Description

Parallel-Project is a performance-driven exploration of parallel computing techniques applied to a classic computational problem: **matrix multiplication**.  
The project compares multiple parallelization models—**CUDA**, **MPI**, **OpenMP**, and **Pthreads**—against a traditional **sequential** implementation. By analyzing execution time, scalability, and efficiency across these approaches, the project demonstrates how different hardware and software paradigms impact performance in high-performance computing (HPC) environments.

## Features

- **Sequential Matrix Multiplication** – Baseline implementation for benchmarking  
- **OpenMP Version** – Shared-memory parallelism with multithreading  
- **Pthreads Version** – Fine-grained thread control and custom workload distribution  
- **MPI Version** – Distributed computing across multiple nodes or processes  
- **CUDA Version** – GPU-accelerated matrix multiplication using NVIDIA CUDA  
- **Benchmarking Support** – Compare performance across all implementations  

---

## Project Structure

```
.
├── cuda_matmul_gpu_only.cu
├── mpi_matmul.c
├── openmp_matmul.c
├── pthreads_matmul.c
└── seq_matmul.c
```

---

## How to Build and Run

### **Sequential**
```bash
gcc seq_matmul.c -o seq
./seq
```

### **OpenMP**
```bash
gcc -fopenmp openmp_matmul.c -o openmp
./openmp
```

### **Pthreads**
```bash
gcc pthreads_matmul.c -o pthreads -lpthread
./pthreads
```

### **MPI**
```bash
mpicc mpi_matmul.c -o mpi
mpirun -np <num_processes> ./mpi
```

### **CUDA**
```bash
nvcc cuda_matmul_gpu_only.cu -o cuda
./cuda
```

---

## Performance Evaluation

After running each implementation, you can:

- Compare execution times  
- Calculate speedup relative to the sequential version  
- Analyze scalability by varying matrix sizes and thread/process counts  
- Visualize results using plotting tools (e.g., Python/Matplotlib)
