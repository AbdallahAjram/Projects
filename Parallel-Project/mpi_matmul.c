// mpi_matmul.c
// Block-row MPI matrix multiplication: C = A * B
// Perfect for showing MPI speedup and comparing with CUDA.

#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>
#include <time.h>

#define N 1500   // Matrix size NxN (same as CUDA)
#define TAG 0

double get_time_ms()
{
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (ts.tv_sec * 1e3) + (ts.tv_nsec / 1e6);
}

// Sequential CPU multiply for each rank's submatrix
void matmul_block(float *A, float *B, float *C, int rows, int n)
{
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < n; j++)
        {
            float sum = 0;
            for (int k = 0; k < n; k++)
                sum += A[i * n + k] * B[k * n + j];

            C[i * n + j] = sum;
        }
    }
}

int main(int argc, char **argv)
{
    MPI_Init(&argc, &argv);

    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int n = N;

    // Compute block size (# of rows per process)
    int rows_per_proc = n / size;
    int remainder     = n % size;

    int my_rows = rows_per_proc + (rank < remainder ? 1 : 0);

    // Calculate displacements for scattering A
    int *sendcounts = NULL;
    int *displs     = NULL;
    if (rank == 0)
    {
        sendcounts = malloc(sizeof(int) * size);
        displs     = malloc(sizeof(int) * size);

        int offset = 0;
        for (int r = 0; r < size; r++)
        {
            sendcounts[r] = (rows_per_proc + (r < remainder ? 1 : 0)) * n;
            displs[r] = offset;
            offset += sendcounts[r];
        }
    }

    // Allocate local matrices
    float *A_full  = NULL;
    float *B_full  = malloc(n * n * sizeof(float));
    float *A_local = malloc(my_rows * n * sizeof(float));
    float *C_local = malloc(my_rows * n * sizeof(float));

    if (rank == 0)
    {
        A_full = malloc(n * n * sizeof(float));

        // Initialize A and B
        for (int i = 0; i < n * n; i++)
        {
            A_full[i] = (float)((i * 2) % 100) / 10.0f;
            B_full[i] = (float)((i * 3) % 100) / 10.0f;
        }
    }

    // Broadcast entire B matrix to all processes
    MPI_Bcast(B_full, n * n, MPI_FLOAT, 0, MPI_COMM_WORLD);

    // Scatter rows of A
    MPI_Scatterv(A_full, sendcounts, displs, MPI_FLOAT,
                 A_local, my_rows * n, MPI_FLOAT,
                 0, MPI_COMM_WORLD);

    // Compute local result
    double start = get_time_ms();
    matmul_block(A_local, B_full, C_local, my_rows, n);
    double end = get_time_ms();

    if (rank == 0)
        printf("MPI (rank 0) block compute time only: %.3f ms\n", end - start);

    // Gather results back to root
    float *C_full = NULL;
    if (rank == 0)
        C_full = malloc(n * n * sizeof(float));

    MPI_Gatherv(C_local, my_rows * n, MPI_FLOAT,
                C_full, sendcounts, displs, MPI_FLOAT,
                0, MPI_COMM_WORLD);

    if (rank == 0)
        printf("MPI matrix multiplication complete.\n");

    // Cleanup
    free(A_local);
    free(B_full);
    free(C_local);

    if (rank == 0)
    {
        free(A_full);
        free(C_full);
        free(sendcounts);
        free(displs);
    }

    MPI_Finalize();
    return 0;
}

