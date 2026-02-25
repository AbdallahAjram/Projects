// seq_matmul.c
// Sequential (single-thread) matrix multiplication
// Serves as the baseline for speedup comparisons.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 1500  // Same size as CUDA, MPI, OpenMP, Pthreads

double get_time_ms() {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (ts.tv_sec * 1e3) + (ts.tv_nsec / 1e6);
}

void matmul_seq(float *A, float *B, float *C, int n)
{
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

int main()
{
    printf("Sequential Matrix Multiplication (N = %d)\n", N);

    size_t bytes = N * N * sizeof(float);

    float *A = (float*)malloc(bytes);
    float *B = (float*)malloc(bytes);
    float *C = (float*)malloc(bytes);

    if (!A || !B || !C) {
        printf("Memory allocation failed.\n");
        return 1;
    }

    // Initialize matrices with deterministic values
    for (int i = 0; i < N * N; i++) {
        A[i] = (float)((i * 2) % 100) / 10.0f;
        B[i] = (float)((i * 3) % 100) / 10.0f;
    }

    double start = get_time_ms();
    matmul_seq(A, B, C, N);
    double end = get_time_ms();

    printf("Sequential time: %.3f ms (%.3f seconds)\n",
           end - start, (end - start) / 1000.0);

    free(A);
    free(B);
    free(C);

    return 0;
}

