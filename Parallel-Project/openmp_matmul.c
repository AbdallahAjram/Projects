// openmp_matmul.c
// OpenMP parallel matrix multiplication: C = A * B
// Matches CUDA and MPI matrix sizes for fair comparison.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

#define N 1500  // Same size as CUDA & MPI for performance comparison

double get_time_ms() {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (ts.tv_sec * 1e3) + (ts.tv_nsec / 1e6);
}

void matmul_openmp(float *A, float *B, float *C, int n, int threads) {
    omp_set_num_threads(threads);

    #pragma omp parallel for collapse(2)
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            float sum = 0.0f;
            for (int k = 0; k < n; k++) {
                sum += A[i * n + k] * B[k * n + j];
            }
            C[i * n + j] = sum;
        }
    }
}

int main(int argc, char** argv) {

    if (argc < 2) {
        printf("Usage: %s <num_threads>\n", argv[0]);
        return 1;
    }

    int threads = atoi(argv[1]);

    printf("OpenMP Matrix Multiplication (N = %d, threads = %d)\n", N, threads);

    size_t bytes = N * N * sizeof(float);

    float *A = (float*)malloc(bytes);
    float *B = (float*)malloc(bytes);
    float *C = (float*)malloc(bytes);

    if (!A || !B || !C) {
        printf("Memory allocation failed.\n");
        return 1;
    }

    // Initialize matrices A and B
    for (int i = 0; i < N * N; i++) {
        A[i] = (float)((i * 2) % 100) / 10.0f;
        B[i] = (float)((i * 3) % 100) / 10.0f;
    }

    double start = get_time_ms();
    matmul_openmp(A, B, C, N, threads);
    double end = get_time_ms();

    printf("OpenMP time: %.3f ms (%.3f seconds)\n", end - start, (end - start) / 1000.0);

    free(A);
    free(B);
    free(C);

    return 0;
}
