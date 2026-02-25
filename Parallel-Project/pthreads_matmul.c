// pthreads_matmul.c
// Parallel Matrix Multiplication using POSIX Threads (Pthreads)
//
// C = A * B
// Each thread handles a block of rows of matrix A
// Works with same N as CUDA/MPI/OpenMP

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>

#define N 1500   // Matrix size NxN
#define MAX_THREADS 32  // Safety limit

typedef struct {
    int thread_id;
    int start_row;
    int end_row;
    float *A;
    float *B;
    float *C;
} ThreadData;

double get_time_ms() {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (ts.tv_sec * 1e3) + (ts.tv_nsec / 1e6);
}

// Thread routine
void* thread_matmul(void *arg)
{
    ThreadData *td = (ThreadData*)arg;

    float *A = td->A;
    float *B = td->B;
    float *C = td->C;

    int start = td->start_row;
    int end = td->end_row;

    for (int i = start; i < end; i++) {
        for (int j = 0; j < N; j++) {
            float sum = 0.0f;
            for (int k = 0; k < N; k++) {
                sum += A[i * N + k] * B[k * N + j];
            }
            C[i * N + j] = sum;
        }
    }
    return NULL;
}

int main(int argc, char **argv)
{
    if (argc < 2) {
        printf("Usage: %s <num_threads>\n", argv[0]);
        return 1;
    }

    int num_threads = atoi(argv[1]);
    if (num_threads > MAX_THREADS) num_threads = MAX_THREADS;

    printf("Pthreads Matrix Multiplication (N = %d, threads = %d)\n",
           N, num_threads);

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

    pthread_t threads[MAX_THREADS];
    ThreadData td[MAX_THREADS];

    int rows_per_thread = N / num_threads;
    int extra = N % num_threads;

    double start = get_time_ms();

    // Launch threads
    int current_row = 0;
    for (int t = 0; t < num_threads; t++) {
        int rows = rows_per_thread + (t < extra ? 1 : 0);

        td[t].thread_id = t;
        td[t].start_row = current_row;
        td[t].end_row = current_row + rows;
        td[t].A = A;
        td[t].B = B;
        td[t].C = C;

        pthread_create(&threads[t], NULL, thread_matmul, &td[t]);

        current_row += rows;
    }

    // Wait for all threads
    for (int t = 0; t < num_threads; t++) {
        pthread_join(threads[t], NULL);
    }

    double end = get_time_ms();

    printf("Pthreads time: %.3f ms (%.3f seconds)\n",
           end - start, (end - start) / 1000.0);

    free(A);
    free(B);
    free(C);

    return 0;
}
